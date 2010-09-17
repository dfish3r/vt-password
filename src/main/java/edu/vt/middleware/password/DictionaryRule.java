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
 * <code>DictionaryRule</code> determines if a password matches a
 * dictionary word. The checker will optionally also check for reversed words.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */

public class DictionaryRule extends AbstractDictionaryRule
  implements Rule<String>
{

  /**
   * This creates a new a new <code>DictionaryRule</code> without
   * supplying a dictionary. The dictionary should be set using the {@link
   * #setDictionary(Dictionary)} method.
   */
  public DictionaryRule() {}


  /**
   * This creates a new <code>DictionaryRule</code> with the supplied
   * dictionary. The dictionary should be ready to use when passed to this
   * constructor.
   *
   * @param  dict  <code>Dictionary</code> to use for searching
   */
  public DictionaryRule(final Dictionary dict)
  {
    this.dictionary = dict;
  }


  /** {@inheritDoc} */
  protected String doWordSearch(final String text)
  {
    if (this.dictionary.search(text)) {
      return text;
    }
    return null;
  }
}
