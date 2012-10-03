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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Rule for determining if a password matches a regular expression.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */
public class RegexRule implements Rule
{

  /** Error code for regex validation failures. */
  public static final String ERROR_CODE = "ILLEGAL_MATCH";

  /** Regex pattern. */
  protected final Pattern pattern;


  /**
   * Creates a new regex rule.
   *
   * @param  regex  regular expression
   */
  public RegexRule(final String regex)
  {
    pattern = Pattern.compile(regex);
  }


  /** {@inheritDoc} */
  @Override
  public RuleResult validate(final PasswordData passwordData)
  {
    final RuleResult result = new RuleResult(true);
    final Matcher m = pattern.matcher(passwordData.getPassword().getText());
    if (m.find()) {
      result.setValid(false);
      result.getDetails().add(
        new RuleResultDetail(
          ERROR_CODE, createRuleResultDetailParameters(m.group())));
    }
    return result;
  }


  /**
   * Creates the parameter data for the rule result detail.
   *
   * @param  match  matching regex
   *
   * @return  map of parameter name to value
   */
  protected Map<String, ?> createRuleResultDetailParameters(final String match)
  {
    final Map<String, Object> m = new LinkedHashMap<String, Object>();
    m.put("match", match);
    m.put("pattern", pattern);
    return m;
  }


  /** {@inheritDoc} */
  @Override
  public String toString()
  {
    return
      String.format(
        "%s@%h::pattern=%s",
        getClass().getName(),
        hashCode(),
        pattern);
  }
}
