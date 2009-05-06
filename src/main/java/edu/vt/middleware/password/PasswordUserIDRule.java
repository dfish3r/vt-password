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

/**
 * <code>PasswordUserIDRule</code> contains methods for determining if a
 * password contains the user id associated with that password. String
 * comparision for the user id are case insensitive.
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
   * This will create a new <code>PasswordUserIDRule</code> with the supplied
   * user id.
   *
   * @param  id  <code>String</code>
   */
  public PasswordUserIDRule(final String id)
  {
    this.userID = id;
    this.reverseUserID = new StringBuffer(id).reverse().toString();
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
   * This causes the verify method to ignore case when searching the for a user
   * id.
   */
  public void ignoreCase()
  {
    this.ignoreCase = true;
  }


  /** {@inheritDoc}. */
  public boolean verifyPassword(final Password password)
  {
    boolean success = false;
    if (password != null) {
      String text = password.getText();
      if (this.ignoreCase) {
        text = text.toLowerCase();
      }
      if (text.indexOf(this.userID) != -1) {
        final StringBuffer msg = new StringBuffer(
          "Password contains the user id '").append(this.userID).append("'");
        this.setMessage(msg.toString());
      } else if (this.backwards && text.indexOf(this.reverseUserID) != -1) {
        final StringBuffer msg = new StringBuffer(
          "Password contains the backwards user id '").append(
            this.reverseUserID).append("'");
        this.setMessage(msg.toString());
      } else {
        success = true;
      }
    } else {
      this.setMessage("Password cannot be null");
    }
    return success;
  }
}
