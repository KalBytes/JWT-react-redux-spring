import React from 'react';
import { useForm } from 'react-hook-form';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import { authActions } from '../../../../redux'
import { connect } from 'react-redux';

const LoginComponent = (props) => {
  const {
    register,
    handleSubmit,
    clearErrors,
    formState: { errors },
  } = useForm({ mode: 'onTouched' });

  const onSubmit = (loginData) => {
    const { email, password } = loginData;
    props.login(email, password);
  }

  return (
    <form>
      {props.status && <span>{props.status}.</span>}
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
        <br/>
      <Button type="submit" onClick={handleSubmit(onSubmit)} variant="contained">SUBMIT</Button>
    </form>
  );
};

const mapState = (state) => {
  const { loggedIn } = state.authentication;
  return { loggedIn };
}

const mapDispatchToProps = {
  login: authActions.login
}

export default connect(
  mapState,
  mapDispatchToProps
)(LoginComponent)
