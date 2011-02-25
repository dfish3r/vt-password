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
 * <code>AbstractSequenceRule</code> contains functionality common to keyboard
 * sequence rules.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */
public abstract class AbstractSequenceRule implements Rule
{

  /** Default length of keyboard sequence, value is {@value}. */
  public static final int DEFAULT_SEQUENCE_LENGTH = 5;

  /** Minimum length of keyboard sequence, value is {@value}. */
  public static final int MINIMUM_SEQUENCE_LENGTH = 3;

  /** Number of characters in sequence to match. */
  protected int sequenceLength = DEFAULT_SEQUENCE_LENGTH;

  /** Whether or not to wrap a sequence when searching for matches. */
  protected boolean wrapSequence;


  /** {@inheritDoc} */
  public RuleResult validate(final PasswordData passwordData)
  {
    final RuleResult result = new RuleResult(true);
    final String password = passwordData.getPassword().getText();
    final int max = password.length() - sequenceLength + 1;
    Sequence sequence;
    int position;
    char c;
    for (int i = 0; i < getSequenceCount(); i++) {
      for (int j = 0; j < max; j++) {
        sequence = newSequence(getSequence(i), password.charAt(j));
        if (sequence != null) {
          position = j;
          while (sequence.forward()) {
            c = password.charAt(++position);
            if (c == sequence.currentLower() || c == sequence.currentUpper()) {
              sequence.addMatchCharacter(c);
            } else {
              break;
            }
          }
          if (sequence.matchCount() == sequenceLength) {
            recordFailure(result, sequence.matchString());
          }
          sequence.reset();
          position = j;
          while (sequence.backward()) {
            c = password.charAt(++position);
            if (c == sequence.currentLower() || c == sequence.currentUpper()) {
              sequence.addMatchCharacter(c);
            } else {
              break;
            }
          }
          if (sequence.matchCount() == sequenceLength) {
            recordFailure(result, sequence.matchString());
          }
        }
      }
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
        "%s@%h::length=%d,wrap=%s",
        this.getClass().getName(),
        this.hashCode(),
        this.sequenceLength,
        this.wrapSequence);
  }


  /**
   * Sets the sequence length.
   *
   * @param  sl  <code>int</code> sequence length
   */
  protected void setSequenceLength(final int sl)
  {
    if (sl < MINIMUM_SEQUENCE_LENGTH) {
      throw new IllegalArgumentException(
        String.format(
          "sequence length must be >= %s",
          MINIMUM_SEQUENCE_LENGTH));
    }
    this.sequenceLength = sl;
  }


  /**
   * Get the sequence of character pairs for which to search.
   *
   * @param  n  <code>int</code> provides support for multiple character
   * sequences that are indexed from 0 to n.
   *
   * @return  <code>char[][]</code> character sequence.
   */
  protected abstract char[][] getSequence(int n);


  /**
   * Get the number of character sequences used in this implementation.
   *
   * @return  <code>int</code> number of character sequences.
   */
  protected abstract int getSequenceCount();


  /**
   * Creates an iterator that iterates over a character sequence positioned at
   * the first matching character, if any, in the given password.
   *
   * @param  chars  <code>char[][]</code> sequence of upper/lowercase character
   * pairs.
   * @param  first  <code>char</code> first character to match in character
   * sequence.
   *
   * @return  <code>Sequence</code> forward sequence iterator.
   */
  private Sequence newSequence(final char[][] chars, final char first)
  {
    for (int i = 0; i < chars.length; i++) {
      if (first == chars[i][0] || first == chars[i][1]) {
        final Sequence s = new Sequence(chars, i, sequenceLength, wrapSequence);
        s.addMatchCharacter(first);
        return s;
      }
    }
    return null;
  }


  /**
   * Records a validation failure.
   *
   * @param  result  <code>RuleResult</code> rule result holding failure
   * details.
   * @param  match  <code>String</code> illegal string matched in the password
   * that caused failure.
   */
  private void recordFailure(final RuleResult result, final String match)
  {
    result.setValid(false);
    result.getDetails().add(
      new RuleResultDetail(
        String.format("Password contains illegal sequence '%s'", match)));
  }


  /**
   * <code>Sequence</code> contains convenience methods for iterating over a
   * sequence of upper/lowercase pairs of characters and stores matched
   * characters.
   *
   * @author  Middleware Services
   * @version  $Revision$
   */
  private class Sequence
  {

    /** Sequence of upper/lower character pairs. */
    private final char[][] chars;

    /** 0-based iterator start position. */
    private int start;

    /** Number of characters to iterate over. */
    private int length;

    /** Index upper bound. */
    private int ubound;

    /** Index lower bound. */
    private int lbound;

    /** Current 0-based iterator position. */
    private int position;

    /** Stores matched characters. */
    private StringBuilder matches;


    /**
     * Creates a new instance with the given parameters.
     *
     * @param  characters  <code>char[][]</code> sequence of characters
     * @param  startIndex  <code>int</code> in the characters array
     * @param  count  <code>int</code> length of this sequence
     * @param  wrap  <code>boolean</code> whether this sequence wraps
     */
    public Sequence(
      final char[][] characters,
      final int startIndex,
      final int count,
      final boolean wrap)
    {
      chars = characters;
      start = startIndex;
      length = count;
      lbound = start - length;
      ubound = start + length;
      if (lbound < 0 && !wrap) {
        lbound = 0;
      }
      if (ubound >= characters.length && !wrap) {
        ubound = characters.length;
      }
      position = start;
      matches = new StringBuilder(length);
    }


    /**
     * Advances the iterator one unit in the forward direction.
     *
     * @return  <code>boolean</code> true if characters remain, false otherwise.
     */
    public boolean forward()
    {
      return ++position < ubound;
    }


    /**
     * Advances the iterator one unit in the backward direction.
     *
     * @return  <code>boolean</code> true if characters remain, false otherwise.
     */
    public boolean backward()
    {
      return --position > lbound;
    }


    /**
     * Resets the sequence to its original position and discards all but the
     * initial match character.
     */
    public void reset()
    {
      position = start;
      matches.delete(1, length);
    }


    /**
     * Get the lowercase character at the current iterator position.
     *
     * @return  <code>char</code> lowercase character at current position.
     */
    public char currentLower()
    {
      final int i;
      if (position < 0) {
        i = chars.length + position;
      } else if (position >= chars.length) {
        i = position - chars.length;
      } else {
        i = position;
      }
      return chars[i][0];
    }


    /**
     * Get the uppercase character at the current iterator position.
     *
     * @return  <code>char</code> uppercase character at current position.
     */
    public char currentUpper()
    {
      final int i;
      if (position < 0) {
        i = chars.length + position;
      } else if (position >= chars.length) {
        i = position - chars.length;
      } else {
        i = position;
      }
      return chars[i][1];
    }


    /**
     * Adds the given character to the set of matched characters.
     *
     * @param  c  <code>char</code> match character.
     */
    public void addMatchCharacter(final char c)
    {
      matches.append(c);
    }


    /**
     * Get the number of matched characters.
     *
     * @return  <code>int</code> matched character count.
     */
    public int matchCount()
    {
      return matches.length();
    }


    /**
     * Get the string of matched characters.
     *
     * @return  <code>String</code> match string.
     */
    public String matchString()
    {
      return matches.toString();
    }
  }
}
