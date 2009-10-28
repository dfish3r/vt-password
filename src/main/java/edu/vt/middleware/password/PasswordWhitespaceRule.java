/*
  $Id$

  Copyright (C) 2003-2008 Virginia Tech.
  All rights reserved.

  SEE LICENSE FOR MORE INFORMATION

  Author:  Middleware Services
  Email:   middleware@vt.edu
  Version: $Revision$
  Updated: $Date$
*/
package edu.vt.middleware.password;

/**
 * <code>PasswordWhitespaceRule</code> contains methods for determining if a
 * password contains whitespace characters.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */

public class PasswordWhitespaceRule extends AbstractPasswordRule
{


  /** {@inheritDoc} */
  public boolean verifyPassword(final Password password)
  {
    boolean success = false;
    if (password != null) {
      if (!password.containsWhitespace()) {
        success = true;
      } else {
        this.setMessage("Password cannot contain whitespace characters");
      }
    } else {
      this.setMessage("Password cannot be null");
    }
    return success;
  }
}
