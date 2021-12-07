/** source/controllers/posts.ts */
import { Request, Response } from 'express';
import carbone from 'carbone';
import short from 'short-uuid';

import minio_client from '../config/minio';
import user_info_client from '../config/user_info';
import { InvoiceData } from '../model/invoice_models';
import restaurant_info_client from '../config/restaurant_info';
import logger from '../shared/logger';

const options: carbone.RenderOptions = {
    convertTo: 'pdf'
};

const handle = async (promise: Promise<any>) => {
    return promise
      .then(response => ([response, undefined]))
      .catch(error => Promise.resolve([undefined, error]));
}

const generateInvoice = async (req: Request, res: Response) => {
    const request_body: InvoiceData = req.body;

    const [invoice_user, user_error] = await handle(user_info_client.get(request_body.user_id));
    if (user_error) {
        logger.warn(`Failed to fetch from user endpoint, msg: ${user_error.message}`);
        return res.status(user_error.response?.status || 500).json({
            message: `Couldn't fetch user by user ID: ${request_body.user_id}, msg: ${user_error.message ?? ''}`
        });
    }

    const [invoice_rest, rest_error] = await handle(restaurant_info_client.get(request_body.restaurant_id));
    if (rest_error) {
        logger.warn(`Failed to fetch from restaurant endpoint, msg: ${rest_error.message}`);
        return res.status(rest_error.response?.status || 500).json({
            message: `Couldn't fetch restaurant by restaurant ID: ${request_body.restaurant_id}, msg: ${rest_error.message ?? ''}`
        });
    }

    const amount_total: number = request_body.products
        .reduce((prevValue, currProd) => prevValue + (currProd.quantity * currProd.cost), 0);

    const invoice_products = request_body.products
        .map(product => ({
            name: product.name,
            cost: product.cost.toFixed(2),
            quantity: Math.round(product.quantity).toFixed(0),
            amount: (product.cost * Math.round(product.quantity)).toFixed(2)
        }));

    invoice_rest.data.comp_reg = invoice_rest.data.name + ', s.r.o'
    invoice_rest.data.b_name = "Building 1/A"
    invoice_rest.data.addr = (''+ invoice_rest.data.address).split('\n')[0]
    invoice_rest.data.location = (''+ invoice_rest.data.address).split('\n')[1].split(',')[0]
        + (''+ invoice_rest.data.address).split(' ')[6]
    invoice_rest.data.zip = (''+ invoice_rest.data.address).split(' ').slice(-1)[0]

    const data = {
        id: short().generate().toString(),
        client: invoice_user.data,
        rest: invoice_rest.data,
        products: invoice_products,
        amount_total: amount_total.toFixed(2),
        sub_total: Number(amount_total * 0.8).toFixed(2),
        tax_total: Number(amount_total * 0.2).toFixed(2),
        issue_date: new Date().toLocaleString('sk', {
            year: 'numeric', month: '2-digit', day: '2-digit',
            hour: '2-digit', hour12: false,
            minute:'2-digit', second:'2-digit'}),
        acc_holder: request_body.account_holder,
        acc_num: request_body.account_number
    };

    const [carbon_res, carbon_err] = await handle(new Promise((resolve, reject) =>
        carbone.render('./invoice-template.odt', data, options, (carbon_err, carbon_res) => {
            if (carbon_err) reject(carbon_err); else resolve(carbon_res)
        })
    ));

    if (carbon_err) {
        logger.error(`Failed to generate invoice pdf, msg: ${carbon_err.message}`);
        return res.status(500).json({ message: "Failed to generate invoice pdf" });
    }

    const normalized_name: string = data.client.name.replace(" ", "_");
    const [minio_res, minio_err] = await handle(minio_client.putObject(
        "invoice", data.id + "_" + normalized_name + ".pdf", carbon_res, {
            'Content-Type': "application/pdf",
            'Cache-Control': 'max-age=31536000, immutable'
        })
    );

    if (minio_err) {
        logger.error(`Failed to upload invoice pdf to minio, msg: ${minio_err.message}`);
        return res.status(500).json({ message: "Failed to upload invoice pdf to file storage" });
    }
    return res.status(201).json({ message: "Invoice pdf generated" });
};

export default { generateInvoice };
