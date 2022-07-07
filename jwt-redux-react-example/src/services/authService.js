import configData from '../config.json';
import axios from 'axios';
import { token } from '../helpers/token';

const signup = (user) => {
  return axios.post(`${process.env.REACT_APP_SERVER_URL}/user/signup`, user);
};

const login = (email, password) => {
  return axios
    .post(`${process.env.REACT_APP_SERVER_URL}/user/login`, {
      email,
      password,
    })
    .then((res) => {
      token.storeToken(res.data);
      return res.data;
    });
};

const logout = () => {
  token.removeToken();
}

export const authService = {
  signup,
  login,
  logout
}