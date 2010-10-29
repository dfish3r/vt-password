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
 * <code>RuleList</code> contains methods for setting password rules and then
 * determining if a password meets the requirements of all the rules.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */

public class RuleList implements Rule
{

  /** rules to apply when checking a password. */
  private List<Rule> rules = new ArrayList<Rule>();


  /**
   * This will return the rules being used by this <code>RuleList</code>.
   *
   * @return  <code>List</code> of rules
   */
  public List<Rule> getRules()
  {
    return this.rules;
  }


  /**
   * This will set the rules to be used by this <code>RuleList</code>.
   *
   * @param  l  <code>List</code> of rules
   */
  public void setRules(final List<Rule> l)
  {
    this.rules = l;
  }


  /** {@inheritDoc} */
  public RuleResult validate(final PasswordData passwordData)
  {
    final RuleResult result = new RuleResult(true);
    for (Rule rule : this.rules) {
      final RuleResult rr = rule.validate(passwordData);
      if (!rr.isValid()) {
        result.setValid(false);
        result.getDetails().addAll(rr.getDetails());
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
        "%s@%h::rules=%s",
        this.getClass().getName(),
        this.hashCode(),
        this.rules);
  }
}
