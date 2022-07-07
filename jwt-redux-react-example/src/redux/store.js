import { createLogger } from 'redux-logger';
import thunk from 'redux-thunk';
import { createStore, applyMiddleware } from 'redux';
import { rootReducer } from './rootReducer';
import { composeWithDevTools } from 'redux-devtools-extension'


const loggerMiddleware = createLogger();

export const store = createStore(
    rootReducer,
    composeWithDevTools( applyMiddleware(
        thunk,
        // loggerMiddleware
    ))
);

export default store;