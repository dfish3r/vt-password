/*
  $Id$

  Copyright (C) 2003-2011 Virginia Tech.
  All rights reserved.

  SEE LICENSE FOR MORE INFORMATION

  Author:  Middleware Services
  Email:   middleware@vt.edu
  Version: $Revision$
  Updated: $Date$
*/
package edu.vt.middleware.password;

/**
 * <code>RuleResultDetail</code> provides common implementation for password
 * rule result detail implementations.
 *
 * @author  Middleware Services
 * @version  $Revision: 1614 $ $Date: 2010-09-20 15:02:39 -0400 (Mon, 20 Sep 2010) $
 */
public class RuleResultDetail
{

  /** message associated with a password rule result detail. */
  protected String message;


  /** Default constructor. */
  public RuleResultDetail() {}


  /**
   * Creates a new <code>RuleResultDetail</code> with the supplied message.
   *
   * @param  s  detail message
   */
  public RuleResultDetail(final String s)
  {
    this.message = s;
  }


  /**
   * Returns the message associated with this detail. May be null.
   *
   * @return  rule result detail message
   */
  public String getMessage()
  {
    return this.message;
  }


  /**
   * Sets the message associated with this detail.
   *
   * @param  s  detail message
   */
  public void setMessage(final String s)
  {
    this.message = s;
  }
}
