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
 * Rule for determining if a password matches one of any previous password a
 * user has chosen. If no password history has been set or an empty history has
 * been set, then passwords will meet this rule.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */
public class HistoryRule extends AbstractDigester implements Rule
{

  /** Error code for history violation. */
  public static final String ERROR_CODE = "HISTORY_VIOLATION";


  /** {@inheritDoc} */
  @Override
  public RuleResult validate(final PasswordData passwordData)
  {
    final RuleResult result = new RuleResult(true);
    final int size = passwordData.getPasswordHistory().size();
    if (size == 0) {
      return result;
    }

    final String cleartext = passwordData.getPassword().getText();
    for (String previousPassword : passwordData.getPasswordHistory()) {
      if (matches(cleartext, previousPassword)) {
        result.setValid(false);
        result.getDetails().add(
          new RuleResultDetail(
            ERROR_CODE,
            createRuleResultDetailParameters(size)));
      }
    }
    return result;
  }


  /**
   * Creates the parameter data for the rule result detail.
   *
   * @param  size  of the history list
   *
   * @return  map of parameter name to value
   */
  protected Map<String, ?> createRuleResultDetailParameters(final int size)
  {
    final Map<String, Object> m = new LinkedHashMap<String, Object>();
    m.put("historySize", size);
    return m;
  }
}
