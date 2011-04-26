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
 * Rule for determining if a password contains the correct number of
 * alphabetical characters.
 *
 * @author  Middleware Services
 * @version  $Revision: 1636 $ $Date: 2010-10-04 11:12:15 -0400 (Mon, 04 Oct 2010) $
 */
public class AlphabeticalCharacterRule extends AbstractCharacterRule
{

  /** Character type. */
  private static final String CHARACTER_TYPE = "alphabetical";


  /** Default constructor. */
  public AlphabeticalCharacterRule() {}


  /**
   * Creates a new alphabetical character rule.
   *
   * @param  num  of alphabetical characters to enforce
   */
  public AlphabeticalCharacterRule(final int num)
  {
    setNumberOfCharacters(num);
  }


  /** {@inheritDoc} */
  @Override
  public String getValidCharacters()
  {
    return "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
  }


  /** {@inheritDoc} */
  @Override
  protected int getNumberOfCharacterType(final Password password)
  {
    return password.getNumberOfAlphabetical();
  }


  /** {@inheritDoc} */
  @Override
  protected String getCharacterType()
  {
    return CHARACTER_TYPE;
  }
}
