/*
  $Id$

  Copyright (C) 2003-2008 Virginia Tech.
  All rights reserved.

  SEE LICENSE FOR MORE INFORMATION

  Author:  Middleware Services
  Email:   middleware@vt.edu
  Version: $Revision$
  Updated: $Date$
*/
package edu.vt.middleware.password;

/**
 * <code>PasswordException</code> is used to determine why a password did not
 * meet the requirements of a password rule.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */

public class PasswordException extends Exception
{
  /** PasswordException.java */
  private static final long serialVersionUID = -8254917606421076833L;


  /** This creates a new <code>PasswordException</code>. */
  public PasswordException() {}


  /**
   * This creates a new <code>PasswordException</code> with the supplied
   * message.
   *
   * @param  msg  <code>String</code>
   */
  public PasswordException(final String msg)
  {
    super(msg);
  }


  /**
   * This creates a new <code>PasswordException</code> with the supplied <code>
   * PasswordRule</code>.
   *
   * @param  rule  <code>PasswordRule</code>
   */
  public PasswordException(final PasswordRule rule)
  {
    super(rule.getMessage());
  }
}
