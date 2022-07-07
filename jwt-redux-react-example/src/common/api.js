import { token } from '../helpers/token';
import { authActions } from '../redux';
import configData from "../config.json";
import { history } from '../history';
import axios from "axios";
require('dotenv').config()


let store;

export const injectStore = (_store) => {
  store = _store
}

const axiosInstance = axios.create({
  baseURL: process.env.REACT_APP_SERVER_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

axiosInstance.interceptors.request.use(
  (config) => {
    if (token.getAccessToken()) {    
      config.headers["Authorization"] = getAuthorizationHeader(token.getAccessToken());
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  },
);

axiosInstance.interceptors.response.use(
  (res) => {
    return res;
  },
  async (error) => {
    const { dispatch } = store;
    const originalConfig = error.config;
      if (error.response.status === 403 && !originalConfig._retry) {
        originalConfig._retry = true;
        try {
          const response = await refreshToken();
          dispatch(authActions.refreshToken(response.data));
          token.storeToken(response.data);
          return axiosInstance(originalConfig);
        } catch (refreshTokenError) {
          onError(refreshTokenError);
        }
    }

    onError(error);

    function onError(err) {
      history.push('/auth/login');
      dispatch(authActions.logout());
      return Promise.reject(err);
    }
  },
);


const refreshToken = () => {
  return axios.get(`${process.env.REACT_APP_SERVER_URL}/token/refresh`, {
    headers: {
      Authorization: getAuthorizationHeader(token.getRefreshToken()),
    }
  });
}

const getAuthorizationHeader = (token) => {
  return 'Bearer ' + token;
}

export default axiosInstance;
