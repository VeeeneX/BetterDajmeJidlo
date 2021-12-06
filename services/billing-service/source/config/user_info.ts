import axios from 'axios';

const user_info_client = axios.create({
  baseURL: process.env.USER_SERVICE_URL,
	timeout: 15000,
});

export default user_info_client;
