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
 * <code>Password</code> contains functions for determining what type and what
 * quantity of characters a password contains.
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
   * This will create a new <code>Password</code> with the supplied password
   * text.
   *
   * @param  text  <code>String</code> password
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
   * This returns the text of this <code>Password</code>.
   *
   * @return  <code>String</code> - password
   */
  public String getText()
  {
    return this.password;
  }


  /**
   * This returns the length of this <code>Password</code>.
   *
   * @return  <code>int</code> - password length
   */
  public int length()
  {
    return this.password.length();
  }


  /**
   * This returns whether or not this <code>Password</code> contains digits.
   *
   * @return  <code>boolean</code> - whether or not the password contains digits
   */
  public boolean containsDigits()
  {
    return this.digits.length() > 0;
  }


  /**
   * This returns the number of digits in this <code>Password</code>.
   *
   * @return  <code>int</code> - number of digits in the password
   */
  public int numberOfDigits()
  {
    return this.digits.length();
  }


  /**
   * This returns the digits in this <code>Password</code>.
   *
   * @return  <code>char[]</code> - digits in this password
   */
  public char[] digits()
  {
    char[] array = null;
    if (this.digits != null && this.digits.length() > 0) {
      array = this.digits.toString().toCharArray();
    }
    return array;
  }


  /**
   * This returns whether or not this <code>Password</code> contains non-digits.
   *
   * @return  <code>boolean</code> - whether or not the password contains
   * non-digits
   */
  public boolean containsNonDigits()
  {
    return this.nonDigits.length() > 0;
  }


  /**
   * This returns the number of non-digits in this <code>Password</code>.
   *
   * @return  <code>int</code> - number of non-digits in this password
   */
  public int numberOfNonDigits()
  {
    return this.nonDigits.length();
  }


  /**
   * This returns the non-digits in this <code>Password</code>.
   *
   * @return  <code>char[]</code> - non-digits in this password
   */
  public char[] nonDigits()
  {
    char[] array = null;
    if (this.nonDigits != null && this.nonDigits.length() > 0) {
      array = this.nonDigits.toString().toCharArray();
    }
    return array;
  }


  /**
   * This returns whether or not this <code>Password</code> contains
   * alphabetical characters.
   *
   * @return  <code>boolean</code> - whether or not the password contains
   * alphabetical characters
   */
  public boolean containsAlphabetical()
  {
    return this.alphabetical.length() > 0;
  }


  /**
   * This returns the number of alphabetical characters in this <code>
   * Password</code>.
   *
   * @return  <code>int</code> - number of alphabetical characters in this
   * password
   */
  public int numberOfAlphabetical()
  {
    return this.alphabetical.length();
  }


  /**
   * This returns the alphabetical characters in this <code>Password</code>.
   *
   * @return  <code>char[]</code> - alphabetical characters in this password
   */
  public char[] alphabetical()
  {
    char[] array = null;
    if (this.alphabetical != null && this.alphabetical.length() > 0) {
      array = this.alphabetical.toString().toCharArray();
    }
    return array;
  }


  /**
   * This returns whether or not this <code>Password</code> contains
   * non-alphabetical characters.
   *
   * @return  <code>boolean</code> - whether or not the password contains
   * non-alphabetical characters
   */
  public boolean containsNonAlphabetical()
  {
    return this.nonAlphabetical.length() > 0;
  }


  /**
   * This returns the number of non-alphabetical characters in this <code>
   * Password</code>.
   *
   * @return  <code>int</code> - number of non-alphabetical characters in this
   * password
   */
  public int numberOfNonAlphabetical()
  {
    return this.nonAlphabetical.length();
  }


  /**
   * This returns the non-alphabetical characters in this <code>Password</code>.
   *
   * @return  <code>char[]</code> - non-alphabetical characters in this password
   */
  public char[] nonAlphabetical()
  {
    char[] array = null;
    if (this.nonAlphabetical != null && this.nonAlphabetical.length() > 0) {
      array = this.nonAlphabetical.toString().toCharArray();
    }
    return array;
  }


  /**
   * This returns whether or not this <code>Password</code> contains
   * alphanumeric characters.
   *
   * @return  <code>boolean</code> - whether or not the password contains
   * alphanumeric characters
   */
  public boolean containsAlphanumeric()
  {
    return this.alphanumeric.length() > 0;
  }


  /**
   * This returns the number of alphanumeric characters in this <code>
   * Password</code>.
   *
   * @return  <code>int</code> - number of alphanumeric characters in this
   * password
   */
  public int numberOfAlphanumeric()
  {
    return this.alphanumeric.length();
  }


  /**
   * This returns the alphanumeric characters in this <code>Password</code>.
   *
   * @return  <code>char[]</code> - alphanumeric characters in this password
   */
  public char[] alphanumeric()
  {
    char[] array = null;
    if (this.alphanumeric != null && this.alphanumeric.length() > 0) {
      array = this.alphanumeric.toString().toCharArray();
    }
    return array;
  }


  /**
   * This returns whether or not this <code>Password</code> contains
   * non-alphanumeric characters.
   *
   * @return  <code>boolean</code> - whether or not the password contains
   * non-alphanumeric characters
   */
  public boolean containsNonAlphanumeric()
  {
    return this.nonAlphanumeric.length() > 0;
  }


  /**
   * This returns the number of non-alphanumeric characters in this <code>
   * Password</code>.
   *
   * @return  <code>int</code> - number of non-alphanumeric characters in this
   * password
   */
  public int numberOfNonAlphanumeric()
  {
    return this.nonAlphanumeric.length();
  }


  /**
   * This returns the non-alphanumeric characters in this <code>Password</code>.
   *
   * @return  <code>char[]</code> - non-alphanumeric characters in this password
   */
  public char[] nonAlphanumeric()
  {
    char[] array = null;
    if (this.nonAlphanumeric != null && this.nonAlphanumeric.length() > 0) {
      array = this.nonAlphanumeric.toString().toCharArray();
    }
    return array;
  }


  /**
   * This returns whether or not this <code>Password</code> contains uppercase
   * characters.
   *
   * @return  <code>boolean</code> - whether or not the password contains
   * uppercase characters
   */
  public boolean containsUppercase()
  {
    return this.uppercase.length() > 0;
  }


  /**
   * This returns the number of uppercase characters in this <code>
   * Password</code>.
   *
   * @return  <code>int</code> - number of uppercase characters in this password
   */
  public int numberOfUppercase()
  {
    return this.uppercase.length();
  }


  /**
   * This returns the uppercase characters in this <code>Password</code>.
   *
   * @return  <code>char[]</code> - uppercase characters in this password
   */
  public char[] uppercase()
  {
    char[] array = null;
    if (this.uppercase != null && this.uppercase.length() > 0) {
      array = this.uppercase.toString().toCharArray();
    }
    return array;
  }


  /**
   * This returns whether or not this <code>Password</code> contains lowercase
   * characters.
   *
   * @return  <code>boolean</code> - whether or not the password contains
   * uppercase characters
   */
  public boolean containsLowercase()
  {
    return this.lowercase.length() > 0;
  }


  /**
   * This returns the number of lowercase characters in this <code>
   * Password</code>.
   *
   * @return  <code>int</code> - number of lowercase characters in this password
   */
  public int numberOfLowercase()
  {
    return this.lowercase.length();
  }


  /**
   * This returns the lowercase characters in this <code>Password</code>.
   *
   * @return  <code>char[]</code> - lowercase characters in this password
   */
  public char[] lowercase()
  {
    char[] array = null;
    if (this.lowercase != null && this.lowercase.length() > 0) {
      array = this.lowercase.toString().toCharArray();
    }
    return array;
  }


  /**
   * This returns whether or not this <code>Password</code> contains whitespace
   * characters.
   *
   * @return  <code>boolean</code> - whether or not the password contains
   * whitespace characters
   */
  public boolean containsWhitespace()
  {
    return this.whitespace.length() > 0;
  }


  /**
   * This returns the number of whitespace characters in this <code>
   * Password</code>.
   *
   * @return  <code>int</code> - number of whitespace characters in this
   * password
   */
  public int numberOfWhitespace()
  {
    return this.whitespace.length();
  }


  /**
   * This returns the whitespace characters in this <code>Password</code>.
   *
   * @return  <code>char[]</code> - whitespace characters in this password
   */
  public char[] whitespace()
  {
    char[] array = null;
    if (this.whitespace != null && this.whitespace.length() > 0) {
      array = this.whitespace.toString().toCharArray();
    }
    return array;
  }
}
