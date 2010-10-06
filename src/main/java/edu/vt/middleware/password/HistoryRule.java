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

public class HistoryRule extends AbstractDigestRule implements Rule
{

  /** password history. */
  private List<String> history = new ArrayList<String>();


  /**
   * This will create a new <code>HistoryRule</code> with no history.
   */
  public HistoryRule() {}


  /**
   * This will create a new <code>HistoryRule</code> with the supplied
   * history.
   *
   * @param  l  <code>List</code> of password history
   */
  public HistoryRule(final List<String> l)
  {
    this.setHistory(l);
  }


  /**
   * This will return the password history.
   *
   * @return  <code>List</code> of password history
   */
  public List<String> getHistory()
  {
    return this.history;
  }


  /**
   * This will set the password history.
   *
   * @param  l  <code>List</code> of password history
   */
  public void setHistory(final List<String> l)
  {
    this.history = l;
  }


  /** {@inheritDoc} */
  public RuleResult validate(final Password password)
  {
    final RuleResult result = new RuleResult(true);

    if (!this.history.isEmpty()) {
      for (String p : this.history) {
        if (this.digest != null) {
          final String hash = this.digest.digest(
            password.getText().getBytes(),
            this.converter);
          if (p.equals(hash)) {
            result.setValid(false);
            result.getDetails().add(
              new RuleResultDetail(String.format(
                "Password matches one of %s previous passwords",
                this.history.size())));
          }
        } else {
          if (p.equals(password.getText())) {
            result.setValid(false);
            result.getDetails().add(
              new RuleResultDetail(String.format(
                "Password matches one of %s previous passwords",
                this.history.size())));
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
      "%s@%h::history=%s",
      this.getClass().getName(),
      this.hashCode(),
      this.history);
  }
}
