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

import java.util.Arrays;

/**
 * Rule for determining if a password contains an illegal character.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */
public class IllegalCharacterRule implements Rule
{

  /** Error code for illegal character failures. */
  public static final String ERROR_CODE = "ILLEGAL_CHAR";

  /** Stores the characters that are not allowed. */
  private char[] illegalChar;


  /**
   * Create a new illegal character rule.
   *
   * @param  c  illegal characters
   */
  public IllegalCharacterRule(final char[] c)
  {
    illegalChar = c;
  }


  /** {@inheritDoc} */
  @Override
  public RuleResult validate(final PasswordData passwordData)
  {
    final RuleResult result = new RuleResult(true);
    for (char c : illegalChar) {
      if (passwordData.getPassword().getText().indexOf(c) != -1) {
        result.setValid(false);
        result.getDetails().add(
          new RuleResultDetail(ERROR_CODE, new Object[] {c}));
      }
    }
    return result;
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
        "%s@%h::illegalChar=%s",
        getClass().getName(),
        hashCode(),
        illegalChar != null ? Arrays.asList(illegalChar) : null);
  }
}
