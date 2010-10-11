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
 * <code>HistoryRule</code> contains methods for determining if a
 * password matches one of any previous password a user has chosen. If no
 * password history has been set or an empty history has been set, then
 * passwords will meet this rule.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */
public class HistoryRule extends AbstractDigester implements Rule
{


  /** {@inheritDoc} */
  public RuleResult validate(final PasswordData passwordData)
  {
    final RuleResult result = new RuleResult(true);

    if (!passwordData.getUsername().getPasswordHistory().isEmpty()) {
      for (String p : passwordData.getUsername().getPasswordHistory()) {
        if (this.digest != null) {
          final String hash = this.digest.digest(
            passwordData.getPassword().getText().getBytes(),
            this.converter);
          if (p.equals(hash)) {
            result.setValid(false);
            result.getDetails().add(
              new RuleResultDetail(String.format(
                "Password matches one of %s previous passwords",
                passwordData.getUsername().getPasswordHistory().size())));
          }
        } else {
          if (p.equals(passwordData.getPassword().getText())) {
            result.setValid(false);
            result.getDetails().add(
              new RuleResultDetail(String.format(
                "Password matches one of %s previous passwords",
                passwordData.getUsername().getPasswordHistory().size())));
          }
        }
      }
    }
    return result;
  }
}
