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

import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Base class for all rule tests.
 *
 * @author  Middleware Services
 * @version  $Revision$
 */
public class PasswordValidatorTest
{

  /** Test password. */
  private static final Password VALID_PASS = new Password("aBcD3FgH1Jk");

  /** Test password. */
  private static final Password INVALID_PASS = new Password("aBcDeFgHiJk");

  /** For testing. */
  private static final Username USERNAME = new Username("testuser");

  /** For testing. */
  private RuleList ruleList = new RuleList();

  /** Initialize rules for this test. */
  @BeforeClass(groups = {"passtest"})
  public void createRules()
  {
    this.ruleList.getRules().add(new LengthRule(8, 16));

    final CharacterCharacteristicsRule ccRule =
      new CharacterCharacteristicsRule();
    ccRule.getRules().add(new DigitCharacterRule(1));
    ccRule.getRules().add(new NonAlphanumericCharacterRule(1));
    ccRule.getRules().add(new UppercaseCharacterRule(1));
    ccRule.getRules().add(new LowercaseCharacterRule(1));
    ccRule.setNumberOfCharacteristics(3);
    this.ruleList.getRules().add(ccRule);

    this.ruleList.getRules().add(new SequenceRule(true, true));
  }


  /**
   * @throws  Exception  On test failure.
   */
  @Test(groups = {"passtest"})
  public void validate()
    throws Exception
  {
    AssertJUnit.assertTrue(
      PasswordValidator.validate(this.ruleList, VALID_PASS).isValid());
    AssertJUnit.assertFalse(
      PasswordValidator.validate(this.ruleList, INVALID_PASS).isValid());

    this.ruleList.getRules().add(new UsernameRule(true, true));

    try {
      PasswordValidator.validate(ruleList, VALID_PASS);
      AssertJUnit.fail("Should have thrown UnsupportedOperationException");
    } catch (UnsupportedOperationException e) {
      AssertJUnit.assertEquals(
        e.getClass(), UnsupportedOperationException.class);
    } catch (Exception e) {
      AssertJUnit.fail(
        "Should have thrown UnsupportedOperationException, threw " +
        e.getMessage());
    }

    AssertJUnit.assertTrue(
      PasswordValidator.validate(
        this.ruleList, USERNAME, VALID_PASS).isValid());
    AssertJUnit.assertFalse(
      PasswordValidator.validate(
        this.ruleList, USERNAME, INVALID_PASS).isValid());
  }
}
