/** source/routes/posts.ts */
import express from 'express';
import controller from '../controllers/invoices';

const router = express.Router();

router.post('/invoice', controller.generateInvoice);

export = router;