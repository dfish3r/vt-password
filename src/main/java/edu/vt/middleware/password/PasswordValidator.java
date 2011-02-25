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
 * <code>PasswordValidator</code> provides methods to running rule validation
 * against password data.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */

public final class PasswordValidator
{


  /** Default constructor. */
  private PasswordValidator() {}


  /**
   * Validates the supplied password data against the supplied rule.
   *
   * @param  rule  <code>Rule</code> to validate password with
   * @param  passwordData  <code>PasswordData</code> to validate
   *
   * @return  <code>RuleResult</code>
   */
  public static RuleResult validate(
    final Rule rule,
    final PasswordData passwordData)
  {
    return rule.validate(passwordData);
  }
}
