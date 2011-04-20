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

import java.util.ArrayList;
import java.util.List;

/**
 * <code>PasswordValidator</code> is the central component for evaluating
 * multiple password validation rules against a candidate password.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */

public class PasswordValidator
{

  /** Password rules. */
  private final List<Rule> passwordRules;

  /** Message resolver. */
  private final MessageResolver messageResolver;


  /**
   * Creates a new instance with the default message resolver and the supplied
   * rules.
   *
   * @param  rules  to validate
   */
  public PasswordValidator(final List<Rule> rules)
  {
    this(new MessageResolver(), rules);
  }


  /**
   * Creates a new validator with the given message resolver.
   *
   * @param  resolver  Message resolver.
   * @param  rules  to validate
   */
  public PasswordValidator(
    final MessageResolver resolver, final List<Rule> rules)
  {
    this.messageResolver = resolver;
    this.passwordRules = rules;
  }


  /**
   * Validates the supplied password data against the supplied rule.
   *
   * @param  passwordData  <code>PasswordData</code> to validate
   *
   * @return  <code>RuleResult</code>
   */
  public RuleResult validate(final PasswordData passwordData)
  {
    final RuleResult result = new RuleResult(true);
    for (Rule rule : this.passwordRules) {
      final RuleResult rr = rule.validate(passwordData);
      if (!rr.isValid()) {
        result.setValid(false);
        result.getDetails().addAll(rr.getDetails());
      }
    }
    return result;
  }


  /**
   * Gets a list of human-readable messages by iterating over the details of a
   * failed rule result.
   *
   * @param  result  Failed rule result.
   *
   * @return  List of human-readable messages describing the reason(s) for
   * validation failure.
   */
  public List<String> getMessages(final RuleResult result)
  {
    final List<String> messages = new ArrayList<String>();
    for (RuleResultDetail detail : result.getDetails()) {
      messages.add(this.messageResolver.resolve(detail));
    }
    return messages;
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
        "%s@%h::passwordRules=%s, messageResolver=%s",
        this.getClass().getName(),
        this.hashCode(),
        this.passwordRules,
        this.messageResolver);
  }
}
