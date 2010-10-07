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

public class SourceRule extends AbstractDigester implements Rule
{

  /** password sources. */
  private Map<String, String> sources = new HashMap<String, String>();


  /**
   * This will create a new <code>SourceRule</code> with no sources.
   */
  public SourceRule() {}


  /**
   * This will create a new <code>SourceRule</code> with the supplied
   * sources.
   *
   * @param  m  <code>Map</code> of source to password
   */
  public SourceRule(final Map<String, String> m)
  {
    this.setSources(m);
  }


  /**
   * This will return the password sources.
   *
   * @return  <code>Map</code> of password sources
   */
  public Map<String, String> getSources()
  {
    return this.sources;
  }


  /**
   * This will set the password sources.
   *
   * @param  m  <code>Map</code> of password sources
   */
  public void setSources(final Map<String, String> m)
  {
    this.sources = m;
  }


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
  public RuleResult validate(final Password password)
  {
    final RuleResult result = new RuleResult(true);

    if (!this.sources.isEmpty()) {
      for (Map.Entry<String, String> entry : this.sources.entrySet()) {
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
      "%s@%h::sourcess=%s",
      this.getClass().getName(),
      this.hashCode(),
      this.sources);
  }
}
