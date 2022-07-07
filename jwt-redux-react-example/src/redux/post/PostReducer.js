
import { postsConstants } from './types';
const initialState = {
  loading: false,
  posts: []
}
export const post = (state = initialState, action) => {
  const { type, payload } = action;
  switch (type) {
    case postsConstants.CREATE_POST_REQUEST:
      return { 
        ...state,
        loading: true,
      };
    case postsConstants.CREATE_POST_SUCCESS:
      return { 
        ...state,
        loading: false,
        posts: [...state.posts, payload]
      };
    case postsConstants.CREATE_POST_FAILURE:
      return { 
        ...state,
        loading: false,
      };
    case postsConstants.GET_POSTS_REQUEST:
      return { 
        ...state,
        loading: false,
      };
    case postsConstants.GET_POSTS_SUCCESS:
      return { 
        ...state,
        loading: false,
        posts: payload
      };
    case postsConstants.GET_POSTS_FAILURE:
      return { 
        ...state,
        loading: false,
      };
    default:
      return state
  }
}