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
 * Class for determining what type and what quantity of characters a password
 * contains.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */
public class Password
{

  /** Stores the password. */
  private String password;

  /** Digits in the password [0-9]. */
  private StringBuilder digits;

  /** Non-Digits in the password ![0-9]. */
  private StringBuilder nonDigits;

  /** Alphabetical characters in the password [a-zA-Z]. */
  private StringBuilder alphabetical;

  /** Non-Alphabetical characters in the password ![a-zA-Z]. */
  private StringBuilder nonAlphabetical;

  /** Alphanumeric characters in the password [a-zA-Z0-9]. */
  private StringBuilder alphanumeric;

  /** Non-Alphanumeric characters in the password ![a-zA-Z0-9]. */
  private StringBuilder nonAlphanumeric;

  /** Uppercase characters in the password [A-Z]. */
  private StringBuilder uppercase;

  /** Lowercase characters in the password [a-z]. */
  private StringBuilder lowercase;

  /** Whitespace characters in the password [\s]. */
  private StringBuilder whitespace;


  /**
   * Create a new password with the supplied password text.
   *
   * @param  text  password
   */
  public Password(final String text)
  {
    this.password = text;

    this.digits = new StringBuilder(this.password.length());
    this.nonDigits = new StringBuilder(this.password.length());
    this.alphabetical = new StringBuilder(this.password.length());
    this.nonAlphabetical = new StringBuilder(this.password.length());
    this.alphanumeric = new StringBuilder(this.password.length());
    this.nonAlphanumeric = new StringBuilder(this.password.length());
    this.uppercase = new StringBuilder(this.password.length());
    this.lowercase = new StringBuilder(this.password.length());
    this.whitespace = new StringBuilder(this.password.length());

    for (int i = 0; i < this.password.length(); i++) {
      final char c = this.password.charAt(i);
      if (Character.isDigit(c)) {
        this.digits.append(c);
        this.alphanumeric.append(c);
        this.nonAlphabetical.append(c);
      } else if (Character.isLetter(c)) {
        this.nonDigits.append(c);
        this.alphanumeric.append(c);
        this.alphabetical.append(c);
        if (Character.isUpperCase(c)) {
          this.uppercase.append(c);
        } else if (Character.isLowerCase(c)) {
          this.lowercase.append(c);
        }
      } else {
        if (Character.isWhitespace(c)) {
          this.whitespace.append(c);
        }
        this.nonDigits.append(c);
        this.nonAlphanumeric.append(c);
        this.nonAlphabetical.append(c);
      }
    }
  }


  /**
   * Returns the text of this password.
   *
   * @return  password
   */
  public String getText()
  {
    return this.password;
  }


  /**
   * Returns the length of this password.
   *
   * @return  password length
   */
  public int length()
  {
    return this.password.length();
  }


  /**
   * Returns whether or not this password contains digits.
   *
   * @return  whether or not the password contains digits
   */
  public boolean containsDigits()
  {
    return this.digits.length() > 0;
  }


  /**
   * Returns the number of digits in this password.
   *
   * @return  number of digits in the password
   */
  public int getNumberOfDigits()
  {
    return this.digits.length();
  }


  /**
   * Returns the digits in this password.
   *
   * @return  digits in this password
   */
  public char[] getDigits()
  {
    char[] array = null;
    if (this.digits != null && this.digits.length() > 0) {
      array = this.digits.toString().toCharArray();
    }
    return array;
  }


  /**
   * Returns whether or not this password contains non-digits.
   *
   * @return  whether or not the password contains non-digits
   */
  public boolean containsNonDigits()
  {
    return this.nonDigits.length() > 0;
  }


  /**
   * Returns the number of non-digits in this password.
   *
   * @return  number of non-digits in this password
   */
  public int getNumberOfNonDigits()
  {
    return this.nonDigits.length();
  }


  /**
   * Returns the non-digits in this password.
   *
   * @return  non-digits in this password
   */
  public char[] getNonDigits()
  {
    char[] array = null;
    if (this.nonDigits != null && this.nonDigits.length() > 0) {
      array = this.nonDigits.toString().toCharArray();
    }
    return array;
  }


  /**
   * Returns whether or not this password contains alphabetical characters.
   *
   * @return  whether or not the password contains alphabetical characters
   */
  public boolean containsAlphabetical()
  {
    return this.alphabetical.length() > 0;
  }


  /**
   * Returns the number of alphabetical characters in this password.
   *
   * @return  number of alphabetical characters in this password
   */
  public int getNumberOfAlphabetical()
  {
    return this.alphabetical.length();
  }


  /**
   * Returns the alphabetical characters in this password.
   *
   * @return  alphabetical characters in this password
   */
  public char[] getAlphabetical()
  {
    char[] array = null;
    if (this.alphabetical != null && this.alphabetical.length() > 0) {
      array = this.alphabetical.toString().toCharArray();
    }
    return array;
  }


  /**
   * Returns whether or not this password contains non-alphabetical characters.
   *
   * @return  whether or not the password contains non-alphabetical characters
   */
  public boolean containsNonAlphabetical()
  {
    return this.nonAlphabetical.length() > 0;
  }


  /**
   * Returns the number of non-alphabetical characters in this password.
   *
   * @return  number of non-alphabetical characters in this password
   */
  public int getNumberOfNonAlphabetical()
  {
    return this.nonAlphabetical.length();
  }


  /**
   * Returns the non-alphabetical characters in this password.
   *
   * @return  non-alphabetical characters in this password
   */
  public char[] getNonAlphabetical()
  {
    char[] array = null;
    if (this.nonAlphabetical != null && this.nonAlphabetical.length() > 0) {
      array = this.nonAlphabetical.toString().toCharArray();
    }
    return array;
  }


  /**
   * Returns whether or not this password contains alphanumeric characters.
   *
   * @return  whether or not the password contains alphanumeric characters
   */
  public boolean containsAlphanumeric()
  {
    return this.alphanumeric.length() > 0;
  }


  /**
   * Returns the number of alphanumeric characters in this password.
   *
   * @return  number of alphanumeric characters in this password
   */
  public int getNumberOfAlphanumeric()
  {
    return this.alphanumeric.length();
  }


  /**
   * Returns the alphanumeric characters in this password.
   *
   * @return  alphanumeric characters in this password
   */
  public char[] getAlphanumeric()
  {
    char[] array = null;
    if (this.alphanumeric != null && this.alphanumeric.length() > 0) {
      array = this.alphanumeric.toString().toCharArray();
    }
    return array;
  }


  /**
   * Returns whether or not this password contains non-alphanumeric characters.
   *
   * @return  whether or not the password contains non-alphanumeric characters
   */
  public boolean containsNonAlphanumeric()
  {
    return this.nonAlphanumeric.length() > 0;
  }


  /**
   * Returns the number of non-alphanumeric characters in this password.
   *
   * @return  number of non-alphanumeric characters in this password
   */
  public int getNumberOfNonAlphanumeric()
  {
    return this.nonAlphanumeric.length();
  }


  /**
   * Returns the non-alphanumeric characters in this password.
   *
   * @return  non-alphanumeric characters in this password
   */
  public char[] getNonAlphanumeric()
  {
    char[] array = null;
    if (this.nonAlphanumeric != null && this.nonAlphanumeric.length() > 0) {
      array = this.nonAlphanumeric.toString().toCharArray();
    }
    return array;
  }


  /**
   * Returns whether or not this password contains uppercase characters.
   *
   * @return  whether or not the password contains uppercase characters
   */
  public boolean containsUppercase()
  {
    return this.uppercase.length() > 0;
  }


  /**
   * Returns the number of uppercase characters in this password.
   *
   * @return  number of uppercase characters in this password
   */
  public int getNumberOfUppercase()
  {
    return this.uppercase.length();
  }


  /**
   * Returns the uppercase characters in this password.
   *
   * @return  uppercase characters in this password
   */
  public char[] getUppercase()
  {
    char[] array = null;
    if (this.uppercase != null && this.uppercase.length() > 0) {
      array = this.uppercase.toString().toCharArray();
    }
    return array;
  }


  /**
   * Returns whether or not this password contains lowercase characters.
   *
   * @return  whether or not the password contains uppercase characters
   */
  public boolean containsLowercase()
  {
    return this.lowercase.length() > 0;
  }


  /**
   * Returns the number of lowercase characters in this password.
   *
   * @return  number of lowercase characters in this password
   */
  public int getNumberOfLowercase()
  {
    return this.lowercase.length();
  }


  /**
   * Returns the lowercase characters in this password.
   *
   * @return  lowercase characters in this password
   */
  public char[] getLowercase()
  {
    char[] array = null;
    if (this.lowercase != null && this.lowercase.length() > 0) {
      array = this.lowercase.toString().toCharArray();
    }
    return array;
  }


  /**
   * Returns whether or not this Password contains whitespace characters.
   *
   * @return  whether or not the password contains whitespace characters
   */
  public boolean containsWhitespace()
  {
    return this.whitespace.length() > 0;
  }


  /**
   * Returns the number of whitespace characters in this password.
   *
   * @return  number of whitespace characters in this password
   */
  public int getNumberOfWhitespace()
  {
    return this.whitespace.length();
  }


  /**
   * Returns the whitespace characters in this password.
   *
   * @return  whitespace characters in this password
   */
  public char[] getWhitespace()
  {
    char[] array = null;
    if (this.whitespace != null && this.whitespace.length() > 0) {
      array = this.whitespace.toString().toCharArray();
    }
    return array;
  }


  /**
   * Returns a string representation of this object.
   *
   * @return  string representation
   */
  @Override
  public String toString()
  {
    return this.password;
  }
}
