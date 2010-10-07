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
 * <code>LengthRule</code> contains methods for determining if a password is
 * within a desired length. The minimum and maximum lengths are used
 * inclusively to determine if a password meets this rule.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */

public class LengthRule implements Rule
{

  /** Stores the minimum length of a password. */
  private int minimumLength;

  /** Stores the maximum length of a password. */
  private int maximumLength = Integer.MAX_VALUE;


  /**
   * This will create a new <code>LengthRule</code> with lengths unset.
   * The defaults are 0 and Integer.MAX_VALUE respectively.
   */
  public LengthRule() {}


  /**
   * This will create a new <code>LengthRule</code> with the supplied
   * length. Both the minimum and the maximum length will be set to this value.
   *
   * @param  length  <code>int</code> length of password
   */
  public LengthRule(final int length)
  {
    this.minimumLength = length;
    this.maximumLength = length;
  }


  /**
   * This will create a new <code>LengthRule</code> with the supplied
   * lengths.
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
  public RuleResult validate(final Password password)
  {
    final RuleResult result = new RuleResult();
    if (
      password.length() >= this.minimumLength &&
        password.length() <= this.maximumLength) {
      result.setValid(true);
    } else if (this.minimumLength == this.maximumLength) {
      result.setValid(false);
      result.getDetails().add(
        new RuleResultDetail(String.format(
          "Password length must be %s characters",
          this.minimumLength)));
    } else if (this.maximumLength == Integer.MAX_VALUE) {
      result.setValid(false);
      result.getDetails().add(
        new RuleResultDetail(String.format(
          "Password length must be greater than or equal to %s characters",
          this.minimumLength)));
    } else {
      result.setValid(false);
      result.getDetails().add(
        new RuleResultDetail(String.format(
          "Password length must be greater than or equal to %s " +
          "and less than or equal to %s characters",
          this.minimumLength,
          this.maximumLength)));
    }
    return result;
  }


  /** {@inheritDoc} */
  public RuleResult validate(final Username username, final Password password)
  {
    return this.validate(password);
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
