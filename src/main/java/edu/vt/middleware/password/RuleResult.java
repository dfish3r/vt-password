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
 * <code>RuleResult</code> provides common implementation for password rule
 * result implementations.
 *
 * @param <T> type of password rule result details
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */
public class RuleResult<T>
{

  /** whether password rule was successful. */
  protected boolean valid;

  /** details associated with a password rule result. */
  protected T details;



  /** {@inheritDoc} */
  public boolean isValid()
  {
    return this.valid;
  }


  /** {@inheritDoc} */
  public void setValid(final boolean b)
  {
    this.valid = b;
  }


  /** {@inheritDoc} */
  public T getDetails()
  {
    return this.details;
  }


  /** {@inheritDoc} */
  public void setDetails(final T t)
  {
    this.details = t;
  }
}
