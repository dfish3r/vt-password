/*
  $Id: CharacterRule.java 1636 2010-10-04 15:12:15Z dfisher $

  Copyright (C) 2003-2010 Virginia Tech.
  All rights reserved.

  SEE LICENSE FOR MORE INFORMATION

  Author:  Middleware Services
  Email:   middleware@vt.edu
  Version: $Revision: 1636 $
  Updated: $Date: 2010-10-04 11:12:15 -0400 (Mon, 04 Oct 2010) $
*/
package edu.vt.middleware.password;

/**
 * <code>AlphabeticalCharacterRule</code> contains methods for determining if a
 * password contains the correct number of alphabetical characters.
 *
 * @author  Middleware Services
 * @version  $Revision: 1636 $ $Date: 2010-10-04 11:12:15 -0400 (Mon, 04 Oct 2010) $
 */

public class AlphabeticalCharacterRule extends AbstractCharacterRule
{

  /**
   * Default constructor.
   */
  public AlphabeticalCharacterRule() {}


  /**
   * This will create a new <code>AlphabeticalCharacterRule</code> with the
   * supplied number of alphabetical characters to enforce.
   *
   * @param  num  <code>int</code>
   */
  public AlphabeticalCharacterRule(final int num)
  {
    this.setNumberOfCharacters(num);
  }


  /** {@inheritDoc} */
  public String getValidCharacters()
  {
    return "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
  }


  /** {@inheritDoc} */
  protected int getNumberOfCharacterType(final Password password)
  {
    return password.getNumberOfAlphabetical();
  }


  /** {@inheritDoc} */
  protected String getRuleResultDetailMessage()
  {
    return String.format(
      "Password must contain at least %s alphabetical characters",
      this.numCharacters);
  }
}
