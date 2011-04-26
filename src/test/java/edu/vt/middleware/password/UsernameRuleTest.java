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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Unit test for {@link UsernameRule}.
 *
 * @author  Middleware Services
 * @version  $Revision$
 */
public class UsernameRuleTest extends AbstractRuleTest
{

  /** Test password. */
  private static final Password VALID_PASS = new Password("p4t3stu$er#n65");

  /** Test password. */
  private static final Password USERID_PASS = new Password("p4testuser#n65");

  /** Test password. */
  private static final Password BACKWARDS_USERID_PASS = new Password(
    "p4resutset#n65");

  /** Test password. */
  private static final Password UPPERCASE_USERID_PASS = new Password(
    "p4TEStuSER#n65");

  /** Test password. */
  private static final Password BACKWARDS_UPPERCASE_USERID_PASS = new Password(
    "p4RESUTsET#n65");

  /** Test username. */
  private static final String USER = "testuser";

  /** For testing. */
  private UsernameRule rule = new UsernameRule();

  /** For testing. */
  private UsernameRule backwardsRule = new UsernameRule();

  /** For testing. */
  private UsernameRule ignoreCaseRule = new UsernameRule();

  /** For testing. */
  private UsernameRule allRule = new UsernameRule();


  /** Initialize rules for this test. */
  @BeforeClass(groups = {"passtest"})
  public void createRules()
  {
    backwardsRule.setMatchBackwards(true);
    ignoreCaseRule.setIgnoreCase(true);
    allRule.setMatchBackwards(true);
    allRule.setIgnoreCase(true);
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

        {
          rule,
          PasswordData.newInstance(VALID_PASS, USER, null, null),
          true,
        },
        {
          rule,
          PasswordData.newInstance(USERID_PASS, USER, null, null),
          false,
        },
        {
          rule,
          PasswordData.newInstance(BACKWARDS_USERID_PASS, USER, null, null),
          true,
        },
        {
          rule,
          PasswordData.newInstance(UPPERCASE_USERID_PASS, USER, null, null),
          true,
        },
        {
          rule,
          PasswordData.newInstance(
            BACKWARDS_UPPERCASE_USERID_PASS, USER, null, null),
          true,
        },

        {
          backwardsRule,
          PasswordData.newInstance(VALID_PASS, USER, null, null),
          true,
        },
        {
          backwardsRule,
          PasswordData.newInstance(USERID_PASS, USER, null, null),
          false,
        },
        {
          backwardsRule,
          PasswordData.newInstance(BACKWARDS_USERID_PASS, USER, null, null),
          false,
        },
        {
          backwardsRule,
          PasswordData.newInstance(UPPERCASE_USERID_PASS, USER, null, null),
          true,
        },
        {
          backwardsRule,
          PasswordData.newInstance(
            BACKWARDS_UPPERCASE_USERID_PASS, USER, null, null),
          true,
        },

        {
          ignoreCaseRule,
          PasswordData.newInstance(VALID_PASS, USER, null, null),
          true,
        },
        {
          ignoreCaseRule,
          PasswordData.newInstance(USERID_PASS, USER, null, null),
          false,
        },
        {
          ignoreCaseRule,
          PasswordData.newInstance(BACKWARDS_USERID_PASS, USER, null, null),
          true,
        },
        {
          ignoreCaseRule,
          PasswordData.newInstance(UPPERCASE_USERID_PASS, USER, null, null),
          false,
        },
        {
          ignoreCaseRule,
          PasswordData.newInstance(
            BACKWARDS_UPPERCASE_USERID_PASS, USER, null, null),
          true,
        },

        {
          allRule,
          PasswordData.newInstance(VALID_PASS, USER, null, null),
          true,
        },
        {
          allRule,
          PasswordData.newInstance(USERID_PASS, USER, null, null),
          false,
        },
        {
          allRule,
          PasswordData.newInstance(BACKWARDS_USERID_PASS, USER, null, null),
          false,
        },
        {
          allRule,
          PasswordData.newInstance(UPPERCASE_USERID_PASS, USER, null, null),
          false,
        },
        {
          allRule,
          PasswordData.newInstance(
            BACKWARDS_UPPERCASE_USERID_PASS, USER, null, null),
          false,
        },
      };
  }


  /**
   * @throws  Exception  On test failure.
   */
  @Test(groups = {"passtest"})
  public void resolveMessage()
    throws Exception
  {
    RuleResult result = rule.validate(
      PasswordData.newInstance(USERID_PASS, USER, null, null));
    for (RuleResultDetail detail : result.getDetails()) {
      AssertJUnit.assertEquals(
        String.format("Password contains the user id '%s'.", USER),
        DEFAULT_RESOLVER.resolve(detail));
    }

    result = rule.validate(
      PasswordData.newInstance(BACKWARDS_USERID_PASS, USER, null, null));
    for (RuleResultDetail detail : result.getDetails()) {
      AssertJUnit.assertEquals(
        String.format("Password contains the user id '%s' in reverse.", USER),
        DEFAULT_RESOLVER.resolve(detail));
    }
  }
}
