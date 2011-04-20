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
 * <code>LengthRule</code> contains methods for determining if a password is
 * within a desired length. The minimum and maximum lengths are used inclusively
 * to determine if a password meets this rule.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */
public class LengthRule implements Rule
{

  /** Error code for password too short. */
  public static final String ERROR_CODE_MIN = "TOO_SHORT";

  /** Error code for password too long. */
  public static final String ERROR_CODE_MAX = "TOO_LONG";

  /** Stores the minimum length of a password. */
  private int minimumLength;

  /** Stores the maximum length of a password. */
  private int maximumLength = Integer.MAX_VALUE;


  /**
   * This will create a new <code>LengthRule</code> with lengths unset. The
   * defaults are 0 and Integer.MAX_VALUE respectively.
   */
  public LengthRule() {}


  /**
   * This will create a new <code>LengthRule</code> with the supplied length.
   * Both the minimum and the maximum length will be set to this value.
   *
   * @param  length  <code>int</code> length of password
   */
  public LengthRule(final int length)
  {
    this.minimumLength = length;
    this.maximumLength = length;
  }


  /**
   * This will create a new <code>LengthRule</code> with the supplied lengths.
   *
   * @param  minLength  <code>int</code> minimum length of a password
   * @param  maxLength  <code>int</code> maximum length of a password
   */
  public LengthRule(final int minLength, final int maxLength)
  {
    this.minimumLength = minLength;
    this.maximumLength = maxLength;
  }


  /**
   * Set the minimum password length.
   *
   * @param  minLength  <code>int</code> minimum length of a password
   */
  public void setMinimumLength(final int minLength)
  {
    this.minimumLength = minLength;
  }


  /**
   * Get the minimum password length.
   *
   * @return  <code>int</code>
   */
  public int getMinimumLength()
  {
    return minimumLength;
  }


  /**
   * Set the maximum password length.
   *
   * @param  maxLength  <code>int</code> maximum length of a password
   */
  public void setMaximumLength(final int maxLength)
  {
    this.maximumLength = maxLength;
  }


  /**
   * Get the maximum password length.
   *
   * @return  <code>int</code>
   */
  public int getMaximumLength()
  {
    return maximumLength;
  }


  /** {@inheritDoc} */
  public RuleResult validate(final PasswordData passwordData)
  {
    final RuleResult result = new RuleResult();
    final int length = passwordData.getPassword().length();
    if (length >= this.minimumLength && length <= this.maximumLength) {
      result.setValid(true);
    } else {
      result.setValid(false);
      if (length < this.minimumLength) {
        result.getDetails().add(
          new RuleResultDetail(
            ERROR_CODE_MIN, new Object[]{this.minimumLength}));
      } else {
        result.getDetails().add(
          new RuleResultDetail(
            ERROR_CODE_MAX, new Object[]{this.maximumLength}));
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
        "%s@%h::minimumLength=%s,maximumLength=%s",
        this.getClass().getName(),
        this.hashCode(),
        this.minimumLength,
        this.maximumLength);
  }
}
