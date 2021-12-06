interface InvoiceRestaurantData {
    comp_reg: string,
    b_name: string,
    addr: string,
    location: string,
    zip: string
};

interface InvoiceClientData {
    name: string,
    addr: string,
    location: string,
    zip: string,
    phone: string
}

interface InvoiceProduct {
    name: string,
    cost: number,
    quantity: number,
    amount?: number
}

interface InvoiceData {
    user_id: string,
    restaurant_id: string,
    account_holder: string,
    account_number: number,
    products: InvoiceProduct[]
}

export { InvoiceRestaurantData, InvoiceClientData, InvoiceProduct, InvoiceData };
