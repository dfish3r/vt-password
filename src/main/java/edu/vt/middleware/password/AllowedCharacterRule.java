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
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Rule for determining if a password contains allowed characters.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */
public class AllowedCharacterRule implements Rule
{

  /** Error code for allowed character failures. */
  public static final String ERROR_CODE = "ALLOWED_CHAR";

  /** Stores the characters that are allowed. */
  private final char[] allowedChar;


  /**
   * Create a new allowed character rule.
   *
   * @param  c  allowed characters
   */
  public AllowedCharacterRule(final char[] c)
  {
    allowedChar = c;
    Arrays.sort(allowedChar);
  }


  /** {@inheritDoc} */
  @Override
  public RuleResult validate(final PasswordData passwordData)
  {
    final RuleResult result = new RuleResult(true);

    for (char c : passwordData.getPassword().getText().toCharArray()) {
      if (Arrays.binarySearch(allowedChar, c) < 0) {
        result.setValid(false);
        result.getDetails().add(
          new RuleResultDetail(
            ERROR_CODE, createRuleResultDetailParameters(c)));
        break;
      }
    }
    return result;
  }


  /**
   * Creates the parameter data for the rule result detail.
   *
   * @param  c  illegal character
   *
   * @return  map of parameter name to value
   */
  protected Map<String, ?> createRuleResultDetailParameters(final char c)
  {
    final Map<String, Object> m = new LinkedHashMap<String, Object>();
    m.put("illegalCharacter", c);
    return m;
  }


  /** {@inheritDoc} */
  @Override
  public String toString()
  {
    return
      String.format(
        "%s@%h::allowedChar=%s",
        getClass().getName(),
        hashCode(),
        allowedChar != null ? Arrays.toString(allowedChar) : null);
  }
}
