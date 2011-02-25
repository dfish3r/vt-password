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
  private static final String USER = "testuser";

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

    this.ruleList.getRules().add(new QwertySequenceRule());
    this.ruleList.getRules().add(new AlphabeticalSequenceRule());
    this.ruleList.getRules().add(new NumericalSequenceRule());
    this.ruleList.getRules().add(new RepeatCharacterRegexRule());
  }


  /** @throws  Exception  On test failure. */
  @Test(groups = {"passtest"})
  public void validate()
    throws Exception
  {
    AssertJUnit.assertTrue(
      PasswordValidator.validate(this.ruleList, new PasswordData(VALID_PASS))
          .isValid());
    AssertJUnit.assertFalse(
      PasswordValidator.validate(
        this.ruleList,
        new PasswordData(INVALID_PASS)).isValid());

    this.ruleList.getRules().add(new UsernameRule(true, true));

    try {
      PasswordValidator.validate(ruleList, new PasswordData(VALID_PASS));
      AssertJUnit.fail("Should have thrown NullPointerException");
    } catch (NullPointerException e) {
      AssertJUnit.assertEquals(e.getClass(), NullPointerException.class);
    } catch (Exception e) {
      AssertJUnit.fail(
        "Should have thrown NullPointerException, threw " + e.getMessage());
    }

    final PasswordData valid = new PasswordData(VALID_PASS);
    valid.setUsername(USER);
    AssertJUnit.assertTrue(
      PasswordValidator.validate(this.ruleList, valid).isValid());

    final PasswordData invalid = new PasswordData(INVALID_PASS);
    invalid.setUsername(USER);
    AssertJUnit.assertFalse(
      PasswordValidator.validate(this.ruleList, invalid).isValid());
  }
}
