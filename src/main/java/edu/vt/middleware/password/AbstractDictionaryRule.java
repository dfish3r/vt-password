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

import edu.vt.middleware.dictionary.Dictionary;

/**
 * <code>AbstractPasswordDictionaryRule</code> provides common implementation
 * for password dictionary rules.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */

public abstract class AbstractDictionaryRule
{

  /** dictionary of words. */
  protected Dictionary dictionary;

  /** whether to search for dictionary words backwards. */
  protected boolean matchBackwards;


  /**
   * This will set the <code>Dictionary</code> used to search for passwords.
   *
   * @param  dict  <code>Dictionary</code> to use for searching
   */
  public void setDictionary(final Dictionary dict)
  {
    this.dictionary = dict;
  }


  /**
   * This will return the <code>Dictionary</code> used to search for passwords.
   *
   * @return  <code>Dictionary</code>
   */
  public Dictionary getDictionary()
  {
    return this.dictionary;
  }


  /**
   * This causes the verify method to search the password for dictionary words
   * spelled backwards as well as forwards.
   *
   * @param  b  <code>boolean</code>
   */
  public void setMatchBackwards(final boolean b)
  {
    this.matchBackwards = b;
  }


  /**
   * Return true if the verify method will search the password for dictionary
   * words spelled backwards as well as forwards.
   *
   * @return  <code>boolean</code>
   */
  public boolean isMatchBackwards()
  {
    return this.matchBackwards;
  }


  /**
   * See {@link Rule#verifyPassword(Password)}.
   *
   * @param  password  <code>Password</code> to verify (not null).
   *
   * @return  <code>DictionaryRuleResult</code> - details on password
   * verification
   *
   * @throws  NullPointerException  if the password is null.
   */
  public DictionaryRuleResult<String> verifyPassword(
    final Password password)
  {
    final DictionaryRuleResult<String> result =
      new DictionaryRuleResult<String>();
    result.setValid(true);
    String text = password.getText();
    String matchingWord = doWordSearch(text);
    if (matchingWord != null) {
      result.setValid(false);
      result.setDetails(
          String.format(
            "Password contains the dictionary word '%s'",
            matchingWord));
      result.setMatchingWord(matchingWord);
    }
    if (this.matchBackwards && result.isValid()) {
      text = new StringBuilder(password.getText()).reverse().toString();
      matchingWord = doWordSearch(text);
      if (matchingWord != null) {
        result.setValid(false);
        result.setDetails(
            String.format(
              "Password contains the reversed dictionary word '%s'",
              matchingWord));
        result.setMatchingWord(matchingWord);
      }
    }
    return result;
  }


  /**
   * Searches the dictionary with the supplied text.
   *
   * @param  text  to search dictionary with
   * @return  <code>String</code>  matching word
   */
  protected abstract String doWordSearch(final String text);


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
      "%s@%d::dictionary=%s,matchBackwards=%s",
      this.getClass().getName(),
      this.hashCode(),
      this.dictionary,
      this.matchBackwards);
  }
}
