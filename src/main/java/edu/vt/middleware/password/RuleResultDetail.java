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

import java.util.Arrays;

/**
 * Describes an exact cause of a rule validation failure.
 *
 * @author  Middleware Services
 * @version  $Revision: 1614 $ $Date: 2010-09-20 15:02:39 -0400 (Mon, 20 Sep 2010) $
 */
public class RuleResultDetail
{

  /** Detail error code. */
  protected final String errorCode;

  /**
   * Additional parameters that provide information about validation failure.
   */
  protected final Object[] parameters;


  /**
   * Creates a new rule result detail.
   *
   * @param  code  error code.
   * @param  params  error details.
   */
  public RuleResultDetail(final String code, final Object[] params)
  {
    if (code == null || code.length() == 0) {
      throw new IllegalArgumentException("Code cannot be null or empty.");
    }
    errorCode = code;
    parameters = params;
  }


  /**
   * Returns the error code.
   *
   * @return  error code.
   */
  public String getErrorCode()
  {
    return errorCode;
  }


  /**
   * Returns the parameters.
   *
   * @return  array of parameters or empty array if no parameters defined.
   */
  public Object[] getParameters()
  {
    return parameters;
  }


  /**
   * Returns a string representation of this object.
   *
   * @return  string representation
   */
  @Override
  public String toString()
  {
    return
      String.format(
        "%s:%s",
        errorCode,
        parameters != null ? Arrays.asList(parameters) : null);
  }
}
