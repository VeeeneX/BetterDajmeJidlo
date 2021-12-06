import { Client } from 'minio';

const url = new URL(process.env.MINIO_URL || '')

const minio_client: Client = new Client({
    endPoint: url.hostname,
    port: +(url.port),
    useSSL: false,
    accessKey: url.username,
    secretKey: url.password
});

export default minio_client;
