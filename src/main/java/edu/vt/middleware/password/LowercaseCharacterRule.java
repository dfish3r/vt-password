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

/**
 * Rule for determining if a password contains the correct number of lowercase
 * characters.
 *
 * @author  Middleware Services
 * @version  $Revision: 1636 $ $Date: 2010-10-04 11:12:15 -0400 (Mon, 04 Oct 2010) $
 */
public class LowercaseCharacterRule extends AbstractCharacterRule
{

  /** Character type. */
  private static final String CHARACTER_TYPE = "lowercase";


  /** Default constructor. */
  public LowercaseCharacterRule() {}


  /**
   * Creates a new lowercase character rule.
   *
   * @param  num  number of lowercase characters to enforce
   */
  public LowercaseCharacterRule(final int num)
  {
    setNumberOfCharacters(num);
  }


  /** {@inheritDoc} */
  @Override
  public String getValidCharacters()
  {
    return "abcdefghijklmnopqrstuvwxyz";
  }


  /** {@inheritDoc} */
  @Override
  protected int getNumberOfCharacterType(final Password password)
  {
    return password.getNumberOfLowercase();
  }


  /** {@inheritDoc} */
  @Override
  protected String getCharacterType()
  {
    return CHARACTER_TYPE;
  }
}
