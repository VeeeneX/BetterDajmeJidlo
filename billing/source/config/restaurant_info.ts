import axios from 'axios';

const restaurant_info_client = axios.create({
	baseURL: 'http://' + (process.env.RESTAURANT_INFO_ADDR ?? 'localhost') + ':'
                + parseInt(process.env.RESTAURANT_INFO_PORT || '9002') + '/'
                + process.env.RESTAURANT_INFO_PATH ?? '',
	timeout: 15000,
});

export default restaurant_info_client;