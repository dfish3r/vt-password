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
 * <code>IllegalCharacterRule</code> contains methods for determining if a
 * password contains an illegal character.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */
public class IllegalCharacterRule implements Rule
{

  /** Error code for regex validation failures. */
  public static final String ERROR_CODE = "ILLEGAL_CHAR";

  /** Stores the characters that are not allowed. */
  private char[] illegalChar;


  /**
   * This will create a new <code>IllegalCharacterRule</code> with the supplied
   * character.
   *
   * @param  c  <code>char[]</code> illegal characters
   */
  public IllegalCharacterRule(final char[] c)
  {
    this.illegalChar = c;
  }


  /** {@inheritDoc} */
  public RuleResult validate(final PasswordData passwordData)
  {
    final RuleResult result = new RuleResult(true);
    for (char c : this.illegalChar) {
      if (passwordData.getPassword().getText().indexOf(c) != -1) {
        result.setValid(false);
        result.getDetails().add(
          new RuleResultDetail(ERROR_CODE, new Object[]{c}));
      }
    }
    return result;
  }


  /**
   * This returns a string representation of this object.
   *
   * @return  <code>String</code>
   */
  @Override
  public String toString()
  {
    return
      String.format(
        "%s@%h::illegalChar=%s",
        this.getClass().getName(),
        this.hashCode(),
        this.illegalChar != null ? Arrays.asList(this.illegalChar) : null);
  }
}
