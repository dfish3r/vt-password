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
 * <code>NumericalSequenceRule</code> contains methods for determining if a
 * password contains a numerical keyboard sequence. The default sequence length
 * is 5 characters.
 *
 * <ul>
 *   <li>Sequences are of the form: '23456'</li>
 *   <li>If wrap=true: '90123' will match</li>
 * </ul>
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */
public class NumericalSequenceRule extends AbstractSequenceRule
{

  /** Digits of a numerical sequence. */
  private static final char[][] DIGITS = new char[][] {
    new char[] {'0', '0'},
    new char[] {'1', '1'},
    new char[] {'2', '2'},
    new char[] {'3', '3'},
    new char[] {'4', '4'},
    new char[] {'5', '5'},
    new char[] {'6', '6'},
    new char[] {'7', '7'},
    new char[] {'8', '8'},
    new char[] {'9', '9'},
  };

  /** Array of all the characters in this sequence rule. */
  private static final char[][][] ALL_CHARS = new char[][][] {
    DIGITS,
  };


  /**
   * This creates a new <code>NumericalSequenceRule</code> with the default
   * sequence length.
   */
  public NumericalSequenceRule()
  {
    this(DEFAULT_SEQUENCE_LENGTH, false);
  }


  /**
   * This will create a new <code>NumericalSequenceRule</code> with the supplied
   * sequence length and wrap settings.
   *
   * @param  length  Length of sequence to search for.
   * @param  wrap  True to wrap sequences when searching for matches, false
   * otherwise. Default is false.
   */
  public NumericalSequenceRule(final int length, final boolean wrap)
  {
    setSequenceLength(length);
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
