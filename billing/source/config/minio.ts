import * as minio from 'minio';

const minio_client: minio.Client = new minio.Client({
    endPoint: process.env.MINIO_ADDR ?? 'localhost',
    port: parseInt(process.env.MINIO_PORT || '9000'),
    useSSL: false,
    accessKey: process.env.MINIO_ACC_KEY ?? '',
    secretKey: process.env.MINIO_SEC_KEY ?? ''
});

export default minio_client;
