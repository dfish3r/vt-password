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
 * @param <T> type of password rule result details
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */
public interface Rule<T>
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
   */
  RuleResult<T> verifyPassword(Password password);
}
