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
 * non-alphanumeric characters.
 *
 * @author  Middleware Services
 * @version  $Revision: 1636 $ $Date: 2010-10-04 11:12:15 -0400 (Mon, 04 Oct 2010) $
 */
public class NonAlphanumericCharacterRule extends AbstractCharacterRule
{

  /** Character type. */
  private static final String CHARACTER_TYPE = "non-alphanumeric";


  /** Default constructor. */
  public NonAlphanumericCharacterRule() {}


  /**
   * Creates a new non alphanumeric character rule.
   *
   * @param  num  number of non-alphanumeric characters to enforce
   */
  public NonAlphanumericCharacterRule(final int num)
  {
    this.setNumberOfCharacters(num);
  }


  /** {@inheritDoc} */
  public String getValidCharacters()
  {
    return "`~!@#$%^&*()-_=+[{]}\\|;:'\"<,>./?";
  }


  /** {@inheritDoc} */
  protected int getNumberOfCharacterType(final Password password)
  {
    return password.getNumberOfNonAlphanumeric();
  }


  /** {@inheritDoc} */
  protected String getCharacterType()
  {
    return CHARACTER_TYPE;
  }
}
