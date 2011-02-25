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
 * <code>AlphabeticalSequenceRule</code> contains methods for determining if a
 * password contains an alphabetical keyboard sequence. Both uppercase and
 * lowercase sequences are checked. The default sequence length is 5 characters.
 *
 * <ul>
 *   <li>Sequences are of the form: 'stuvw' or 'KLMNO'</li>
 *   <li>If wrap=true: 'yzabc' will match</li>
 * </ul>
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */
public class AlphabeticalSequenceRule extends AbstractSequenceRule
{

  /** Letters of the alphabet. */
  private static final char[][] LETTERS = new char[][] {
    new char[] {'a', 'A'},
    new char[] {'b', 'B'},
    new char[] {'c', 'C'},
    new char[] {'d', 'D'},
    new char[] {'e', 'E'},
    new char[] {'f', 'F'},
    new char[] {'g', 'G'},
    new char[] {'h', 'H'},
    new char[] {'i', 'I'},
    new char[] {'j', 'J'},
    new char[] {'k', 'K'},
    new char[] {'l', 'L'},
    new char[] {'m', 'M'},
    new char[] {'n', 'N'},
    new char[] {'o', 'O'},
    new char[] {'p', 'P'},
    new char[] {'q', 'Q'},
    new char[] {'r', 'R'},
    new char[] {'s', 'S'},
    new char[] {'t', 'T'},
    new char[] {'u', 'U'},
    new char[] {'v', 'V'},
    new char[] {'w', 'W'},
    new char[] {'x', 'X'},
    new char[] {'y', 'Y'},
    new char[] {'z', 'Z'},
  };

  /** Array of all the characters in this sequence rule. */
  private static final char[][][] ALL_CHARS = new char[][][] {
    LETTERS,
  };


  /**
   * This creates a new <code>AlphabeticalSequenceRule</code> with the default
   * sequence length.
   */
  public AlphabeticalSequenceRule()
  {
    this(DEFAULT_SEQUENCE_LENGTH, false);
  }


  /**
   * This will create a new <code>AlphabeticalSequenceRule</code> with the
   * supplied sequence length and wrap settings.
   *
   * @param  sl  <code>int</code> sequence length
   * @param  wrap  <code>boolean</code> whether to wrap search sequences
   */
  public AlphabeticalSequenceRule(final int sl, final boolean wrap)
  {
    setSequenceLength(sl);
    this.wrapSequence = wrap;
  }


  /** {@inheritDoc} */
  protected char[][] getSequence(final int n)
  {
    return ALL_CHARS[n];
  }


  /** {@inheritDoc} */
  protected int getSequenceCount()
  {
    return ALL_CHARS.length;
  }
}
