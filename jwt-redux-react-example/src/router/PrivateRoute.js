import { Route, Redirect } from 'react-router-dom';

export const PrivateRoute = ({ component: Component, authentication }) => (
  <Route render={props => (
      authentication.isLoggedIn
          ? <Component {...props} />
          : <Redirect to={{ pathname: 'auth/login', state: { from: props.location } }} />
  )} />
)
export default PrivateRoute;