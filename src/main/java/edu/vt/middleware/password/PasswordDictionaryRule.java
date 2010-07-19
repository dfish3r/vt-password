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
 * <code>PasswordDictionaryRule</code> determines if a
 * password matches a dictionary word, or if it contains a dictionary
 * word of a given minimum length or greater.  The checker will optionally
 * also check for reversed words.  If the dictionary was built with 'ignoreCase'
 * set, dictionary lookups will be case insensitive.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */

public class PasswordDictionaryRule extends AbstractPasswordRule
{

  /** default word length. */
  public static final int DEFAULT_WORD_LENGTH = 4;

  /** dictionary of words. */
  private Dictionary dictionary;

  /** the minimum substring size to consider as a possible word within the
      password. */
  private int length = DEFAULT_WORD_LENGTH;

  /** whether to search for dictionary words backwards. */
  private boolean backwards;

  /** word which caused this rule to fail. */
  private String match;


  /**
   * This creates a new a new <code>PasswordDictionaryRule</code> without
   * supplying a dictionary.  The dictionary should be set using the
   * {@link #setDictionary(Dictionary)} method.
   */
  public PasswordDictionaryRule() {}


  /**
   * This creates a new <code>PasswordDictionaryRule</code> with the supplied
   * dictionary. The dictionary should be ready to use when passed to this
   * constructor. {@link edu.vt.middleware.dictionary.Dictionary#build()} should
   * be called prior to passing the object to this constructor.
   *
   * @param  dict  <code>Dictionary</code> to use for searching
   */
  public PasswordDictionaryRule(final Dictionary dict)
  {
    this.dictionary = dict;
  }


  /**
   * This will create a new <code>PasswordDictionaryRule</code> with the
   * supplied dictionary and number of characters. The dictionary should be
   * ready to use when passed to this constructor. {@link
   * edu.vt.middleware.dictionary.Dictionary#build()} should be called prior to
   * passing the object to this constructor. See {@link
   * #setNumberOfCharacters(int)}.
   *
   * @param  dict  <code>Dictionary</code> to use for searching
   * @param  n  <code>int</code> number of characters to check in each
   * dictionary word
   */
  public PasswordDictionaryRule(final Dictionary dict, final int n)
  {
    this.setDictionary(dict);
    this.setNumberOfCharacters(n);
  }


  /**
   * This sets the minimum number of characters that constitute a word in a
   * password. So if n = 5 and the password contains 'test', then the password
   * is valid. However if n = 4 then 'test' will be found in the dictionary. The
   * default value is 4.
   *
   * @param  n  <code>int</code> minimum number of characters to check in each
   * dictionary word
   */
  public void setNumberOfCharacters(final int n)
  {
    if (n >= 1) {
      this.length = n;
    } else {
      throw new IllegalArgumentException("numberOfCharacters must be >= 1");
    }
  }


  /**
   * This will return the number of characters that constitute a word in a
   * password.
   *
   * @return <code>int</code>
   */
  public int getNumberOfCharacters()
  {
    return this.length;
  }


  /**
   * This will set the <code>Dictionary</code> used to search for passwords.
   * {@link edu.vt.middleware.dictionary.Dictionary#build()} should be called
   * on the <code>Dictionary</code> prior to calling this method.
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
   */
  public void matchBackwards()
  {
    this.backwards = true;
  }


  /**
   * This method is a bean-compatible version of {#matchBackwards()}
   *
   * @param  b  <code>boolean</code>
   */
  public void setMatchBackwards(final boolean b)
  {
    this.backwards = b;
  }


  /**
   * Return true if the verify method will search the password for dictionary
   * words spelled backwards as well as forwards.
   *
   * @return  <code>boolean</code>
   */
  public boolean isMatchBackwards()
  {
    return this.backwards;
  }


  /**
   * This returns the dictionary word which caused the password to fail. If no
   * failure occurred this value will be null.
   *
   * @return  <code>String</code> - illegal word the password contains
   */
  public String getMatchingWord()
  {
    return this.match;
  }


  /** {@inheritDoc} */
  @Override
  public PasswordRule createCleanCopy()
  {
    final PasswordDictionaryRule res =
      (PasswordDictionaryRule) super.createCleanCopy();
    res.match = null;
    return res;
  }


  /** {@inheritDoc} */
  public boolean verifyPassword(final Password password)
  {
    boolean success = true;
    String text = password.getText();
    success = doWordSearch(text, false);
    if (this.backwards && success) {
      text = new StringBuilder(password.getText()).reverse().toString();
      success = doWordSearch(text, true);
    }
    return success;
  }


  /**
   * Searches the dictionary with the supplied text.
   *
   * @param  text  to search dictionary with
   * @param  b  whether the supplied text is backwards
   * @throws  PasswordDictionaryException if the supplied text contains a
   * dictionary word
   */
  private boolean doWordSearch(final String text, final boolean b)
  {
    boolean success = true;
    for (int i = this.length; (i <= text.length()) && success; i++) {
      for (int j = 0; (j + i <= text.length()) && success; j++) {
        final String s = text.substring(j, j + i);
        if (this.dictionary.search(s)) {
          success = false;
          this.match = s;
          this.setMessage(
            String.format(
              "Password contains the %sdictionary word '%s'",
              b ? "reversed " : "", s));
        }
      }
    }
    return success;
  }
}
