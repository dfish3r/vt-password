/*
  $Id$

  Copyright (C) 2003-2010 Virginia Tech.
  All rights reserved.

  SEE LICENSE FOR MORE INFORMATION

  Author:  Sean C. Sullivan
  Author:  Middleware Services
  Email:   sean@seansullivan.com
  Email:   middleware@vt.edu
  Version: $Revision$
  Updated: $Date$
*/
package edu.vt.middleware.password;

import java.util.ArrayList;
import java.util.List;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Unit test for {@link PasswordGenerator}.
 *
 * @author  Sean C. Sullivan
 * @author  Middleware Services
 * @version  $Revision$
 */
public class PasswordGeneratorTest
{

  /** To generate passwords with. */
  private PasswordGenerator generator = new PasswordGenerator();

  /** Rule to generate passwords with. */
  private CharacterCharacteristicsRule genCharRule =
    new CharacterCharacteristicsRule();

  /** Rule to verify passwords with. */
  private CharacterCharacteristicsRule verifyCharRule =
    new CharacterCharacteristicsRule();

  /** Rule to verify passwords with that should fail. */
  private CharacterCharacteristicsRule failCharRule =
    new CharacterCharacteristicsRule();

  /** Rules to test. */
  private List<CharacterRule> rules = new ArrayList<CharacterRule>();

  /** Rules to test. */
  private List<CharacterRule> failRules = new ArrayList<CharacterRule>();


  /** @throws  Exception  On test failure. */
  @BeforeClass(groups = {"passgentest"})
  public void initializeRules()
    throws Exception
  {
    this.rules.add(new DigitCharacterRule(2));
    this.rules.add(new NonAlphanumericCharacterRule(2));
    this.rules.add(new UppercaseCharacterRule(1));
    this.rules.add(new LowercaseCharacterRule(1));

    this.genCharRule.getRules().addAll(this.rules);
    this.genCharRule.setNumberOfCharacteristics(3);

    this.verifyCharRule.getRules().addAll(this.rules);
    this.verifyCharRule.setNumberOfCharacteristics(3);

    this.failRules.add(new DigitCharacterRule(3));
    this.failRules.add(new NonAlphanumericCharacterRule(3));
    this.failRules.add(new UppercaseCharacterRule(3));
    this.failRules.add(new LowercaseCharacterRule(3));

    this.failCharRule.getRules().addAll(this.failRules);
    this.failCharRule.setNumberOfCharacteristics(4);
  }


  /**
   * @return  Test data.
   *
   * @throws  Exception  On test data generation failure.
   */
  @DataProvider(name = "randomPasswords")
  public Object[][] randomPasswords()
    throws Exception
  {
    final Object[][] passwords = new Object[100][1];
    final int length = 10;
    for (int i = 0; i < 100; i++) {
      final String password = this.generator.generatePassword(
        length,
        this.rules);
      AssertJUnit.assertNotNull(password);
      AssertJUnit.assertTrue(password.length() >= length);
      passwords[i] = new Object[] {new Password(password)};
    }
    return passwords;
  }


  /**
   * @param  pass  <code>Password</code> to verify
   *
   * @throws  Exception  On test failure.
   */
  @Test(
    groups = {"passgentest"},
    dataProvider = "randomPasswords"
  )
  public void testGenerator(final Password pass)
    throws Exception
  {
    AssertJUnit.assertFalse(
      this.failCharRule.validate(new PasswordData(pass)).isValid());
    AssertJUnit.assertTrue(
      this.verifyCharRule.validate(new PasswordData(pass)).isValid());
  }
}
