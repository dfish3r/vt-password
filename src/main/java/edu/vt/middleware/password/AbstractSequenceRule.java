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
 * Provide common implementation for keyboard sequence rules.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */
public abstract class AbstractSequenceRule implements Rule
{

  /** Error code for sequence validation failures. */
  public static final String ERROR_CODE = "ILLEGAL_SEQUENCE";

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
   * Returns a string representation of this object.
   *
   * @return  string representation
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
   * @param  sl  sequence length
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
   * Returns the sequence of character pairs for which to search.
   *
   * @param  n  provides support for multiple character
   * sequences that are indexed from 0 to n.
   *
   * @return  character sequence.
   */
  protected abstract char[][] getSequence(int n);


  /**
   * Returns the number of character sequences used in this implementation.
   *
   * @return  number of character sequences.
   */
  protected abstract int getSequenceCount();


  /**
   * Creates an iterator that iterates over a character sequence positioned at
   * the first matching character, if any, in the given password.
   *
   * @param  chars  sequence of upper/lowercase character pairs.
   * @param  first  first character to match in character sequence.
   *
   * @return  forward sequence iterator.
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
   * @param  result  rule result holding failure details.
   * @param  match  illegal string matched in the password that caused failure.
   */
  private void recordFailure(final RuleResult result, final String match)
  {
    result.setValid(false);
    result.getDetails().add(
      new RuleResultDetail(ERROR_CODE, new Object[]{match}));
  }


  /**
   * Contains convenience methods for iterating over a sequence of
   * upper/lowercase pairs of characters and stores matched characters.
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
     * Creates a new sequence.
     *
     * @param  characters  sequence of characters
     * @param  startIndex  in the characters array
     * @param  count  length of this sequence
     * @param  wrap  whether this sequence wraps
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
     * @return  true if characters remain, false otherwise.
     */
    public boolean forward()
    {
      return ++position < ubound;
    }


    /**
     * Advances the iterator one unit in the backward direction.
     *
     * @return  true if characters remain, false otherwise.
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
     * Returns the lowercase character at the current iterator position.
     *
     * @return  lowercase character at current position.
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
     * Returns the uppercase character at the current iterator position.
     *
     * @return  uppercase character at current position.
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
     * @param  c  match character.
     */
    public void addMatchCharacter(final char c)
    {
      matches.append(c);
    }


    /**
     * Returns the number of matched characters.
     *
     * @return  matched character count.
     */
    public int matchCount()
    {
      return matches.length();
    }


    /**
     * Returns the string of matched characters.
     *
     * @return  match string.
     */
    public String matchString()
    {
      return matches.toString();
    }
  }
}
