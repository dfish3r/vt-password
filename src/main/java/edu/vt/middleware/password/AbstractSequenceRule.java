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

/**
 * <code>AbstractSequenceRule</code> contains functionality common to keyboard
 * sequence rules.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */
public abstract class AbstractSequenceRule implements Rule
{

  /** default length of keyboard sequence, value is {@value}. */
  public static final int DEFAULT_SEQUENCE_LENGTH = 5;

  /** default keyboard sequences. */
  protected String[] sequences;

  /** reverse keyboard sequences. */
  protected String[] reverseSequences;


  /**
   * Iterates over the supplied character arrays and creates an array of
   * sequences for the supplied length. Boundary of the results does not extend
   * past the last element in each array.
   * <p>
   * For arrays {a,b,c,d},{w,x,y,z} with length 2, produces:
   * <ul>
   *   <li>{a,b}</li>
   *   <li>{b,c}</li>
   *   <li>{c,d}</li>
   *   <li>{w,x}</li>
   *   <li>{x,y}</li>
   *   <li>{y,z}</li>
   * </ul>
   * </p>
   *
   * @param  length  <code>int</code> of each sequence to create
   * @param  characters  <code>char[]</code> to create sequence array from
   * @return  <code>String[]</code> of sequences
   */
  protected static String[] initializeSequences(
    final int length, final char[]... characters)
  {
    int seqLength = 0;
    for (char[] ch : characters) {
      seqLength += ch.length - length + 1;
    }
    final String[] sequences = new String[seqLength];
    int index = 0;
    for (char[] ch : characters) {
      final String[] seqs = createSequenceArray(
        ch, ch.length - length + 1, length);
      System.arraycopy(seqs, 0, sequences, index, seqs.length);
      index += seqs.length;
    }
    return sequences;
  }


  /**
   * Iterates over the supplied character arrays and creates an array of
   * sequences for the supplied length. Boundary of the results wraps around to
   * produce a circular sequence
   * <p>
   * For arrays {a,b,c,d},{w,x,y,z} with length 2, produces:
   * <ul>
   *   <li>{a,b}</li>
   *   <li>{b,c}</li>
   *   <li>{c,d}</li>
   *   <li>{d,a}</li>
   *   <li>{w,x}</li>
   *   <li>{x,y}</li>
   *   <li>{y,z}</li>
   *   <li>{z,w}</li>
   * </ul>
   * </p>
   *
   * @param  length  <code>int</code> of each sequence to create
   * @param  characters  <code>char[]</code> to create sequence array from
   * @return  <code>String[]</code> of sequences
   */
  protected static String[] initializeCircularSequences(
    final int length, final char[]... characters)
  {
    int seqLength = 0;
    for (char[] ch : characters) {
      seqLength += ch.length;
    }
    final String[] sequences = new String[seqLength];
    int index = 0;
    for (char[] ch : characters) {
      final String[] seqs = createSequenceArray(ch, ch.length, length);
      System.arraycopy(seqs, 0, sequences, index, seqs.length);
      index += seqs.length;
    }
    return sequences;
  }


  /**
   * Iterates over the supplied character array and returns a string array
   * containing sequences from the character array of the supplied length and
   * extending to the supplied sequence length.
   *
   * @param  src  <code>char[]</code> to create sequence strings from
   * @param  srcLength  <code>int</code> to read from the src array
   * @param  seqLength  <code>int</code> of sequence strings to create
   * @return  <code>String[]</code> of sequence strings
   */
  private static String[] createSequenceArray(
    final char[] src, final int srcLength, final int seqLength)
  {
    final String[] seqs = new String[srcLength];
    for (int i = 0; i < srcLength; i++) {
      final char[] c = new char[seqLength];
      for (int j = 0; j < seqLength; j++) {
        final int x = i + j < src.length ? i + j : i + j - src.length;
        c[j] = src[x];
      }
      seqs[i] = String.valueOf(c);
    }
    return seqs;
  }


  /**
   * Iterates over the supplied character array and returns a string array where
   * each element is one of the characters repeated length number of times.
   *
   * @param  length  <code>int</code> of sequence to create
   * @param  characters  <code>char[]</code> to create sequence array from
   * @return  <code>String[]</code> of sequences
   */
  protected static String[] initializeDuplicateSequence(
    final int length, final char[] characters)
  {
    final String[] seqs = new String[characters.length];
    for (int i = 0; i < characters.length; i++) {
      final char[] c = new char[length];
      for (int j = 0; j < length; j++) {
        c[j] = characters[i];
      }
      seqs[i] = String.valueOf(c);
    }
    return seqs;
  }


  /**
   * Returns an array with each element reversed from the supplied array.
   *
   * @param  sequences  <code>String[]</code> to reverse
   * @return  <code>String[]</code> reversed sequences
   */
  protected static String[] initializeReverseSequences(final String[] sequences)
  {
    final String[] revSeqs = new String[sequences.length];
    for (int i = 0; i < revSeqs.length; i++) {
      revSeqs[i] = new StringBuilder(sequences[i]).reverse().toString();
    }
    return revSeqs;
  }


  /**
   * Validates the supplied text using the supplied sequences. If reverse
   * sequences is null, it is ignored.
   *
   * @param  text  <code>String</code> to validate
   * @param  seqs  <code>String[]</code>  to check text for
   * @param  revSeqs  <code>String[]</code> to check text for
   * @return  <code>RuleResult</code> for this rule
   */
  protected RuleResult validate(
    final String text, final String[] seqs, final String[] revSeqs)
  {
    final RuleResult result = new RuleResult(true);
    for (int i = 0; i < seqs.length; i++) {
      if (text.indexOf(seqs[i]) != -1) {
        result.setValid(false);
        result.getDetails().add(
          new RuleResultDetail(
            String.format(
              "Password contains the keyboard sequence '%s'",
              seqs[i])));
      }
    }
    if (revSeqs != null) {
      for (int j = 0; j < revSeqs.length; j++) {
        if (text.indexOf(revSeqs[j]) != -1) {
          result.setValid(false);
          result.getDetails().add(
            new RuleResultDetail(
              String.format(
                "Password contains the keyboard sequence '%s'",
                revSeqs[j])));
        }
      }
    }
    return result;
  }
}
