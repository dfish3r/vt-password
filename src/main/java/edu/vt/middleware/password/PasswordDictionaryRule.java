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
 * <code>PasswordDictionaryRule</code> contains methods for determining if a
 * password contains a dictionary word of a specified length, forwards or
 * backwards.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */

public class PasswordDictionaryRule extends AbstractPasswordRule
{

  /** dictionary of words. */
  private Dictionary dictionary;

  /** length of dictionary words to search for. */
  private int length = 1;

  /** whether to search for dictionary words backwards. */
  private boolean backwards;

  /** word which caused this rule to fail. */
  private String match;


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
    this.dictionary = dict;
    this.setNumberOfCharacters(n);
  }


  /**
   * This will set the number of characters that constitute a word in a
   * password. So if n = 5 and the password contains 'test', then the password
   * is valid. However if n = 4 then 'test' will be found in the dictionary. The
   * default number is 1.
   *
   * @param  n  <code>int</code> number of characters to check in each
   * dictionary word
   */
  public void setNumberOfCharacters(final int n)
  {
    if (n >= 1) {
      this.length = n;
    }
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
   * This returns the dictionary word which caused the password to fail. If no
   * failure occured this value will be null.
   *
   * @return  <code>String</code> - illegal word the password contains
   */
  public String getMatchingWord()
  {
    return this.match;
  }


  /** {@inheritDoc} */
  public boolean verifyPassword(final Password password)
  {
    boolean success = true;
    if (password != null) {
      String text = password.getText();
      int i = this.length;
      while (i <= text.length() && success) {
        for (int j = 0; (j + i <= text.length()) && success; j++) {
          final String s = text.substring(j, j + i);
          if (this.dictionary.search(s)) {
            success = false;
            this.match = s;
            this.setMessage(
              String.format("Password contains the dictionary word '%s'", s));
          }
        }
        i++;
      }
      if (this.backwards && success) {
        text = new StringBuilder(password.getText()).reverse().toString();
        i = this.length;
        while (i <= text.length() && success) {
          for (int j = 0; (j + i <= text.length()) && success; j++) {
            final String s = text.substring(j, j + i);
            if (this.dictionary.search(s)) {
              success = false;
              this.match = s;
              this.setMessage(
                String.format(
                  "Password contains the backwards dictionary word '%s'",
                  s));
            }
          }
          i++;
        }
      }
    } else {
      success = false;
      this.setMessage("Password cannot be null");
    }
    return success;
  }
}
