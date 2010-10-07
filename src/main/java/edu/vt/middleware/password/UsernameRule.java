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
 * <code>UsernameRule</code> contains methods for determining if a
 * password contains the username associated with that password.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */

public class UsernameRule implements Rule
{

  /** whether to search for username backwards. */
  private boolean matchBackwards;

  /** Whether to ignore case when checking for usernames. */
  private boolean ignoreCase;


  /**
   * This creates a new <code>UsernameRule</code>.
   */
  public UsernameRule() {}


  /**
   * This will create a new <code>UsernameRule</code> with the supplied
   * matchBackwards and ignoreCase settings.
   *
   * @param  mb  <code>boolean</code> whether to match backwards
   * @param  ic  <code>boolean</code> whether to ignore case
   */
  public UsernameRule(final boolean mb, final boolean ic)
  {
    this.setMatchBackwards(mb);
    this.setIgnoreCase(ic);
  }


  /**
   * This causes the verify method to search the password for the username
   * spelled backwards as well as forwards.
   *
   * @param  b  <code>boolean</code>
   */
  public void setMatchBackwards(final boolean b)
  {
    this.matchBackwards = b;
  }


  /**
   * Get the value of the matchBackwards property.
   *
   * @return  <code>boolean</code>
   */
  public boolean isMatchBackwards()
  {
    return this.matchBackwards;
  }


  /**
   * This causes the verify method to ignore case when searching the for a
   * username.
   *
   * @param  b  <code>boolean</code>
   */
  public void setIgnoreCase(final boolean b)
  {
    this.ignoreCase = b;
  }


  /**
   * Get the value of the ignoreCase property.
   *
   * @return  <code>boolean</code>
   */
  public boolean isIgnoreCase()
  {
    return ignoreCase;
  }


  /** {@inheritDoc} */
  public RuleResult validate(final Password password)
  {
    throw new UnsupportedOperationException(
      "UsernameRule requires a username to perform validation");
  }




  /** {@inheritDoc} */
  public RuleResult validate(final Username username, final Password password)
  {
    final RuleResult result = new RuleResult(true);
    String text = password.getText();
    String user = username.getText();
    String reverseUser = new StringBuilder(user).reverse().toString();
    if (this.ignoreCase) {
      text = text.toLowerCase();
      user = user.toLowerCase();
      reverseUser = reverseUser.toLowerCase();
    }
    if (text.indexOf(user) != -1) {
      result.setValid(false);
      result.getDetails().add(
        new RuleResultDetail(
          String.format("Password contains the user id '%s'", user)));
    }
    if (this.matchBackwards && text.indexOf(reverseUser) != -1) {
      result.setValid(false);
      result.getDetails().add(
        new RuleResultDetail(String.format(
          "Password contains the backwards user id '%s'", reverseUser)));
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
      "%s@%h::ignoreCase=%s,matchBackwards=%s",
      this.getClass().getName(),
      this.hashCode(),
      this.ignoreCase,
      this.matchBackwards);
  }
}
