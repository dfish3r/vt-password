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
 * <code>AlphabeticalSequenceRule</code> contains methods for determining if a
 * password contains an alphabetical keyboard sequence. Both uppercase and
 * lowercase sequences are checked. The default sequence length is 5 characters.
 * <p>
 * <ul>
 *   <li>Sequences are of the form: 'stuvw' or 'KLMNO'</li>
 *   <li>If ignoreCase=true: 'sTuVw' will match<li>
 *   <li>If matchBackwards=true: 'ONMLK' will match</li>
 *   <li>If circular=true: 'yzabc' will match</li>
 * </ul>
 * </p>
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */
public class AlphabeticalSequenceRule extends AbstractSequenceRule
{

  /** sequence to generate sequences from. */
  private static final char[] LC_ALPHABETICAL_CHARS = new char[] {
    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
    'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
  };

  /** sequence to generate sequences from. */
  private static final char[] UC_ALPHABETICAL_CHARS = new char[] {
    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
    'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
  };

  /** Whether to ignore case when checking for sequences. */
  private boolean ignoreCase;

  /** whether to search for sequences backwards. */
  private boolean matchBackwards;


  /**
   * This creates a new <code>AlphabeticalSequenceRule</code> with the default
   * sequence length.
   */
  public AlphabeticalSequenceRule()
  {
    this(DEFAULT_SEQUENCE_LENGTH, false, false, false);
  }


  /**
   * This will create a new <code>AlphabeticalSequenceRule</code> with the
   * supplied match backwards and ignore case settings.
   *
   * @param  mb  <code>boolean</code> whether to match backwards
   * @param  ic  <code>boolean</code> whether to ignore case
   */
  public AlphabeticalSequenceRule(final boolean mb, final boolean ic)
  {
    this(DEFAULT_SEQUENCE_LENGTH, false, mb, ic);
  }


  /**
   * This creates a new <code>AlphabeticalSequenceRule</code> with the supplied
   * sequence length setting.
   *
   * @param  sl  <code>int</code> sequence length
   */
  public AlphabeticalSequenceRule(final int sl)
  {
    this(sl, false, false, false);
  }


  /**
   * This will create a new <code>AlphabeticalSequenceRule</code> with the
   * supplied sequence length and match backwards settings.
   *
   * @param  sl  <code>int</code> sequence length
   * @param  mb  <code>boolean</code> whether to match backwards
   */
  public AlphabeticalSequenceRule(final int sl, final boolean mb)
  {
    this(sl, false, mb, false);
  }


  /**
   * This will create a new <code>AlphabeticalSequenceRule</code> with the
   * supplied sequence length, match backwards, and ignore case settings.
   *
   * @param  sl  <code>int</code> sequence length
   * @param  mb  <code>boolean</code> whether to match backwards
   * @param  ic  <code>boolean</code> whether to ignore case
   */
  public AlphabeticalSequenceRule(
    final int sl, final boolean mb, final boolean ic)
  {
    this(sl, false, mb, ic);
  }


  /**
   * This will create a new <code>NumericalSequenceRule</code> with the supplied
   * sequence length, circular, match backwards, and ignore case settings.
   *
   * @param  sl  <code>int</code> sequence length
   * @param  c  <code>boolean</code> whether to create circular sequences
   * @param  mb  <code>boolean</code> whether to match backwards
   * @param  ic  <code>boolean</code> whether to ignore case
   */
  public AlphabeticalSequenceRule(
    final int sl, final boolean c, final boolean mb, final boolean ic)
  {
    if (sl < 2) {
      throw new IllegalArgumentException("sequence length must be >= 2");
    }

    if (c) {
      this.sequences = initializeCircularSequences(
        sl, LC_ALPHABETICAL_CHARS, UC_ALPHABETICAL_CHARS);
    } else {
      this.sequences = initializeSequences(
        sl, LC_ALPHABETICAL_CHARS, UC_ALPHABETICAL_CHARS);
    }
    this.reverseSequences = initializeReverseSequences(this.sequences);

    this.setMatchBackwards(mb);
    this.setIgnoreCase(ic);
  }


  /**
   * This causes the verify method to ignore case when searching the for a
   * sequence.
   *
   * @param  b  <code>boolean</code>
   */
  public void setIgnoreCase(final boolean b)
  {
    this.ignoreCase = b;
  }


  /**
   * Get the value of the ignoreCase property.
   *
   * @return  <code>boolean</code>
   */
  public boolean isIgnoreCase()
  {
    return ignoreCase;
  }


  /**
   * This causes the verify method to search the password for sequences spelled
   * backwards as well as forwards.
   *
   * @param  b  <code>boolean</code>
   */
  public void setMatchBackwards(final boolean b)
  {
    this.matchBackwards = b;
  }


  /**
   * Get the value of the matchBackwards property.
   *
   * @return  <code>boolean</code>
   */
  public boolean isMatchBackwards()
  {
    return this.matchBackwards;
  }


  /** {@inheritDoc} */
  public RuleResult validate(final PasswordData passwordData)
  {
    String text = passwordData.getPassword().getText();
    if (this.ignoreCase) {
      text = text.toLowerCase();
    }
    if (this.matchBackwards) {
      return super.validate(text, this.sequences, this.reverseSequences);
    } else {
      return super.validate(text, this.sequences, null);
    }
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
        "%s@%h::ignoreCase=%s,matchBackwards=%s,sequences=%s," +
        "reverseSequences=%s",
        this.getClass().getName(),
        this.hashCode(),
        this.ignoreCase,
        this.matchBackwards,
        this.sequences != null ? Arrays.asList(this.sequences) : null,
        this.reverseSequences != null ?
          Arrays.asList(this.reverseSequences) : null);
  }
}
