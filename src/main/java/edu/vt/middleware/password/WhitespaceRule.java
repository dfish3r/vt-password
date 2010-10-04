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
 * <code>WhitespaceRule</code> contains methods for determining if a password
 * contains whitespace characters.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */

public class WhitespaceRule implements Rule
{


  /** {@inheritDoc} */
  public RuleResult<String> verifyPassword(final Password password)
  {
    final RuleResult<String> result = new RuleResult<String>();
    if (!password.containsWhitespace()) {
      result.setValid(true);
    } else {
      result.setDetails("Password cannot contain whitespace characters");
    }
    return result;
  }
}
