/*
  $Id$

  Copyright (C) 2003-2011 Virginia Tech.
  All rights reserved.

  SEE LICENSE FOR MORE INFORMATION

  Author:  Middleware Services
  Email:   middleware@vt.edu
  Version: $Revision$
  Updated: $Date$
*/
package edu.vt.middleware.password;

/**
 * Rule for determining if a password contains the username associated with that
 * password.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */
public class UsernameRule implements Rule
{

  /** Error code for matching username. */
  public static final String ERROR_CODE = "ILLEGAL_USERNAME";

  /** Error code for matching reversed dictionary word. */
  public static final String ERROR_CODE_REVERSED = "ILLEGAL_USERNAME_REVERSED";

  /** Whether to search for username backwards. */
  private boolean matchBackwards;

  /** Whether to ignore case when checking for usernames. */
  private boolean ignoreCase;


  /** Default constructor. */
  public UsernameRule() {}


  /**
   * Create a new username rule.
   *
   * @param  mb  whether to match backwards
   * @param  ic  whether to ignore case
   */
  public UsernameRule(final boolean mb, final boolean ic)
  {
    this.setMatchBackwards(mb);
    this.setIgnoreCase(ic);
  }


  /**
   * Sets whether the verify method will search the password for the username
   * spelled backwards as well as forwards.
   *
   * @param  b  whether to match username backwards
   */
  public void setMatchBackwards(final boolean b)
  {
    this.matchBackwards = b;
  }


  /**
   * Returns whether to match the username backwards.
   *
   * @return  whether to match username backwards
   */
  public boolean isMatchBackwards()
  {
    return this.matchBackwards;
  }


  /**
   * Sets whether the verify method will ignore case when searching the for a
   * username.
   *
   * @param  b  whether to ignore case
   */
  public void setIgnoreCase(final boolean b)
  {
    this.ignoreCase = b;
  }


  /**
   * Returns whether to ignore the case of the username.
   *
   * @return  whether to ignore case
   */
  public boolean isIgnoreCase()
  {
    return ignoreCase;
  }


  /** {@inheritDoc} */
  public RuleResult validate(final PasswordData passwordData)
  {
    final RuleResult result = new RuleResult(true);
    String text = passwordData.getPassword().getText();
    String user = passwordData.getUsername();
    String reverseUser = new StringBuilder(user).reverse().toString();
    if (this.ignoreCase) {
      text = text.toLowerCase();
      user = user.toLowerCase();
      reverseUser = reverseUser.toLowerCase();
    }
    if (text.indexOf(user) != -1) {
      result.setValid(false);
      result.getDetails().add(
        new RuleResultDetail(ERROR_CODE, new Object[]{user}));
    }
    if (this.matchBackwards && text.indexOf(reverseUser) != -1) {
      result.setValid(false);
      result.getDetails().add(
        new RuleResultDetail(ERROR_CODE_REVERSED, new Object[]{user}));
    }
    return result;
  }


  /**
   * Returns a string representation of this object.
   *
   * @return  string representation
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
