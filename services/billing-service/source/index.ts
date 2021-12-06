import http from 'http';
import router from './server';

/** Server */
const httpServer = http.createServer(router);
httpServer.listen(8080, "0.0.0.0", () => console.log(`The server is running on port 8080`));
