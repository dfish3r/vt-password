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
 * <code>PasswordCharacterRule</code> contains methods for determining if a
 * password contains the desired mix of character types. In order to meet the
 * criteria of this rule, passwords must have any number of the following five
 * characteristics: (The default is to enforce none of the characteristics, so
 * you must explicitly call the set methods you want to enforce.) 1) contain n
 * or more digits 2) contain n or more alphabetical characters 3) contain n or
 * more non-alphanumeric characters 4) contain n or more uppercase characters 5)
 * contain n or more lowercase characters Where n can be set, but the default is
 * 0.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */

public final class PasswordCharacterRule extends AbstractPasswordRule
{

  /** number of rules to enforce. */
  private int numCharacteristics;

  /** whether to require a password contain digits. */
  private boolean requireDigits;

  /** number of digits to require. */
  private int numDigits;

  /** whether to require a password contain alphabetical characters. */
  private boolean requireAlphabetical;

  /** number of alphabetical to require. */
  private int numAlphabetical;

  /** whether to require a password contain non-alphanumeric characters. */
  private boolean requireNonAlphanumeric;

  /** number of non-alphanumerics to require. */
  private int numNonAlphanumeric;

  /** whether to require a password contain uppercase characters. */
  private boolean requireUppercase;

  /** number of uppercase to require. */
  private int numUppercase;

  /** whether to require a password contain lowercase characters. */
  private boolean requireLowercase;

  /** number of lowercase to require. */
  private int numLowercase;


  /**
   * This sets the number of characteristics which must be satisfied in order
   * for a password to meet the requirements of this rule. The default is zero.
   * i.e. you may wish to enforce any three of the five characteristics.
   *
   * @param  n  <code>int</code> number of characteristics to enforce where n >=
   * 0
   */
  public void setNumberOfCharacteristics(final int n)
  {
    if (n >= 0) {
      this.numCharacteristics = n;
    }
  }


  /**
   * This returns the number of characteristics which currently must be
   * satisfied in order for a password to meet the requirements of this rule.
   *
   * @return  <code>int</code> number of characteristics to enforce
   */
  public int getNumberOfCharacteristics()
  {
    return this.numCharacteristics;
  }


  /**
   * This sets the number of digits to require in a password. The default is
   * zero.
   *
   * @param  n  <code>int</code> number of digits to require where n >= 0
   */
  public void setNumberOfDigits(final int n)
  {
    if (n >= 0) {
      this.numDigits = n;
    }
  }


  /**
   * This sets the number of alphabetical characters to require in a password.
   * The default is zero.
   *
   * @param  n  <code>int</code> number of alphabetical characters to require
   * where n >= 0
   */
  public void setNumberOfAlphabetical(final int n)
  {
    if (n >= 0) {
      this.numAlphabetical = n;
    }
  }


  /**
   * This sets the number of non-alphanumeric characters to require in a
   * password. The default is zero.
   *
   * @param  n  <code>int</code> number of non-alphanumeric characters to
   * require where n >= 0
   */
  public void setNumberOfNonAlphanumeric(final int n)
  {
    if (n >= 0) {
      this.numNonAlphanumeric = n;
    }
  }


  /**
   * This sets the number of uppercase characters to require in a password. The
   * default is zero.
   *
   * @param  n  <code>int</code> number of uppercase characters to require where
   * n >= 0
   */
  public void setNumberOfUppercase(final int n)
  {
    if (n >= 0) {
      this.numUppercase = n;
    }
  }


  /**
   * This sets the number of lowercase characters to require in a password. The
   * default is zero.
   *
   * @param  n  <code>int</code> number of lowercase characters to require where
   * n >= 0
   */
  public void setNumberOfLowercase(final int n)
  {
    if (n >= 0) {
      this.numLowercase = n;
    }
  }


  /** {@inheritDoc}. */
  public boolean verifyPassword(final Password password)
  {
    boolean success = false;
    if (password != null) {

      int count = 0;

      // check for digits
      if (this.numDigits > 0) {
        if (password.numberOfDigits() >= this.numDigits) {
          count++;
        }
      }

      // check for alphabetical
      if (this.numAlphabetical > 0) {
        if (password.numberOfAlphabetical() >= this.numAlphabetical) {
          count++;
        }
      }

      // check for non-alphanumeric
      if (this.numNonAlphanumeric > 0) {
        if (password.numberOfNonAlphanumeric() >= this.numNonAlphanumeric) {
          count++;
        }
      }

      // check for uppercase
      if (this.numUppercase > 0) {
        if (password.numberOfUppercase() >= this.numUppercase) {
          count++;
        }
      }

      // check for lowercase
      if (this.numLowercase > 0) {
        if (password.numberOfLowercase() >= this.numLowercase) {
          count++;
        }
      }

      if (count >= this.numCharacteristics) {
        success = true;
      } else {
        final StringBuffer msg = new StringBuffer("Password did not meet ")
            .append(this.numCharacteristics).append(
            " of the following characteristics:\n");
        if (this.numDigits > 0) {
          msg.append("    * must contain at least ").append(this.numDigits)
            .append(" digits\n");
        }
        if (this.numAlphabetical > 0) {
          msg.append("    * must contain at least ").append(
            this.numAlphabetical).append(" alphabetical characters\n");
        }
        if (this.numNonAlphanumeric > 0) {
          msg.append("    * must contain at least ").append(
            this.numNonAlphanumeric).append(" non-alphanumeric characters\n");
        }
        if (this.numUppercase > 0) {
          msg.append("    * must contain at least ").append(this.numUppercase)
            .append(" uppercase characters\n");
        }
        if (this.numLowercase > 0) {
          msg.append("    * must contain at least ").append(this.numLowercase)
            .append(" lowercase characters\n");
        }
        this.setMessage(msg.toString());
      }
    } else {
      this.setMessage("Password cannot be null");
    }
    return success;
  }
}
