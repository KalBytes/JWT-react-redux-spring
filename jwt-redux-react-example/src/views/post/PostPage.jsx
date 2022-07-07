import React from 'react'
import CreatePostComponent from './components/CreatePostComponent'
import PostListComponent from './components/PostListComponent'
import './PostPage.scss'

const PostPage = () => {
  return (
    <div className="post-page">
      <div className="post-page--create-post">
        <CreatePostComponent />
      </div>
      <div className="post-page--post-list">
        <PostListComponent />
      </div>
    </div>
  )
}

export default PostPage;