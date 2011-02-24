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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <code>DuplicateSequenceRule</code> contains methods for determining if a
 * password contains a duplicate ASCII keyboard sequence. ASCII values from 32
 * to 127 are checked. The default sequence length is 5 characters.
 * <p>
 * <ul>
 *   <li>Sequences are of the form: 'bbbbb' or '#####'</li>
 * </ul>
 * </p>
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */
public class DuplicateSequenceRule implements Rule
{
  /** Default length of sequence, value is {@value}. */
  public static final int DEFAULT_SEQUENCE_LENGTH = 5;

  /** Minimum length of sequence, value is {@value}. */
  public static final int MINIMUM_SEQUENCE_LENGTH = 3;

  /** Repeat pattern. */
  private final Pattern repeatPattern;


  /**
   * This creates a new <code>DuplicateSequenceRule</code> with the default
   * sequence length.
   */
  public DuplicateSequenceRule()
  {
    this(DEFAULT_SEQUENCE_LENGTH);
  }


  /**
   * This creates a new <code>DuplicateSequenceRule</code> with the supplied
   * sequence length setting.
   *
   * @param  sl  <code>int</code> sequence length
   */
  public DuplicateSequenceRule(final int sl)
  {
    if (sl < MINIMUM_SEQUENCE_LENGTH) {
      throw new IllegalArgumentException(
        String.format(
          "sequence length must be >= %s", MINIMUM_SEQUENCE_LENGTH));
    }
    repeatPattern = Pattern.compile(
        String.format("([^\\x00-\\x1F])\\1{%d}", sl - 1));
  }


  /** {@inheritDoc} */
  public RuleResult validate(final PasswordData passwordData)
  {
    final RuleResult result = new RuleResult(true);
    final Matcher m = repeatPattern.matcher(
        passwordData.getPassword().getText());
    if (m.find()) {
      result.setValid(false);
      result.getDetails().add(
          new RuleResultDetail(
            String.format(
              "Password contains the repeated sequence '%s'",
              m.group())));
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
        "%s@%h",
        this.getClass().getName(),
        this.hashCode());
  }
}
