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
 * <code>PasswordLengthRule</code> contains methods for determining if a
 * password is within a desired length. The minimum and maximum lengths are used
 * inclusively to determine if a password meets this rule.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */

public class PasswordLengthRule extends AbstractPasswordRule
{

  /** Stores the minimum length of a password. */
  private int minimumLength;

  /** Stores the maximum length of a password. */
  private int maximumLength;


  /**
   * This will create a new <code>PasswordLengthRule</code> with the supplied
   * length. Both the minimum and the maximum length will be set to this value.
   *
   * @param  length  <code>int</code> length of password
   */
  public PasswordLengthRule(final int length)
  {
    this.minimumLength = length;
    this.maximumLength = length;
  }


  /**
   * This will create a new <code>PasswordLengthRule</code> with the supplied
   * lengths.
   *
   * @param  minLength  <code>int</code> minimum length of a password
   * @param  maxLength  <code>int</code> maximum length of a password
   */
  public PasswordLengthRule(final int minLength, final int maxLength)
  {
    this.minimumLength = minLength;
    this.maximumLength = maxLength;
  }


  /** {@inheritDoc}. */
  public boolean verifyPassword(final Password password)
  {
    boolean success = false;
    if (password != null) {
      if (
        password.length() >= this.minimumLength &&
          password.length() <= this.maximumLength) {
        success = true;
      } else if (this.minimumLength == this.maximumLength) {
        this.setMessage(
          String.format(
            "Password length must be %s characters",
            this.minimumLength));
      } else {
        this.setMessage(
          String.format(
            "Password length must be greater than or equal to %s " +
            "and less than or equal to %s characters",
            this.minimumLength,
            this.maximumLength));
      }
    } else {
      this.setMessage("Password cannot be null");
    }
    return success;
  }
}
