import api from '../common/api'

const createPost = (post) => {
  return api.post(`/post`, post).then(res => res.data);
}

const getAllPosts = (post) => {
  return api.get(`/post`).then(res => res.data);
}

export const postService = {
  createPost,
  getAllPosts
};
