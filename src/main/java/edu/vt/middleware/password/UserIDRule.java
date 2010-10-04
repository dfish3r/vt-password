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
 * <code>UserIDRule</code> contains methods for determining if a
 * password contains the user id associated with that password.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */

public class UserIDRule implements Rule
{

  /** user id to verify. */
  private String userID;

  /** reverse user id to verify. */
  private String reverseUserID;

  /** whether to search for user id backwards. */
  private boolean matchBackwards;

  /** Whether to ignore case when checking for user ids. */
  private boolean ignoreCase;


  /**
   * This creates a new <code>UserIDRule</code> without supplying a
   * userID. The userID should be set using the {@link #setUserID(String)}
   * method.
   */
  public UserIDRule() {}


  /**
   * This will create a new <code>UserIDRule</code> with the supplied user id.
   *
   * @param  id  <code>String</code>
   */
  public UserIDRule(final String id)
  {
    this.setUserID(id);
  }


  /**
   * This sets the userID for this rule.
   *
   * @param  id  <code>String</code> to set
   */
  public void setUserID(final String id)
  {
    this.userID = id;
    this.reverseUserID = new StringBuilder(id).reverse().toString();
  }


  /**
   * This returns the userID for this rule.
   *
   * @return  <code>String</code>
   */
  public String getUserID()
  {
    return this.userID;
  }


  /**
   * This causes the verify method to search the password for the user id
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
   * This causes the verify method to ignore case when searching the for a user
   * id.
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
  public RuleResult<String> verifyPassword(final Password password)
  {
    final RuleResult<String> result = new RuleResult<String>();
    String text = password.getText();
    if (this.ignoreCase) {
      text = text.toLowerCase();
    }
    if (text.indexOf(this.userID) != -1) {
      result.setDetails(
        String.format("Password contains the user id '%s'", this.userID));
    } else if (this.matchBackwards && text.indexOf(this.reverseUserID) != -1) {
      result.setDetails(
        String.format(
          "Password contains the backwards user id '%s'",
          this.reverseUserID));
    } else {
      result.setValid(true);
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
