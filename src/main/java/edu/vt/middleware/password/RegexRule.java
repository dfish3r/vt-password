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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <code>RegexRule</code> contains methods for testing a regular expression to
 * determine if a password is valid.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */
public class RegexRule implements Rule
{
  /** Regex pattern. */
  protected final Pattern pattern;


  /**
   * This creates a new <code>RegexRule</code> with the supplied regular
   * expression.
   *
   * @param  regex  <code>String</code> regular expression
   */
  public RegexRule(final String regex)
  {
    this.pattern = Pattern.compile(regex);
  }


  /** {@inheritDoc} */
  public RuleResult validate(final PasswordData passwordData)
  {
    final RuleResult result = new RuleResult(true);
    final Matcher m = this.pattern.matcher(
      passwordData.getPassword().getText());
    if (m.find()) {
      result.setValid(false);
      result.getDetails().add(
        new RuleResultDetail(this.getRuleResultDetail(m)));
    }
    return result;
  }


  /**
   * Returns the message to set on the rule result detail.
   *
   * @param  m  <code>Matcher</code> that found a match
   * @return  <code>String</code> result detail
   */
  protected String getRuleResultDetail(final Matcher m)
  {
    return String.format(
      "Password contains the regular expression match '%s'", m.group());
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
        "%s@%h::pattern=%s",
        this.getClass().getName(),
        this.hashCode(),
        this.pattern);
  }
}
