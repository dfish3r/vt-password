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
 * <code>NumericalSequenceRule</code> contains methods for determining if a
 * password contains a numerical keyboard sequence. The default sequence length
 * is 5 characters.
 * <p>
 * <ul>
 *   <li>Sequences are of the form: '23456'</li>
 *   <li>If matchBackwards=true: '98765' will match</li>
 *   <li>If circular=true: '90123' will match</li>
 * </ul>
 * </p>
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */
public class NumericalSequenceRule extends AbstractSequenceRule
{

  /** sequence to generate sequences from. */
  private static final char[] NUMBER_CHARS = new char[] {
    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
  };

  /** whether to search for sequences backwards. */
  private boolean matchBackwards;


  /**
   * This creates a new <code>NumericalSequenceRule</code> with the default
   * sequence length.
   */
  public NumericalSequenceRule()
  {
    this(DEFAULT_SEQUENCE_LENGTH, false, false);
  }


  /**
   * This will create a new <code>NumericalSequenceRule</code> with the supplied
   * match backwards settings.
   *
   * @param  mb  <code>boolean</code> whether to match backwards
   */
  public NumericalSequenceRule(final boolean mb)
  {
    this(DEFAULT_SEQUENCE_LENGTH, false, mb);
  }


  /**
   * This creates a new <code>NumericalSequenceRule</code> with the supplied
   * sequence length setting.
   *
   * @param  sl  <code>int</code> sequence length
   */
  public NumericalSequenceRule(final int sl)
  {
    this(sl, false, false);
  }


  /**
   * This will create a new <code>NumericalSequenceRule</code> with the supplied
   * sequence length and match backwards settings.
   *
   * @param  sl  <code>int</code> sequence length
   * @param  mb  <code>boolean</code> whether to match backwards
   */
  public NumericalSequenceRule(final int sl, final boolean mb)
  {
    this(sl, false, mb);
  }


  /**
   * This will create a new <code>NumericalSequenceRule</code> with the supplied
   * sequence length, circular, and match backwards settings.
   *
   * @param  sl  <code>int</code> sequence length
   * @param  c  <code>boolean</code> whether to create circular sequences
   * @param  mb  <code>boolean</code> whether to match backwards
   */
  public NumericalSequenceRule(final int sl, final boolean c, final boolean mb)
  {
    if (sl < 2) {
      throw new IllegalArgumentException("sequence length must be >= 2");
    }
    if (c) {
      this.sequences = initializeCircularSequences(sl, NUMBER_CHARS);
    } else {
      this.sequences = initializeSequences(sl, NUMBER_CHARS);
    }
    this.reverseSequences = initializeReverseSequences(this.sequences);
    this.setMatchBackwards(mb);
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
    if (this.matchBackwards) {
      return super.validate(
        passwordData.getPassword().getText(),
        this.sequences,
        this.reverseSequences);
    } else {
      return super.validate(
        passwordData.getPassword().getText(), this.sequences, null);
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
        "%s@%h::matchBackwards=%s,sequences=%s,reverseSequences=%s",
        this.getClass().getName(),
        this.hashCode(),
        this.matchBackwards,
        this.sequences != null ? Arrays.asList(this.sequences) : null,
        this.reverseSequences != null ?
          Arrays.asList(this.reverseSequences) : null);
  }
}
