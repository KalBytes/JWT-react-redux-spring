import { combineReducers } from 'redux';
import { authentication, post } from './index';

export const rootReducer = combineReducers({
  authentication,
  post
});

