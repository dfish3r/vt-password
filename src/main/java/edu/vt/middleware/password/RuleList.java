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

import java.util.ArrayList;
import java.util.List;

/**
 * <code>RuleList</code> contains methods for setting password rules and
 * then determining if a password meets the requirements of all the rules.
 *
 * @param  <T>  type of rules to validate
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */

public class RuleList<T extends Rule> implements Rule
{

  /** rules to apply when checking a password. */
  protected List<T> rules = new ArrayList<T>();


  /**
   * This will return the rules being used by this <code>RuleList</code>.
   *
   * @return  <code>List</code> of rules
   */
  public List<T> getRules()
  {
    return this.rules;
  }


  /**
   * This will set the rules to be used by this <code>RuleList</code>.
   *
   * @param  l  <code>List</code> of rules
   */
  public void setRules(final List<T> l)
  {
    this.rules = l;
  }


  /** {@inheritDoc} */
  public RuleResult validate(final Password password)
  {
    final RuleResult result = new RuleResult(true);
    for (Rule rule : this.rules) {
      final RuleResult rr = rule.validate(password);
      if (!rr.isValid()) {
        result.setValid(false);
        result.getDetails().addAll(rr.getDetails());
      }
    }
    return result;
  }


  /** {@inheritDoc} */
  public RuleResult validate(final Username username, final Password password)
  {
    final RuleResult result = new RuleResult(true);
    for (Rule rule : this.rules) {
      final RuleResult rr = rule.validate(username, password);
      if (!rr.isValid()) {
        result.setValid(false);
        result.getDetails().addAll(rr.getDetails());
      }
    }
    return result;
  }
}