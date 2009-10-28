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

import java.util.ArrayList;
import java.util.List;

/**
 * <code>PasswordHistoryRule</code> contains methods for determining if a
 * password matches one of any previous password a user has choosen. If no
 * password history has been set or an empty history has been set, then
 * passwords will meet this rule.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */

public class PasswordHistoryRule extends AbstractDigestRule
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
  public boolean verifyPassword(final Password password)
  {
    boolean success = false;

    if (password != null) {
      if (this.history.size() == 0) {
        success = true;
      } else {
        for (String p : this.history) {
          if (this.digest != null) {
            final String hash = this.digest.digest(
              password.getText().getBytes(),
              this.converter);
            if (p.equals(hash)) {
              success = false;
              this.setMessage(
                String.format(
                  "Password matches one of %s previous passwords",
                  this.history.size()));
              break;
            } else {
              success = true;
            }
          } else {
            if (p.equals(password.getText())) {
              success = false;
              this.setMessage(
                String.format(
                  "Password matches one of %s previous passwords",
                  this.history.size()));
              break;
            } else {
              success = true;
            }
          }
        }
      }
    } else {
      this.setMessage("Password cannot be null");
    }
    return success;
  }
}
