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
 * Rule for determining if a password contains the correct number of uppercase
 * characters.
 *
 * @author  Middleware Services
 * @version  $Revision: 1636 $ $Date: 2010-10-04 11:12:15 -0400 (Mon, 04 Oct 2010) $
 */
public class UppercaseCharacterRule extends AbstractCharacterRule
{

  /** Character type. */
  private static final String CHARACTER_TYPE = "uppercase";


  /** Default constructor. */
  public UppercaseCharacterRule() {}


  /**
   * Create a new uppercase character rule.
   *
   * @param  num  number of uppercase characters to enforce
   */
  public UppercaseCharacterRule(final int num)
  {
    this.setNumberOfCharacters(num);
  }


  /** {@inheritDoc} */
  public String getValidCharacters()
  {
    return "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  }


  /** {@inheritDoc} */
  protected int getNumberOfCharacterType(final Password password)
  {
    return password.getNumberOfUppercase();
  }


  /** {@inheritDoc} */
  protected String getCharacterType()
  {
    return CHARACTER_TYPE;
  }
}
