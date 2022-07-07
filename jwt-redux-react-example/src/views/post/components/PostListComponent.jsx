import { Card, CardActions, CardContent, Typography, Button } from '@mui/material';
import React, {useEffect} from 'react';
import { postActions } from '../../../redux';
import { connect } from 'react-redux';

const PostListComponent = (props) => {
  const { posts, getAllPosts } = props;

  useEffect(() => {
    getAllPosts();
  }, []);

  return (
    <div>
      {posts && (
        <React.Fragment>
        {posts.map((post, index) => 
          <Card  key={post.id} sx={{ maxWidth: 345 }}>
          <CardContent>
            <Typography gutterBottom variant="h5" component="div">
              {post.title}
            </Typography>
            <Typography variant="body2" color="text.secondary">
              {post.text}
            </Typography>
          </CardContent>
          <CardActions>
            <Button size="small">Share</Button>
            <Button size="small">Learn More</Button>
          </CardActions>
        </Card>
        )}
        </React.Fragment>
      )}
    </div>
  );
}

const mapState = (state) => {
  const { posts } = state.post;
  return { posts };
};

const mapDispatchToProps = {
  getAllPosts: postActions.getAllPosts,
};

export default connect(mapState, mapDispatchToProps)(PostListComponent);
