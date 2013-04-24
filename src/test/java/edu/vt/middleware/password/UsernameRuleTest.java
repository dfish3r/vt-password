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
  private final UsernameRule rule = new UsernameRule();

  /** For testing. */
  private final UsernameRule backwardsRule = new UsernameRule();

  /** For testing. */
  private final UsernameRule ignoreCaseRule = new UsernameRule();

  /** For testing. */
  private final UsernameRule allRule = new UsernameRule();


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
          null,
        },
        {
          rule,
          PasswordData.newInstance(USERID_PASS, USER, null, null),
          codes(UsernameRule.ERROR_CODE),
        },
        {
          rule,
          PasswordData.newInstance(BACKWARDS_USERID_PASS, USER, null, null),
          null,
        },
        {
          rule,
          PasswordData.newInstance(UPPERCASE_USERID_PASS, USER, null, null),
          null,
        },
        {
          rule,
          PasswordData.newInstance(
            BACKWARDS_UPPERCASE_USERID_PASS, USER, null, null),
          null,
        },

        {
          backwardsRule,
          PasswordData.newInstance(VALID_PASS, USER, null, null),
          null,
        },
        {
          backwardsRule,
          PasswordData.newInstance(USERID_PASS, USER, null, null),
          codes(UsernameRule.ERROR_CODE),
        },
        {
          backwardsRule,
          PasswordData.newInstance(BACKWARDS_USERID_PASS, USER, null, null),
          codes(UsernameRule.ERROR_CODE_REVERSED),
        },
        {
          backwardsRule,
          PasswordData.newInstance(UPPERCASE_USERID_PASS, USER, null, null),
          null,
        },
        {
          backwardsRule,
          PasswordData.newInstance(
            BACKWARDS_UPPERCASE_USERID_PASS, USER, null, null),
          null,
        },

        {
          ignoreCaseRule,
          PasswordData.newInstance(VALID_PASS, USER, null, null),
          null,
        },
        {
          ignoreCaseRule,
          PasswordData.newInstance(USERID_PASS, USER, null, null),
          codes(UsernameRule.ERROR_CODE),
        },
        {
          ignoreCaseRule,
          PasswordData.newInstance(BACKWARDS_USERID_PASS, USER, null, null),
          null,
        },
        {
          ignoreCaseRule,
          PasswordData.newInstance(UPPERCASE_USERID_PASS, USER, null, null),
          codes(UsernameRule.ERROR_CODE),
        },
        {
          ignoreCaseRule,
          PasswordData.newInstance(
            BACKWARDS_UPPERCASE_USERID_PASS, USER, null, null),
          null,
        },

        {
          allRule,
          PasswordData.newInstance(VALID_PASS, USER, null, null),
          null,
        },
        {
          allRule,
          PasswordData.newInstance(USERID_PASS, USER, null, null),
          codes(UsernameRule.ERROR_CODE),
        },
        {
          allRule,
          PasswordData.newInstance(BACKWARDS_USERID_PASS, USER, null, null),
          codes(UsernameRule.ERROR_CODE_REVERSED),
        },
        {
          allRule,
          PasswordData.newInstance(UPPERCASE_USERID_PASS, USER, null, null),
          codes(UsernameRule.ERROR_CODE),
        },
        {
          allRule,
          PasswordData.newInstance(
            BACKWARDS_UPPERCASE_USERID_PASS, USER, null, null),
          codes(UsernameRule.ERROR_CODE_REVERSED),
        },
      };
  }


  /** @throws  Exception  On test failure. */
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
      AssertJUnit.assertNotNull(EMPTY_RESOLVER.resolve(detail));
    }

    result = rule.validate(
      PasswordData.newInstance(BACKWARDS_USERID_PASS, USER, null, null));
    for (RuleResultDetail detail : result.getDetails()) {
      AssertJUnit.assertEquals(
        String.format("Password contains the user id '%s' in reverse.", USER),
        DEFAULT_RESOLVER.resolve(detail));
      AssertJUnit.assertNotNull(EMPTY_RESOLVER.resolve(detail));
    }
  }
}
