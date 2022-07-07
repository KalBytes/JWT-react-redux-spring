import React, { Component } from 'react'
import { Route } from 'react-router';
import  "./AccessPage.scss";
import SignupComponent from './components/signup/SignupComponent';
import LoginComponent from './components/login/LoginComponent';
 

export default class AccessPage extends Component {
  render() {
    return (
      <div className="access-page">
        <h1>Access page</h1>
        <Route path="/auth/signup" component={SignupComponent} exact/>
        <Route path="/auth/login" component={LoginComponent} exact/>
      </div>
    );
  }
}
