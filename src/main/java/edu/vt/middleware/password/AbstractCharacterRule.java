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
 * Provides common implementation for password character rules.
 *
 * @author  Middleware Services
 * @version  $Revision: 1636 $ $Date: 2010-10-04 11:12:15 -0400 (Mon, 04 Oct 2010) $
 */
public abstract class AbstractCharacterRule implements CharacterRule
{

  /** Error code for insufficient number of characters of particular class. */
  public static final String ERROR_CODE = "INSUFFICIENT_CHARACTERS";

  /** Number of characters to require. Default value is 1. */
  protected int numCharacters = 1;


  /** {@inheritDoc} */
  @Override
  public void setNumberOfCharacters(final int n)
  {
    if (n > 0) {
      numCharacters = n;
    } else {
      throw new IllegalArgumentException("argument must be greater than zero");
    }
  }


  /** {@inheritDoc} */
  @Override
  public int getNumberOfCharacters()
  {
    return numCharacters;
  }


  /**
   * Returns the number of the type of characters in the supplied password for
   * the implementing class.
   *
   * @param  password  to get character count from
   *
   * @return  number of characters
   */
  protected abstract int getNumberOfCharacterType(final Password password);


  /**
   * Returns the type of character managed by this rule.
   *
   * @return  name of a character type, e.g. "digits."
   */
  protected abstract String getCharacterType();


  /** {@inheritDoc} */
  @Override
  public RuleResult validate(final PasswordData passwordData)
  {
    if (getNumberOfCharacterType(passwordData.getPassword()) >= numCharacters) {
      return new RuleResult(true);
    } else {
      return
        new RuleResult(
          false,
          new RuleResultDetail(
            ERROR_CODE,
            new Object[] {
              numCharacters,
              getCharacterType(),
              getNumberOfCharacterType(passwordData.getPassword()),
              getValidCharacters(),
            }));
    }
  }


  /**
   * Returns a string representation of this object.
   *
   * @return  string representation
   */
  @Override
  public String toString()
  {
    return
      String.format(
        "%s@%h::numberOfCharacters=%s",
        getClass().getName(),
        hashCode(),
        numCharacters);
  }
}
