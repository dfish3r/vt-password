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

import java.util.Map;

/**
 * <code>SourceRule</code> contains methods for determining if a password
 * matches a password from a different source. Useful for when separate
 * systems cannot have matching passwords. If no sources have been set or an
 * empty source has been set, then passwords will meet this rule.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */

public class SourceRule extends AbstractDigester implements Rule
{


  /** {@inheritDoc} */
  public RuleResult validate(final Password password)
  {
    throw new UnsupportedOperationException(
      "SourceRule requires a username to perform validation");
  }


  /** {@inheritDoc} */
  public RuleResult validate(final Username username, final Password password)
  {
    final RuleResult result = new RuleResult(true);

    if (!username.getPasswordSources().isEmpty()) {
      for (Map.Entry<String, String> entry :
           username.getPasswordSources().entrySet()) {
        final String p = entry.getValue();
        if (this.digest != null) {
          final String hash = this.digest.digest(
            password.getText().getBytes(),
            this.converter);
          if (p.equals(hash)) {
            result.setValid(false);
            result.getDetails().add(
              new RuleResultDetail(String.format(
                "Password can not be the same as your %s password",
                entry.getKey())));
          }
        } else {
          if (p.equals(password.getText())) {
            result.setValid(false);
            result.getDetails().add(
              new RuleResultDetail(String.format(
                "Password can not be the same as your %s password",
                entry.getKey())));
          }
        }
      }
    }
    return result;
  }
}
