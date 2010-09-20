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



  /** Default constructor. */
  public RuleResult() {}


  /**
   * Creates a new <code>RuleResult</code> with the supplied validity and
   * details.
   *
   * @param  b  result validity
   * @param  t  details associated with this result
   */
  public RuleResult(final boolean b, final T t)
  {
    this.valid = b;
    this.details = t;
  }


  /**
   * Returns whether the result of the rule verification is a valid password.
   *
   * @return  valid password for this rule
   */
  public boolean isValid()
  {
    return this.valid;
  }


  /**
   * Sets whether the result of the rule verification is a valid password.
   *
   * @param  b  valid password for this rule
   */
  public void setValid(final boolean b)
  {
    this.valid = b;
  }


  /**
   * Returns any details associated with the rule verification. May be null.
   *
   * @return  T  rule result details
   */
  public T getDetails()
  {
    return this.details;
  }


  /**
   * Sets any details associated with the rule verification.
   *
   * @param  t  rule result details
   */
  public void setDetails(final T t)
  {
    this.details = t;
  }
}
