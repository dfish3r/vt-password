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

import java.util.ArrayList;
import java.util.List;

/**
 * <code>HistoryRule</code> contains methods for determining if a
 * password matches one of any previous password a user has chosen. If no
 * password history has been set or an empty history has been set, then
 * passwords will meet this rule.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */

public class HistoryRule extends AbstractDigestRule
  implements Rule<String>
{

  /** password history. */
  private List<String> history = new ArrayList<String>();


  /**
   * This will add the supplied password to the list of history passwords.
   *
   * @param  password  <code>String</code> to add to history
   */
  public void addHistory(final String password)
  {
    if (password != null) {
      this.history.add(password);
    }
  }


  /**
   * This will add the supplied passwords to the list of history passwords.
   *
   * @param  passwords  <code>String[]</code> to add to history
   */
  public void addHistory(final String[] passwords)
  {
    if (passwords != null) {
      for (String s : passwords) {
        this.addHistory(s);
      }
    }
  }


  /**
   * This will add the supplied passwords to the list of history passwords.
   *
   * @param  passwords  <code>List</code> to add to history
   */
  public void addHistory(final List<String> passwords)
  {
    if (passwords != null) {
      for (String s : passwords) {
        this.addHistory(s);
      }
    }
  }


  /** {@inheritDoc} */
  public RuleResult<String> verifyPassword(final Password password)
  {
    final RuleResult<String> result = new RuleResult<String>();

    if (this.history.size() == 0) {
      result.setValid(true);
    } else {
      for (String p : this.history) {
        if (this.digest != null) {
          final String hash = this.digest.digest(
            password.getText().getBytes(),
            this.converter);
          if (p.equals(hash)) {
            result.setValid(false);
            result.setDetails(
              String.format(
                "Password matches one of %s previous passwords",
                this.history.size()));
            break;
          } else {
            result.setValid(true);
          }
        } else {
          if (p.equals(password.getText())) {
            result.setValid(false);
            result.setDetails(
              String.format(
                "Password matches one of %s previous passwords",
                this.history.size()));
            break;
          } else {
            result.setValid(true);
          }
        }
      }
    }
    return result;
  }


  /**
   * This returns a string representation of this object.
   *
   * @return  <code>String</code>
   */
  @Override
  public String toString()
  {
    return
    String.format(
      "%s@%d::history=%s",
      this.getClass().getName(),
      this.hashCode(),
      this.history);
  }
}
