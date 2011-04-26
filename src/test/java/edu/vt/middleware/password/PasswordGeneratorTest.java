/*
  $Id$

  Copyright (C) 2003-2011 Virginia Tech.
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
    rules.add(new DigitCharacterRule(2));
    rules.add(new NonAlphanumericCharacterRule(2));
    rules.add(new UppercaseCharacterRule(1));
    rules.add(new LowercaseCharacterRule(1));

    genCharRule.getRules().addAll(rules);
    genCharRule.setNumberOfCharacteristics(3);

    verifyCharRule.getRules().addAll(rules);
    verifyCharRule.setNumberOfCharacteristics(3);

    failRules.add(new DigitCharacterRule(3));
    failRules.add(new NonAlphanumericCharacterRule(3));
    failRules.add(new UppercaseCharacterRule(3));
    failRules.add(new LowercaseCharacterRule(3));

    failCharRule.getRules().addAll(failRules);
    failCharRule.setNumberOfCharacteristics(4);
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
      final String password = generator.generatePassword(
        length,
        rules);
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
      failCharRule.validate(new PasswordData(pass)).isValid());
    AssertJUnit.assertTrue(
      verifyCharRule.validate(new PasswordData(pass)).isValid());
  }
}
