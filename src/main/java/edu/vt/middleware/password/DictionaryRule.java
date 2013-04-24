/*
  $Id$

  Copyright (C) 2003-2013 Virginia Tech.
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
 * Rule for determining if a password matches a dictionary word. This rule will
 * optionally also check for reversed words.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */
public class DictionaryRule extends AbstractDictionaryRule
{

  /**
   * Creates a new dictionary rule without supplying a dictionary. The
   * dictionary should be set using {@link #setDictionary(Dictionary)}.
   */
  public DictionaryRule() {}


  /**
   * Creates a new dictionary rule. The dictionary should be ready to use when
   * passed to this constructor.
   *
   * @param  dict  to use for searching
   */
  public DictionaryRule(final Dictionary dict)
  {
    dictionary = dict;
  }


  /** {@inheritDoc} */
  @Override
  protected String doWordSearch(final String text)
  {
    if (dictionary.search(text)) {
      return text;
    }
    return null;
  }
}
