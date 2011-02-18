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

import java.util.Arrays;

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
public class DuplicateSequenceRule extends AbstractSequenceRule
{

  /** sequence to generate sequences from, ASCII values from 32 to 127. */
  private static final char[] ASCII_CHARS = new char[] {
    (char) 32, (char) 33, (char) 34, (char) 35, (char) 36, (char) 37, (char) 38,
    (char) 39, (char) 40, (char) 41, (char) 42, (char) 43, (char) 44, (char) 45,
    (char) 46, (char) 47, (char) 48, (char) 49, (char) 50, (char) 51, (char) 52,
    (char) 53, (char) 54, (char) 55, (char) 56, (char) 57, (char) 58, (char) 59,
    (char) 60, (char) 61, (char) 62, (char) 63, (char) 64, (char) 65, (char) 66,
    (char) 67, (char) 68, (char) 69, (char) 70, (char) 71, (char) 72, (char) 73,
    (char) 74, (char) 75, (char) 76, (char) 77, (char) 78, (char) 79, (char) 80,
    (char) 81, (char) 82, (char) 83, (char) 84, (char) 85, (char) 86, (char) 87,
    (char) 88, (char) 89, (char) 90, (char) 91, (char) 92, (char) 93, (char) 94,
    (char) 95, (char) 96, (char) 97, (char) 98, (char) 99, (char) 100,
    (char) 101, (char) 102, (char) 103, (char) 104, (char) 105, (char) 106,
    (char) 107, (char) 108, (char) 109, (char) 110, (char) 111, (char) 112,
    (char) 113, (char) 114, (char) 115, (char) 116, (char) 117, (char) 118,
    (char) 119, (char) 120, (char) 121, (char) 122, (char) 123, (char) 124,
    (char) 125, (char) 126, (char) 127,
  };


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
    if (sl < 2) {
      throw new IllegalArgumentException("sequence length must be >= 2");
    }

    this.sequences = initializeDuplicateSequence(sl, ASCII_CHARS);
  }


  /** {@inheritDoc} */
  public RuleResult validate(final PasswordData passwordData)
  {
    return super.validate(
      passwordData.getPassword().getText(), this.sequences, null);
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
        "%s@%h::sequences=%s",
        this.getClass().getName(),
        this.hashCode(),
        this.sequences != null ? Arrays.asList(this.sequences) : null);
  }
}
