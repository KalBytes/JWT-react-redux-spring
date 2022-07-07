import { TextField } from '@mui/material';
import React from 'react';
import { useForm } from 'react-hook-form';
import Button from '@mui/material/Button';
import './CreatePostComponent.scss';
import { connect } from 'react-redux';
import { postActions } from '../../../redux/post/PostActions';

const CreatePostComponent = (props) => {
  const {
    register,
    handleSubmit,
    clearErrors,
    formState: { errors },
  } = useForm({ mode: 'onTouched' });

  const onSubmit = (postData) => {
    props.createPost(postData);
  };

  return (
    <div>
      <form className="create-form">
        <div className="form-element">
          <TextField
            variant="standard"
            label="Title"
            type="text"
            id="title"
            helperText={errors.title ? errors?.title?.message : null}
            onChange={(e) => {
              e.onChange(e);
              clearErrors('title');
            }}
            {...register('title', {
              required: 'title is required',
            })}
          />
        </div>

        <div className="form-element">
          <TextField
            multiline
            rows={4}
            label="Text"
            type="text"
            id="text"
            helperText={errors.text ? errors?.text?.message : null}
            onChange={(e) => {
              e.onChange(e);
              clearErrors('text');
            }}
            {...register('text', {
              required: 'text is required',
            })}
          />
        </div>
        <br />
        <div>
        <Button type="submit" onClick={handleSubmit(onSubmit)} variant="contained">
          SUBMIT
        </Button>
        </div>
      </form>
    </div>
  );
}

const mapState = (state) => {
  return {};
};


const mapDispatchToProps = {
  createPost: postActions.createPost,
};

export default connect(mapState, mapDispatchToProps)(CreatePostComponent);
