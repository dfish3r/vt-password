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
 * <code>PasswordUserIDRule</code> contains methods for determining if a
 * password contains the user id associated with that password. String
 * comparison for the user id are case insensitive.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */

public class PasswordUserIDRule extends AbstractPasswordRule
{

  /** user id to verify. */
  private String userID;

  /** reverse user id to verify. */
  private String reverseUserID;

  /** whether to search for user id backwards. */
  private boolean backwards;

  /** Whether to ignore case when checking for user ids. */
  private boolean ignoreCase;


  /**
   * This creates a new <code>PasswordUserIDRule</code> without
   * supplying a userID.  The userID should be set using the
   * {@link #setUserID(String)} method.
   */
  public PasswordUserIDRule() {}


  /**
   * This will create a new <code>PasswordUserIDRule</code> with the supplied
   * user id.
   *
   * @param  id  <code>String</code>
   */
  public PasswordUserIDRule(final String id)
  {
    this.userID = id;
    this.reverseUserID = new StringBuilder(id).reverse().toString();
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
   */
  public void matchBackwards()
  {
    this.backwards = true;
  }


  /**
   * Bean compatible version of the {@link #matchBackwards()} method.
   *
   * @param  b  <code>boolean</code>
   */
  public void setMatchBackwards(final boolean b)
  {
    this.backwards = b;
  }


  /**
   * Get the value of the matchBackwards property.
   *
   * @return  <code>boolean</code>
   */
  public boolean isMatchBackwards()
  {
    return this.backwards;
  }


  /**
   * This causes the verify method to ignore case when searching the for a user
   * id.
   */
  public void ignoreCase()
  {
    this.ignoreCase = true;
  }


  /**
   * Bean compatible version of the {@link #ignoreCase()} method.
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
  public boolean verifyPassword(final Password password)
  {
    boolean success = false;
    String text = password.getText();
    if (this.ignoreCase) {
      text = text.toLowerCase();
    }
    if (text.indexOf(this.userID) != -1) {
      this.setMessage(
        String.format("Password contains the user id '%s'", this.userID));
    } else if (this.backwards && text.indexOf(this.reverseUserID) != -1) {
      this.setMessage(
        String.format(
          "Password contains the backwards user id '%s'",
          this.reverseUserID));
    } else {
      success = true;
    }
    return success;
  }
}
