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
 * <code>DictionaryRuleResult</code> provides common implementation for password
 * dictionary rule result implementations.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */
public class DictionaryRuleResult extends RuleResult
{

  /** word which caused this rule to fail. */
  protected String matchingWord;


  /** Default constructor. */
  public DictionaryRuleResult() {}


  /**
   * Creates a new <code>DictionaryRuleResult</code> with the supplied validity.
   *
   * @param  b  result validity
   */
  public DictionaryRuleResult(final boolean b)
  {
    this.setValid(b);
  }


  /**
   * This returns the dictionary word which caused the password to fail. If no
   * failure occurred this value will be null.
   *
   * @return  <code>String</code> - illegal word the password contains
   */
  public String getMatchingWord()
  {
    return this.matchingWord;
  }


  /**
   * This sets the dictionary word which caused the password to fail.
   *
   * @param  s  illegal word the password contains
   */
  public void setMatchingWord(final String s)
  {
    this.matchingWord = s;
  }
}
