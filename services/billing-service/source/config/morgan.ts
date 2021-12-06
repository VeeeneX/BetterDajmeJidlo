import morgan, { StreamOptions } from "morgan";
import logger from "../shared/logger";

const stream: StreamOptions = {
    write: (message) => logger.info(message.substring(0, message.lastIndexOf('\n'))),
};

const skip = () => {
    const env = process.env.NODE_ENV || "development";
    return env !== "development";
};

const morganMiddleware = morgan(
    ":method :url :status :res[content-length] - :response-time ms",
    { stream, skip }
);

export default morganMiddleware;
