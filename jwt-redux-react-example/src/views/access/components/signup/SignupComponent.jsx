import React from 'react';
import { useForm } from 'react-hook-form';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import { AUTH, authActions } from '../../../../redux'
import { connect } from 'react-redux';

const SignupComponent = (props) => {
  const {
    register,
    handleSubmit,
    clearErrors,
    getValues,
    formState: { errors },
  } = useForm({ mode: 'onTouched' });

  const onSubmit = (user) => {
    delete user.passwordConfirm;
    props.singup(user);
  }

  return (
    <form>
      {/* First name input */}
      <div>
        <TextField
          label="First name"
          variant="standard"
          type="text"
          id="firstname"
          helperText={errors.firstName ? errors?.firstName?.message : null}
          onChange={(e) => {
            e.onChange(e);
            clearErrors('firstname');
          }}
          {...register('firstname', {
            minLength: { value: 2, message: 'First Name most contain at least 2 characters' },
            required: 'First name is required',
          })}
        />
      </div>

        {/* Last name input */}
        <div>
        <TextField
          label="Last Name"
          variant="standard"
          type="text"
          id="lastname"
          helperText={errors.lastName ? errors?.lastName?.message : null}
          onChange={(e) => {
            e.onChange(e);
            clearErrors('lastname');
          }}
          {...register('lastname', {
            minLength: { value: 2, message: 'Last Name most contain at least 2 characters' },
            required: 'Last name is required',
          })}/>
      </div>

      {/* Email input */}
      <div>
        <TextField
          variant="standard"
          label="Email"
          type="text"
          id="email"
          helperText={errors.email ? errors?.email?.message : null}
          onChange={(e) => {
            e.onChange(e);
            clearErrors('email');
          }}
          {...register('email', {
            pattern: { value: /\S+@\S+\.\S+/, message: 'Email is in invalid format' },
            required: 'Email is required',
          })}
        />
      </div>

      {/* Password input */}
      <div>
        <TextField
          variant="standard"
          label="Password"
          type="password"
          autoComplete="on"
          id="password"
          helperText={errors.password ? errors?.password?.message : null}
          onChange={(e) => {
            e.onChange(e);
            clearErrors('password');
          }}
          {...register('password', {
            required: 'Password is required',
          })}
        />
      </div>
      {/* Password confirm input */}
      <div>
        <TextField
          variant="standard"
          label="Confirm Password"
          type="password"
          autoComplete="on"
          id="passwordConfirm"
          helperText={errors.passwordConfirm ? errors?.passwordConfirm?.message : null}
          onChange={(e) => {
            e.onChange(e);
            clearErrors('passwordConfirm');
          }}
          {...register('passwordConfirm', {
            passwordIsEqual: (value) => value === getValues().password || 'Password not matches!',
            required: 'Password confirm is required',
          })}
        />
      </div>

          <br/>
      <Button type="submit" onClick={handleSubmit(onSubmit)} variant="contained">SUBMIT</Button>
    </form>
  );
};

const mapState = (state) => {
  
}

const mapDispatchToProps = {
  singup: authActions.signup
}

export default connect(
  mapState,
  mapDispatchToProps
)(SignupComponent)
