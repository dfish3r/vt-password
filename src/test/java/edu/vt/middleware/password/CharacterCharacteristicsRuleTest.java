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

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

/**
 * Unit test for {@link CharacterCharacteristicsRule}.
 *
 * @author  Middleware Services
 * @version  $Revision$
 */
public class CharacterCharacteristicsRuleTest extends AbstractRuleTest
{

  /** Test password. */
  private static final Password VALID_PASS = new Password("r%scvEW2e93)");

  /** Test password. */
  private static final Password ALPHA_PASS = new Password("r%5#8EW2393)");

  /** Test password. */
  private static final Password DIGIT_PASS = new Password("r%scvEW2e9e)");

  /** Test password. */
  private static final Password UPPERCASE_PASS = new Password("r%scv3W2e9)");

  /** Test password. */
  private static final Password LOWERCASE_PASS = new Password("R%s4VEW239)");

  /** Test password. */
  private static final Password NONALPHA_PASS = new Password("r5scvEW2e9b");

  /** For testing. */
  private CharacterCharacteristicsRule rule =
    new CharacterCharacteristicsRule();


  /** Initialize rules for this test. */
  @BeforeClass(groups = {"passtest"})
  public void createRules()
  {
    this.rule.getRules().add(new AlphabeticalCharacterRule(4));
    this.rule.getRules().add(new DigitCharacterRule(3));
    this.rule.getRules().add(new UppercaseCharacterRule(2));
    this.rule.getRules().add(new LowercaseCharacterRule(2));
    this.rule.getRules().add(new NonAlphanumericCharacterRule(1));
    this.rule.setNumberOfCharacteristics(5);
  }


  /**
   * @return  Test data.
   *
   * @throws  Exception  On test data generation failure.
   */
  @DataProvider(name = "passwords")
  public Object[][] passwords()
    throws Exception
  {
    return
      new Object[][] {

        {this.rule, new PasswordData(VALID_PASS), true, },
        {this.rule, new PasswordData(ALPHA_PASS), false, },
        {this.rule, new PasswordData(DIGIT_PASS), false, },
        {this.rule, new PasswordData(UPPERCASE_PASS), false, },
        {this.rule, new PasswordData(LOWERCASE_PASS), false, },
        {this.rule, new PasswordData(NONALPHA_PASS), false, },
      };
  }
}
