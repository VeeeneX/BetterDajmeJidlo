import axios from 'axios';

const restaurant_info_client = axios.create({
	baseURL: process.env.RESTAURANT_SERVICE_URL,
	timeout: 15000,
});

export default restaurant_info_client;
