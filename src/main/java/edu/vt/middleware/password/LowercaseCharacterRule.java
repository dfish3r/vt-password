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
 * <code>LowercaseCharacterRule</code> contains methods for determining if a
 * password contains the correct number of lowercase characters.
 *
 * @author  Middleware Services
 * @version  $Revision: 1636 $ $Date: 2010-10-04 11:12:15 -0400 (Mon, 04 Oct 2010) $
 */
public class LowercaseCharacterRule extends AbstractCharacterRule
{

  /**
   * Default constructor.
   */
  public LowercaseCharacterRule() {}


  /**
   * This will create a new <code>LowercaseCharacterRule</code> with the
   * supplied number of lowercase characters to enforce.
   *
   * @param  num  <code>int</code>
   */
  public LowercaseCharacterRule(final int num)
  {
    this.setNumberOfCharacters(num);
  }


  /** {@inheritDoc} */
  public String getValidCharacters()
  {
    return "abcdefghijklmnopqrstuvwxyz";
  }


  /** {@inheritDoc} */
  protected int getNumberOfCharacterType(final Password password)
  {
    return password.getNumberOfLowercase();
  }


  /** {@inheritDoc} */
  protected String getRuleResultDetailMessage()
  {
    return String.format(
      "Password must contain at least %s lowercase characters",
      this.numCharacters);
  }
}
