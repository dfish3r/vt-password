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
 * <code>SequenceRule</code> contains methods for determining if a password
 * contains common keyboard sequences.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */
public class SequenceRule implements Rule
{

  /** default keyboard sequences. */
  private static final String[] SEQUENCES = {
    "aaaaa",
    "bbbbb",
    "ccccc",
    "ddddd",
    "eeeee",
    "fffff",
    "ggggg",
    "hhhhh",
    "iiiii",
    "jjjjj",
    "kkkkk",
    "lllll",
    "mmmmm",
    "nnnnn",
    "ooooo",
    "ppppp",
    "qqqqq",
    "rrrrr",
    "sssss",
    "ttttt",
    "uuuuu",
    "vvvvv",
    "wwwww",
    "xxxxx",
    "yyyyy",
    "zzzzz",
    "11111",
    "22222",
    "33333",
    "44444",
    "55555",
    "66666",
    "77777",
    "88888",
    "99999",
    "00000",
    "qwert",
    "werty",
    "ertyu",
    "rtyui",
    "tyuio",
    "yuiop",
    "uiop[",
    "iop[]",
    "op[]\\",
    "asdfg",
    "sdfgh",
    "dfghj",
    "fghjk",
    "ghjkl",
    "hjkl;",
    "jkl;'",
    "zxcvb",
    "xcvbn",
    "cvbnm",
    "vbnm,",
    "bnm,.",
    "nm,./",
    "`1234",
    "12345",
    "23456",
    "34567",
    "45678",
    "56789",
    "67890",
    "7890-",
    "890-=",
  };

  /** reversed keyboard sequences. */
  private static final String[] REVERSE_SEQUENCES;

  static {
    REVERSE_SEQUENCES = new String[SEQUENCES.length];
    for (int i = 0; i < REVERSE_SEQUENCES.length; i++) {
      REVERSE_SEQUENCES[i] = new StringBuilder(SEQUENCES[i]).reverse()
          .toString();
    }
  }

  /** Whether to ignore case when checking for sequences. */
  private boolean ignoreCase;

  /** whether to search for sequences backwards. */
  private boolean matchBackwards;


  /** This creates a new <code>SequenceRule</code>. */
  public SequenceRule() {}


  /**
   * This will create a new <code>SequenceRule</code> with the supplied
   * matchBackwards and ignoreCase settings.
   *
   * @param  mb  <code>boolean</code> whether to match backwards
   * @param  ic  <code>boolean</code> whether to ignore case
   */
  public SequenceRule(final boolean mb, final boolean ic)
  {
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
    final RuleResult result = new RuleResult(true);
    String text = passwordData.getPassword().getText();
    if (this.ignoreCase) {
      text = text.toLowerCase();
    }
    for (int i = 0; i < SEQUENCES.length; i++) {
      if (text.indexOf(SEQUENCES[i]) != -1) {
        result.setValid(false);
        result.getDetails().add(
          new RuleResultDetail(
            String.format(
              "Password contains the keyboard sequence '%s'",
              SEQUENCES[i])));
      }
    }
    if (this.matchBackwards) {
      for (int j = 0; j < REVERSE_SEQUENCES.length; j++) {
        if (text.indexOf(REVERSE_SEQUENCES[j]) != -1) {
          result.setValid(false);
          result.getDetails().add(
            new RuleResultDetail(
              String.format(
                "Password contains the keyboard sequence '%s'",
                REVERSE_SEQUENCES[j])));
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
        "%s@%h::ignoreCase=%s,matchBackwards=%s",
        this.getClass().getName(),
        this.hashCode(),
        this.ignoreCase,
        this.matchBackwards);
  }
}
