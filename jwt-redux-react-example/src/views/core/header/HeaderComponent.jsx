import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import { history } from '../../../history';
import { connect } from 'react-redux';
import { authActions } from '../../../redux';

const AppMenu = (props) => {
  const onLogout = () => {
    props.logout();
  }

  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static">
        <Toolbar>
          <IconButton
            size="large"
            edge="start"
            color="inherit"
            aria-label="menu"
            sx={{ mr: 2 }}
          >
          </IconButton>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            <span onClick={() => history.push('/blog')}>Blog</span> 
          </Typography>
          {props.isLoggedIn && <span> {props.user?.email}</span>}
          <Button color="inherit" onClick={() => history.push('/auth/login')}>Login</Button>
          <Button color="inherit" onClick={() => history.push('/auth/signup')}>Sign in</Button>
          {props.isLoggedIn && <Button color="inherit" onClick={onLogout}>Logout</Button>}
        </Toolbar>
      </AppBar>
    </Box>
  );
}

const mapState = (state) => {
  const { isLoggedIn, user } = state.authentication;
  return { isLoggedIn, user };
}

const mapDispatchToProps = {
  logout: authActions.logout
}


export default connect(
  mapState,
  mapDispatchToProps
)(AppMenu)
