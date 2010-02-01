/*
  $Id$

  Copyright (C) 2003-2008 Virginia Tech.
  All rights reserved.

  SEE LICENSE FOR MORE INFORMATION

  Author:  Sean C. Sullivan
  Author:  Middleware Services
  Email:   sean@seansullivan.com
  Email:   middleware@vt.edu
  Version: $Revision$
  Updated: $Date$
*/
package edu.vt.middleware.password;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * <code>PasswordGenerator</code> creates password that meet password rule
 * criteria.
 *
 * @author  Sean C. Sullivan
 * @author  Middleware Services
 * @version  $Revision$
 */
public final class PasswordGenerator
{
  /** All digits. */
  private StringBuilder digits = new StringBuilder("0123456789");

  /** All lowercase characters. */
  private StringBuilder lowercase =
    new StringBuilder("abcdefghijklmnopqrstuvwxyz");

  /** All uppercase characters. */
  private StringBuilder uppercase =
    new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");

  /** All non-alphanumeric characters.
      does not include backslash, pipe, single quote, or double quote */
  private StringBuilder nonAlphanumeric =
    new StringBuilder("`~!@#$%^&*()-_=+[{]};:<,>./?");

  /** All uppercase and lowercase characters. */
  private StringBuilder alphabetical =
    new StringBuilder(this.lowercase).append(this.uppercase);

  /** All alphabetical and digit characters. */
  private StringBuilder alphanumeric =
    new StringBuilder(this.digits).append(this.alphabetical);

  /** All characters. */
  private StringBuilder all;

  /** Source of random data. */
  private Random random;


  /**
   * Default constructor.
   * Instantiates a <code>SecureRandom</code> for password generation.
   */
  public PasswordGenerator()
  {
    this(new SecureRandom(), null);
  }


  /**
   * Creates a new <code>PasswordGenerator</code> with the supplied random.
   *
   * @param  r  <code>Random</code>
   */
  public PasswordGenerator(final Random r)
  {
    this(r, null);
  }


  /**
   * Creates a new <code>PasswordGenerator</code> with the supplied random and
   * non-alphanumeric characters. nonAlphaChars can be defined if the default
   * value of {@link #nonAlphanumeric} is not acceptable.
   *
   * @param  r  <code>Random</code>
   * @param  nonAlphaChars  <code>String</code>
   */
  public PasswordGenerator(final Random r, final String nonAlphaChars)
  {
    this.random = r;
    if (nonAlphaChars != null) {
      this.nonAlphanumeric = new StringBuilder(nonAlphaChars);
    }
    this.all = new StringBuilder(
      this.alphanumeric).append(this.nonAlphanumeric);
  }


  /**
   * Generates a password of the supplied length which meets the
   * requirements of the supplied password rule.
   * For length to be evaluated it must be greater than the number of characters
   * defined in the character rule.
   *
   * @param  length  <code>int</code>
   * @param  rule  <code>PasswordCharacterRule</code>
   * @return  <code>String</code> - generated password
   */
  public String generatePassword(
    final int length, final PasswordCharacterRule rule)
  {
    if (length <= 0) {
      throw new IllegalArgumentException("length must be greater than 0");
    }

    final List<Character> temp = new ArrayList<Character>(length);

    if (rule != null) {
      for (int i = 0; i < rule.getNumberOfLowercase(); i++) {
        temp.add(
          this.lowercase.charAt(random.nextInt(this.lowercase.length())));
      }

      for (int i = 0; i < rule.getNumberOfUppercase(); i++) {
        temp.add(
          this.uppercase.charAt(random.nextInt(this.uppercase.length())));
      }

      for (int i = 0; i < rule.getNumberOfAlphabetical(); i++) {
        temp.add(
          this.alphabetical.charAt(random.nextInt(this.alphabetical.length())));
      }

      for (int i = 0; i < rule.getNumberOfDigits(); i++) {
        temp.add(this.digits.charAt(random.nextInt(this.digits.length())));
      }

      for (int i = 0; i < rule.getNumberOfNonAlphanumeric(); i++) {
        temp.add(
          this.nonAlphanumeric.charAt(
            random.nextInt(this.nonAlphanumeric.length())));
      }
    }

    while (temp.size() < length) {
      temp.add(this.all.charAt(random.nextInt(this.all.length())));
    }

    Collections.shuffle(temp, random);

    final StringBuilder sb = new StringBuilder();
    for (Character c : temp) {
      sb.append(c.charValue());
    }
    return sb.toString();
  }
}
