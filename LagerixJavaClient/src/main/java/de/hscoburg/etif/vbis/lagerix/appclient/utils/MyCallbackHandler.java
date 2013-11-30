/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hscoburg.etif.vbis.lagerix.appclient.utils;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

/**
 *
 * @author mti578
 */
public class MyCallbackHandler implements CallbackHandler {

 /**
  * Username which will be set in the NameCallback, when NameCallback is handled
  */
 private String username;

 private char[] passwd;
 
 /**
  * Password which will be set in the PasswordCallback, when PasswordCallback is handled
  */
 private String password;

 /**
  * Constructor
  * @param username The username
  * @param password The password
  */
 public MyCallbackHandler(String username, String password) {
  this.username = username;
  this.password = password;
  this.passwd = this.password.toCharArray();
 }

 /** 
  * @param callbacks Instances of Callbacks 
  * @throws IOException IOException 
  * @throws UnsupportedCallbackException If Callback is other than NameCallback or PasswordCallback 
  */ 
 public void handle(Callback callbacks[]) throws IOException, UnsupportedCallbackException {

  for(int i = 0; i < callbacks.length; i++) {
      if(callbacks[i] instanceof NameCallback) {
   NameCallback nc = (NameCallback)callbacks[i];
   nc.setName(username);
      } else if(callbacks[i] instanceof PasswordCallback) {
   PasswordCallback pc = (PasswordCallback)callbacks[i];
   pc.setPassword(passwd);
      } else {
   throw new UnsupportedCallbackException(callbacks[i], "Unrecognized Callback");
      }

  }
 }
}
