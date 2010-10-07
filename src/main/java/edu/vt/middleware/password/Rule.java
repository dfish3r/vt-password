/*
  $Id$

  Copyright (C) 2003-2010 Virginia Tech.
  All rights reserved.

  SEE LICENSE FOR MORE INFORMATION

  Author:  Middleware Services
  Email:   middleware@vt.edu
  Version: $Revision$
  Updated: $Date$
*/
package edu.vt.middleware.password;

/**
 * <code>Rule</code> allows custom password rules to be created for
 * determining whether or not a password is strong.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */
public interface Rule
{


  /**
   * This returns whether or not the supplied password meets the requirements of
   * this rule.
   *
   * @param  password  <code>Password</code> to verify (not null).
   *
   * @return  <code>RuleResult</code> - details on password verification
   *
   * @throws  NullPointerException  if the password is null.
   * @throws  UnsupportedOperationException  if the rule does not support
   * validation with these arguments
   */
  RuleResult validate(Password password);


  /**
   * This returns whether or not the supplied username and password meets the
   * requirements of this rule.
   *
   * @param  password  <code>Password</code> to verify (not null).
   * @param  username  <code>Username</code> to verify (not null).
   *
   * @return  <code>RuleResult</code> - details on password verification
   *
   * @throws  NullPointerException  if the username or password is null.
   * @throws  UnsupportedOperationException  if the rule does not support
   * validation with these arguments
   */
  RuleResult validate(Username username, Password password);
}
