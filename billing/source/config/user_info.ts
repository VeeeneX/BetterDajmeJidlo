import axios from 'axios';

const user_info_client = axios.create({
	baseURL: 'http://' + (process.env.USER_INFO_ADDR ?? 'localhost') + ':'
                + parseInt(process.env.USER_INFO_PORT || '9001') + '/'
                + process.env.USER_INFO_PATH ?? '',
	timeout: 15000,
});

export default user_info_client;
