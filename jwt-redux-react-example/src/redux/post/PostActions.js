import { postService } from "../../services";
import { postsConstants } from "./types";

const createPost = (post) => {
  return (dispatch) => {
    dispatch({ 
      type: postsConstants.CREATE_POST_REQUEST 
    });
    postService.createPost(post).then(
      (post) => {
        console.log(post)
        dispatch({ 
          type: postsConstants.CREATE_POST_SUCCESS,
          payload: post
        });
      },
      (error) => {
        dispatch({ 
          type: postsConstants.CREATE_POST_FAILURE 
        });
      },
    );
  };
}

const getAllPosts = () => {
  return (dispatch) => {
    dispatch({ 
      type: postsConstants.GET_POSTS_REQUEST 
    });
    postService.getAllPosts().then(
      (posts) => {
        dispatch({ 
          type: postsConstants.GET_POSTS_SUCCESS,
          payload: posts
        });
      },
      (error) => {
        dispatch({ 
          type: postsConstants.GET_POSTS_FAILURE 
        });
      },
    );
  };
}

export const postActions = {
  createPost,
  getAllPosts
}
