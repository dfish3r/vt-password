/*
  $Id$

  Copyright (C) 2003-2011 Virginia Tech.
  All rights reserved.

  SEE LICENSE FOR MORE INFORMATION

  Author:  Middleware Services
  Email:   middleware@vt.edu
  Version: $Revision$
  Updated: $Date$
*/
package edu.vt.middleware.password;

/**
 * <code>DigitCharacterRule</code> contains methods for determining if a
 * password contains the correct number of digit characters.
 *
 * @author  Middleware Services
 * @version  $Revision: 1636 $ $Date: 2010-10-04 11:12:15 -0400 (Mon, 04 Oct 2010) $
 */
public class DigitCharacterRule extends AbstractCharacterRule
{

  /** Character type. */
  private static final String CHARACTER_TYPE = "digit";


  /** Default constructor. */
  public DigitCharacterRule() {}


  /**
   * This will create a new <code>DigitCharacterRule</code> with the supplied
   * number of digit characters to enforce.
   *
   * @param  num  <code>int</code>
   */
  public DigitCharacterRule(final int num)
  {
    this.setNumberOfCharacters(num);
  }


  /** {@inheritDoc} */
  public String getValidCharacters()
  {
    return "0123456789";
  }


  /** {@inheritDoc} */
  protected int getNumberOfCharacterType(final Password password)
  {
    return password.getNumberOfDigits();
  }


  /** {@inheritDoc} */
  protected String getCharacterType()
  {
    return CHARACTER_TYPE;
  }
}
