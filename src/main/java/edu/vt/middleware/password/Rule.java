/*
  $Id$

  Copyright (C) 2003-2013 Virginia Tech.
  All rights reserved.

  SEE LICENSE FOR MORE INFORMATION

  Author:  Middleware Services
  Email:   middleware@vt.edu
  Version: $Revision$
  Updated: $Date$
*/
package edu.vt.middleware.password;

/**
 * Interface for password strength rules.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */
public interface Rule
{


  /**
   * Validates the supplied password data per the requirements of this rule.
   *
   * @param  passwordData  to verify (not null).
   *
   * @return  details on password verification
   *
   * @throws  NullPointerException  if the rule data is null.
   */
  RuleResult validate(PasswordData passwordData);
}
