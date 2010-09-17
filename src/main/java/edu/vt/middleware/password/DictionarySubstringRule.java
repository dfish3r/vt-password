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
 * <code>DictionarySubstringRule</code> determines if a password matches a
 * dictionary word, or if it contains a dictionary word of a given minimum
 * length or greater. The checker will optionally also check for reversed words.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */

public class DictionarySubstringRule extends AbstractDictionaryRule
  implements Rule<String>
{

  /** default word length. */
  public static final int DEFAULT_WORD_LENGTH = 4;

  /**
   * the minimum substring size to consider as a possible word within the
   * password.
   */
  private int length = DEFAULT_WORD_LENGTH;


  /**
   * This creates a new a new <code>DictionarySubstringRule</code> without
   * supplying a dictionary. The dictionary should be set using the {@link
   * #setDictionary(Dictionary)} method.
   */
  public DictionarySubstringRule() {}


  /**
   * This creates a new <code>DictionarySubstringRule</code> with the supplied
   * dictionary. The dictionary should be ready to use when passed to this
   * constructor.
   *
   * @param  dict  <code>Dictionary</code> to use for searching
   */
  public DictionarySubstringRule(final Dictionary dict)
  {
    this.dictionary = dict;
  }


  /**
   * This will create a new <code>DictionarySubstringRule</code> with the
   * supplied dictionary and number of characters. The dictionary should be
   * ready to use when passed to this constructor. See {@link
   * #setNumberOfCharacters(int)}.
   *
   * @param  dict  <code>Dictionary</code> to use for searching
   * @param  n  <code>int</code> number of characters to check in each
   * dictionary word
   */
  public DictionarySubstringRule(final Dictionary dict, final int n)
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
   * @return  <code>int</code>
   */
  public int getNumberOfCharacters()
  {
    return this.length;
  }


  /** {@inheritDoc} */
  protected String doWordSearch(final String text)
  {
    for (int i = this.length; i <= text.length(); i++) {
      for (int j = 0; j + i <= text.length(); j++) {
        final String s = text.substring(j, j + i);
        if (this.dictionary.search(s)) {
          return s;
        }
      }
    }
    return null;
  }
}
