import { token } from '../../helpers/token';
import { AUTH } from './types';

const user = token.getAccessData();
const initialState = user
  ? { isLoggedIn: true, user: user }
  : { isLoggedIn: false, user: null };

export function authentication(state = initialState, action) {
  const { type, payload } = action;

  switch (type) {
    case AUTH.LOGIN_REQUEST:
      return {
        ...state
      };
    case AUTH.LOGIN_SUCCESS:
      return {
        isLoggedIn: true,
        user: payload,
      };
    case AUTH.REFRESH_TOKEN:
      return {
        ...state,
        isLoggedIn: true,
        user: payload,
      };
    case AUTH.LOGIN_FAILURE:
      return {
        ...state,
        isLoggedIn: false,
        user: payload,
      };
    case AUTH.LOGOUT:
      return {
        ...state,
        isLoggedIn: false,
        user: null,
      };
    case AUTH.REGISTER_REQUEST:
      return {
        ...state,
        loading: true,
      };
    case AUTH.REGISTER_SUCCESS:
      return {
        ...state,
        isLoggedIn: false,
        loading: false,
      };
    case AUTH.REGISTER_FAILURE:
      return {
        ...state,
        loading: false,
        isLoggedIn: false,
      };
    default:
      return state;
  }
}
