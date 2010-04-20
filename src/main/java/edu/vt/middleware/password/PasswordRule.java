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
 * <code>PasswordRule</code> allows custom password rules to be created for
 * determining whether or not a password is strong.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */
public interface PasswordRule
{


  /**
   * This returns whether or not the supplied password meets the requirements of
   * this rule.
   *
   * @param  password  <code>Password</code> to verify
   *
   * @return  <code>boolean</code> - whether password passes the rule
   */
  boolean verifyPassword(Password password);


  /**
   * This returns the reason the supplied password did not meet the requirements
   * of this rule. Returns null if all requirements have been met.
   *
   * @return  <code>String</code> - the reason the supplied password did not
   * meet the requirements of this rule
   */
  String getMessage();


  /**
   * This sets the reason the supplied password did not meet the requirements of
   * this rule.
   *
   * @param  msg  <code>String</code> - the reason the supplied password did not
   * meet the requirements of this rule
   */
  void setMessage(String msg);
}
