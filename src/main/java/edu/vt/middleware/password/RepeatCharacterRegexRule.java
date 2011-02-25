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

import java.util.regex.Matcher;

/**
 * <code>RepeatCharacterRegexRule</code> contains methods for determining if a
 * password contains a duplicate ASCII keyboard sequence. See
 * {@link java.util.regex.Pattern} /p{ASCII}. The default sequence length is 5
 * characters.
 *
 * <ul>
 *   <li>Sequences are of the form: 'bbbbb' or '#####'</li>
 * </ul>
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */
public class RepeatCharacterRegexRule extends RegexRule
{

  /** Default length of sequence, value is {@value}. */
  public static final int DEFAULT_SEQUENCE_LENGTH = 5;

  /** Minimum length of sequence, value is {@value}. */
  public static final int MINIMUM_SEQUENCE_LENGTH = 3;

  /** Regular expression used by this rule, value is {@value}. */
  private static final String REPEAT_CHAR_REGEX = "([^\\x00-\\x1F])\\1{%d}";


  /**
   * This creates a new <code>RepeatCharacterRegexRule</code> with the default
   * sequence length.
   */
  public RepeatCharacterRegexRule()
  {
    this(DEFAULT_SEQUENCE_LENGTH);
  }


  /**
   * This creates a new <code>RepeatCharacterRegexRule</code> with the supplied
   * sequence length setting.
   *
   * @param  sl  <code>int</code> sequence length
   */
  public RepeatCharacterRegexRule(final int sl)
  {
    super(String.format(REPEAT_CHAR_REGEX, sl - 1));
    if (sl < MINIMUM_SEQUENCE_LENGTH) {
      throw new IllegalArgumentException(
        String.format(
          "sequence length must be >= %s",
          MINIMUM_SEQUENCE_LENGTH));
    }
  }


  /** {@inheritDoc} */
  protected String getRuleResultDetail(final Matcher m)
  {
    return
      String.format("Password contains the repeated character '%s'", m.group());
  }
}
