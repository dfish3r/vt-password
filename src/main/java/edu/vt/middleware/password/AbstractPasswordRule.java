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
 * <code>AbstractPasswordRule</code> provides a simple implementation of a
 * password rule.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */
public abstract class AbstractPasswordRule implements PasswordRule
{

  /** reason the password failed this rule. */
  private String message;


  /** {@inheritDoc} */
  public abstract boolean verifyPassword(final Password password);


  /** {@inheritDoc} */
  public String getMessage()
  {
    return this.message;
  }


  /** {@inheritDoc} */
  public void setMessage(final String msg)
  {
    this.message = msg;
  }
}
