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

import java.util.HashMap;
import java.util.Map;

/**
 * <code>SourceRule</code> contains methods for determining if a password
 * matches a password from a different source. Useful for when separate
 * systems cannot have matching passwords.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */

public class SourceRule extends AbstractDigestRule
  implements Rule<String>
{

  /** password sources. */
  private Map<String, String> sources = new HashMap<String, String>();


  /**
   * This will add the supplied password as a password source.
   *
   * @param  source  <code>String</code> label
   * @param  password  <code>String</code> to add
   */
  public void addSource(final String source, final String password)
  {
    if (source == null) {
      throw new NullPointerException("Source cannot be null");
    }
    if (password == null) {
      throw new NullPointerException("Password cannot be null");
    }
    this.sources.put(source, password);
  }


  /** {@inheritDoc} */
  public RuleResult<String> verifyPassword(final Password password)
  {
    final RuleResult<String> result = new RuleResult<String>();

    if (this.sources.size() == 0) {
      result.setValid(true);
    } else {
      for (Map.Entry<String, String> entry : this.sources.entrySet()) {
        final String p = entry.getValue();
        if (this.digest != null) {
          final String hash = this.digest.digest(
            password.getText().getBytes(),
            this.converter);
          if (p.equals(hash)) {
            result.setValid(false);
            result.setDetails(
              String.format(
                "Password can not be the same as your %s password",
                entry.getKey()));
            break;
          } else {
            result.setValid(true);
          }
        } else {
          if (p.equals(password.getText())) {
            result.setDetails(
              String.format(
                "Password can not be the same as your %s password",
                entry.getKey()));
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
      "%s@%d::sourcess=%s",
      this.getClass().getName(),
      this.hashCode(),
      this.sources);
  }
}
