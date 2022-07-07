import { AUTH } from './index';
import { authService } from '../../services';
import { history } from '../../history';

const signup = (user) => {
  return (dispatch) => {
    dispatch(request(user));
    authService.signup(user).then(
      (user) => {
        dispatch(success());
        history.push('/auth/login');
      },
      (error) => {
        dispatch(failure(error.toString()));
      },
    );
  };
  function request() {
    return { type: AUTH.REGISTER_REQUEST };
  }
  function success() {
    return { type: AUTH.REGISTER_SUCCESS };
  }
  function failure() {
    return { type: AUTH.REGISTER_FAILURE };
  }
}

const login = (username, password) => {
  return (dispatch) => {
    dispatch(request());
    authService.login(username, password).then(
      (user) => {
        dispatch({ 
          type: AUTH.LOGIN_SUCCESS, 
          payload: user 
        });
        history.push('/blog');
      },
      (error) => {
        dispatch(failure(error));
      },
    );
  };

  function request() {
    return { type: AUTH.LOGIN_REQUEST};
  }
  function failure(error) {
    return { type: AUTH.LOGIN_FAILURE};
  }  
}

export const logout = () => (dispatch) => {
  authService.logout();

  dispatch({
    type: AUTH.LOGOUT,
  });
  history.push('/auth/login');
};

const refreshToken = (accessToken) => (dispatch) => {
  dispatch({
    type: AUTH.REFRESH_TOKEN,
    payload: accessToken,
  })
}

export const authActions = {
  signup,
  login,
  refreshToken,
  logout
}
