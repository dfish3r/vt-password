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
 * Rule for determining if a password contains whitespace characters.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */
public class WhitespaceRule implements Rule
{


  /** {@inheritDoc} */
  @Override
  public RuleResult validate(final PasswordData passwordData)
  {
    if (!passwordData.getPassword().containsWhitespace()) {
      return new RuleResult(true);
    } else {
      return
        new RuleResult(
          false,
          new RuleResultDetail("ILLEGAL_WHITESPACE", new Object[] {}));
    }
  }
}
