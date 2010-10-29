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

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

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
    this.backwardsRule.setMatchBackwards(true);
    this.ignoreCaseRule.setIgnoreCase(true);
    this.allRule.setMatchBackwards(true);
    this.allRule.setIgnoreCase(true);
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
          this.rule,
          createPasswordData(VALID_PASS, USER, null, null),
          true,
        },
        {
          this.rule,
          createPasswordData(USERID_PASS, USER, null, null),
          false,
        },
        {
          this.rule,
          createPasswordData(BACKWARDS_USERID_PASS, USER, null, null),
          true,
        },
        {
          this.rule,
          createPasswordData(UPPERCASE_USERID_PASS, USER, null, null),
          true,
        },
        {
          this.rule,
          createPasswordData(BACKWARDS_UPPERCASE_USERID_PASS, USER, null, null),
          true,
        },

        {
          this.backwardsRule,
          createPasswordData(VALID_PASS, USER, null, null),
          true,
        },
        {
          this.backwardsRule,
          createPasswordData(USERID_PASS, USER, null, null),
          false,
        },
        {
          this.backwardsRule,
          createPasswordData(BACKWARDS_USERID_PASS, USER, null, null),
          false,
        },
        {
          this.backwardsRule,
          createPasswordData(UPPERCASE_USERID_PASS, USER, null, null),
          true,
        },
        {
          this.backwardsRule,
          createPasswordData(BACKWARDS_UPPERCASE_USERID_PASS, USER, null, null),
          true,
        },

        {
          this.ignoreCaseRule,
          createPasswordData(VALID_PASS, USER, null, null),
          true,
        },
        {
          this.ignoreCaseRule,
          createPasswordData(USERID_PASS, USER, null, null),
          false,
        },
        {
          this.ignoreCaseRule,
          createPasswordData(BACKWARDS_USERID_PASS, USER, null, null),
          true,
        },
        {
          this.ignoreCaseRule,
          createPasswordData(UPPERCASE_USERID_PASS, USER, null, null),
          false,
        },
        {
          this.ignoreCaseRule,
          createPasswordData(BACKWARDS_UPPERCASE_USERID_PASS, USER, null, null),
          true,
        },

        {
          this.allRule,
          createPasswordData(VALID_PASS, USER, null, null),
          true,
        },
        {
          this.allRule,
          createPasswordData(USERID_PASS, USER, null, null),
          false,
        },
        {
          this.allRule,
          createPasswordData(BACKWARDS_USERID_PASS, USER, null, null),
          false,
        },
        {
          this.allRule,
          createPasswordData(UPPERCASE_USERID_PASS, USER, null, null),
          false,
        },
        {
          this.allRule,
          createPasswordData(BACKWARDS_UPPERCASE_USERID_PASS, USER, null, null),
          false,
        },
      };
  }
}
