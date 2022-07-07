import React, { Fragment } from 'react';
import { Switch, Router, Route, Redirect } from 'react-router-dom';
import AccessPage from './views/access/AccessPage';
import { history } from './history';
import BlogPage from './views/post/PostPage';
import AppMenu from './views/core/header/HeaderComponent';
import PrivateRoute from './router/PrivateRoute';
import { connect } from 'react-redux';
console.log(process.env)
const App = (props) => {
  return (
    <Fragment>
      <AppMenu />
      <Router history={history}>
          <Switch>
            <Route 
              exact
              path={['/auth/signup', '/auth/login']}>
              <AccessPage />
            </Route>
            <Redirect
              exact
              from="/auth" 
              to="/auth/login"/>

            <PrivateRoute 
              exact 
              authentication={props.authentication} 
              path="/blog" 
              component={BlogPage}
              ></PrivateRoute>
          </Switch>
      </Router>
    </Fragment>
  );
}

const mapState = (state) => {
  const { authentication } = state;
  return {authentication};
}

export default connect(
  mapState,
)(App)
