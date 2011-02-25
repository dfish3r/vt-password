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
 * <code>QwertySequenceRule</code> contains methods for determining if a
 * password contains a QWERTY keyboard sequence. Both uppercase and lowercase
 * sequences are checked. The default sequence length is 5 characters.
 *
 * <ul>
 *   <li>Sequences are of the form: 'yuiop' or '#$%^&'</li>
 *   <li>If wrap=true: './zxc' will match, note wrapping applies to each row of
 *     the keyboard</li>
 * </ul>
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */
public class QwertySequenceRule extends AbstractSequenceRule
{

  /** First row of querty characters. */
  private static final char[][] ROW1 = new char[][] {
    new char[] {'`', '~'},
    new char[] {'1', '!'},
    new char[] {'2', '@'},
    new char[] {'3', '#'},
    new char[] {'4', '$'},
    new char[] {'5', '%'},
    new char[] {'6', '^'},
    new char[] {'7', '&'},
    new char[] {'8', '*'},
    new char[] {'9', '('},
    new char[] {'0', ')'},
    new char[] {'-', '_'},
    new char[] {'=', '+'},
  };

  /** Second row of querty characters. */
  private static final char[][] ROW2 = new char[][] {
    new char[] {'q', 'Q'},
    new char[] {'w', 'W'},
    new char[] {'e', 'E'},
    new char[] {'r', 'R'},
    new char[] {'t', 'T'},
    new char[] {'y', 'Y'},
    new char[] {'u', 'U'},
    new char[] {'i', 'I'},
    new char[] {'o', 'O'},
    new char[] {'p', 'P'},
    new char[] {'[', '{'},
    new char[] {']', '}'},
    new char[] {'\\', '|'},
  };

  /** Third row of querty characters. */
  private static final char[][] ROW3 = new char[][] {
    new char[] {'a', 'A'},
    new char[] {'s', 'S'},
    new char[] {'d', 'D'},
    new char[] {'f', 'F'},
    new char[] {'g', 'G'},
    new char[] {'h', 'H'},
    new char[] {'j', 'J'},
    new char[] {'k', 'K'},
    new char[] {'l', 'L'},
    new char[] {';', ':'},
    new char[] {'\'', '"'},
  };

  /** Fourth row of querty characters. */
  private static final char[][] ROW4 = new char[][] {
    new char[] {'z', 'Z'},
    new char[] {'x', 'X'},
    new char[] {'c', 'C'},
    new char[] {'v', 'V'},
    new char[] {'b', 'B'},
    new char[] {'n', 'N'},
    new char[] {'m', 'M'},
    new char[] {',', '<'},
    new char[] {'.', '>'},
    new char[] {'/', '?'},
  };

  /** Array of all the characters in this sequence rule. */
  private static final char[][][] ALL_CHARS = new char[][][] {
    ROW1,
    ROW2,
    ROW3,
    ROW4,
  };


  /**
   * This creates a new <code>QwertySequenceRule</code> with the default
   * sequence length.
   */
  public QwertySequenceRule()
  {
    this(DEFAULT_SEQUENCE_LENGTH, false);
  }


  /**
   * This will create a new <code>QwertySequenceRule</code> with the supplied
   * sequence length and wrap settings.
   *
   * @param  sl  <code>int</code> sequence length
   * @param  wrap  <code>boolean</code> whether to wrap sequences
   */
  public QwertySequenceRule(final int sl, final boolean wrap)
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
