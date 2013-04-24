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

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Rule for determining if a password matches a password from a different
 * source. Useful for when separate systems cannot have matching passwords. If
 * no sources have been set or an empty source has been set, then passwords will
 * meet this rule.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */
public class SourceRule extends AbstractDigester implements Rule
{

  /** Error code for regex validation failures. */
  public static final String ERROR_CODE = "SOURCE_VIOLATION";


  /** {@inheritDoc} */
  @Override
  public RuleResult validate(final PasswordData passwordData)
  {
    final RuleResult result = new RuleResult(true);
    if (passwordData.getPasswordSources().isEmpty()) {
      return result;
    }

    final String cleartext = passwordData.getPassword().getText();
    for (String source : passwordData.getPasswordSources().keySet()) {
      if (matches(cleartext, passwordData.getPasswordSources().get(source))) {
        result.setValid(false);
        result.getDetails().add(
          new RuleResultDetail(
            ERROR_CODE,
            createRuleResultDetailParameters(source)));
      }
    }
    return result;
  }


  /**
   * Creates the parameter data for the rule result detail.
   *
   * @param  source  matching source
   *
   * @return  map of parameter name to value
   */
  protected Map<String, ?> createRuleResultDetailParameters(final String source)
  {
    final Map<String, Object> m = new LinkedHashMap<String, Object>();
    m.put("source", source);
    return m;
  }
}
