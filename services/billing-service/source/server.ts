/** source/server.ts */
import express, { Express } from 'express';
import swaggerUi from 'swagger-ui-express';
import * as swaggerDocument from './swagger/openapi.json';
import morganMiddleware from './config/morgan';
import routes from './routes/invoices';

const router: Express = express();

/** Logging */
router.use(morganMiddleware);
/** Parse the request */
router.use(express.urlencoded({ extended: false }));
/** Takes care of JSON data */
router.use(express.json());

/** RULES OF OUR API */
router.use((req, res, next) => {
    // set the CORS policy
    res.header('Access-Control-Allow-Origin', '*');
    // set the CORS headers
    res.header('Access-Control-Allow-Headers', 'origin, X-Requested-With,Content-Type,Accept, Authorization');
    // set the CORS method headers
    if (req.method === 'OPTIONS') {
        res.header('Access-Control-Allow-Methods', 'GET PATCH DELETE POST');
        return res.status(200).json({});
    }
    next();
});

/** Routes */
router.use('/', routes);

router.use('/', swaggerUi.serve, swaggerUi.setup(swaggerDocument));


/** Error handling */
router.use((req, res, next) => {
    const error = new Error('not found');
    return res.status(404).json({
        message: error.message
    });
});

export default router
