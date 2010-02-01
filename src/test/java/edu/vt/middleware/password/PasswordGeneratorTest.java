/*
  $Id$

  Copyright (C) 2003-2008 Virginia Tech.
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
  private PasswordCharacterRule genCharRule = new PasswordCharacterRule();

  /** Rule to verify passwords with. */
  private PasswordCharacterRule verifyCharRule = new PasswordCharacterRule();

  /** Rule to verify passwords with that should fail. */
  private PasswordCharacterRule failCharRule = new PasswordCharacterRule();


  /**
   * @throws  Exception  On test failure.
   */
  @BeforeClass(groups = {"passgentest"})
  public void initializeRules()
    throws Exception
  {
    this.genCharRule.setNumberOfDigits(2);
    this.genCharRule.setNumberOfNonAlphanumeric(2);
    this.genCharRule.setNumberOfUppercase(1);
    this.genCharRule.setNumberOfLowercase(1);
    this.genCharRule.setNumberOfCharacteristics(3);

    this.verifyCharRule.setNumberOfDigits(2);
    this.verifyCharRule.setNumberOfNonAlphanumeric(2);
    this.verifyCharRule.setNumberOfUppercase(1);
    this.verifyCharRule.setNumberOfLowercase(1);
    this.verifyCharRule.setNumberOfCharacteristics(3);

    this.failCharRule.setNumberOfDigits(3);
    this.failCharRule.setNumberOfNonAlphanumeric(3);
    this.failCharRule.setNumberOfUppercase(3);
    this.failCharRule.setNumberOfLowercase(3);
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
        length, genCharRule);
      AssertJUnit.assertNotNull(password);
      AssertJUnit.assertTrue(password.length() >= length);
      passwords[i] = new Object[] {new Password(password)};
    }
    return passwords;
  }


  /**
   * @param  pass  <code>Password</code> to verify
   * @throws  Exception  On test failure.
   */
  @Test(
    groups = {"passgentest"},
    dataProvider = "randomPasswords"
  )
  public void testGenerator(final Password pass) throws Exception
  {
    AssertJUnit.assertFalse(this.failCharRule.verifyPassword(pass));
    AssertJUnit.assertTrue(this.verifyCharRule.verifyPassword(pass));
  }
}
