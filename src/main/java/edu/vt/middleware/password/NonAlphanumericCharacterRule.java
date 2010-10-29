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

/**
 * <code>NonAlphanumericCharacterRule</code> contains methods for determining if
 * a password contains the correct number of non-alphanumeric characters.
 *
 * @author  Middleware Services
 * @version  $Revision: 1636 $ $Date: 2010-10-04 11:12:15 -0400 (Mon, 04 Oct 2010) $
 */
public class NonAlphanumericCharacterRule extends AbstractCharacterRule
{

  /** Default constructor. */
  public NonAlphanumericCharacterRule() {}


  /**
   * This will create a new <code>NonAlphanumericCharacterRule</code> with the
   * supplied number of non-alphanumeric characters to enforce.
   *
   * @param  num  <code>int</code>
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
  protected String getRuleResultDetailMessage()
  {
    return
      String.format(
        "Password must contain at least %s non-alphanumeric characters",
        this.numCharacters);
  }
}
