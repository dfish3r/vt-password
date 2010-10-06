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

/**
 * <code>CharacterCharacteristicsRule</code> contains methods for determining if
 * a password contains the desired mix of character types. In order to meet the
 * criteria of this rule, passwords must meet any number of supplied character
 * rules.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */

public class CharacterCharacteristicsRule extends AggregateRule<CharacterRule>
{

  /** number of rules to enforce. Default value is 1. */
  protected int numCharacteristics = 1;


  /**
   * This sets the number of characteristics which must be satisfied in order
   * for a password to meet the requirements of this rule. The default is one.
   * i.e. you may wish to enforce any three of five supplied character rules.
   *
   * @param  n  <code>int</code> number of characteristics to enforce where
   * n > 0
   */
  public void setNumberOfCharacteristics(final int n)
  {
    if (n > 0) {
      this.numCharacteristics = n;
    } else {
      throw new IllegalArgumentException("argument must be greater than zero");
    }
  }


  /**
   * This returns the number of characteristics which currently must be
   * satisfied in order for a password to meet the requirements of this rule.
   *
   * @return  <code>int</code> number of characteristics to enforce
   */
  public int getNumberOfCharacteristics()
  {
    return this.numCharacteristics;
  }


  /** {@inheritDoc} */
  public RuleResult validate(final Password password)
  {
    int successCount = 0;
    final RuleResult result = new RuleResult(true);
    for (CharacterRule rule : this.rules) {
      final RuleResult rr = rule.validate(password);
      if (!rr.isValid()) {
        result.getDetails().addAll(rr.getDetails());
      } else {
        successCount++;
      }
    }
    if (successCount < this.numCharacteristics) {
      result.setValid(false);
      result.getDetails().add(
        0,
        new RuleResultDetail(
          String.format(
            "Password did not meet %s required character characteristics",
            this.numCharacteristics)));
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
      "%s@%h::numberOfCharacteristics=%s",
      this.getClass().getName(),
      this.hashCode(),
      this.numCharacteristics);
  }
}