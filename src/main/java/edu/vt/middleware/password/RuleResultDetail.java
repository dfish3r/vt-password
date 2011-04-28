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

import java.util.LinkedHashMap;
import java.util.Map;

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
  protected final Map<String, ?> parameters;


  /**
   * Creates a new rule result detail.
   *
   * @param  code  error code.
   * @param  params  error details.
   */
  public RuleResultDetail(final String code, final Map<String, ?> params)
  {
    if (code == null || code.length() == 0) {
      throw new IllegalArgumentException("Code cannot be null or empty.");
    }
    errorCode = code;
    if (params == null) {
      parameters = new LinkedHashMap<String, Object>();
    } else {
      parameters = new LinkedHashMap<String, Object>(params);
    }
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
   * @return  map of parameter name to value.
   */
  public Map<String, ?> getParameters()
  {
    return parameters;
  }


  /**
   * Returns the parameter values.
   *
   * @return  array of parameters or empty array if no parameters defined.
   */
  public Object[] getValues()
  {
    return parameters.values().toArray(new Object[parameters.size()]);
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
        parameters);
  }
}
