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
  private static final Password BACKWARDS_USERID_PASS =
    new Password("p4resutset#n65");

  /** Test password. */
  private static final Password UPPERCASE_USERID_PASS =
    new Password("p4TEStuSER#n65");

  /** Test password. */
  private static final Password BACKWARDS_UPPERCASE_USERID_PASS =
    new Password("p4RESUTsET#n65");

  /** Test username */
  private static final Username USERNAME = new Username("testuser");

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

        {this.rule, new PasswordData(USERNAME, VALID_PASS), true, },
        {this.rule, new PasswordData(USERNAME, USERID_PASS), false, },
        {this.rule, new PasswordData(USERNAME, BACKWARDS_USERID_PASS), true, },
        {this.rule, new PasswordData(USERNAME, UPPERCASE_USERID_PASS), true, },
        {
          this.rule,
          new PasswordData(USERNAME, BACKWARDS_UPPERCASE_USERID_PASS),
          true,
        },

        {this.backwardsRule, new PasswordData(USERNAME, VALID_PASS), true, },
        {this.backwardsRule, new PasswordData(USERNAME, USERID_PASS), false, },
        {
          this.backwardsRule,
          new PasswordData(USERNAME, BACKWARDS_USERID_PASS),
          false,
        },
        {
          this.backwardsRule,
          new PasswordData(USERNAME, UPPERCASE_USERID_PASS),
          true,
        },
        {
          this.backwardsRule,
          new PasswordData(USERNAME, BACKWARDS_UPPERCASE_USERID_PASS),
          true,
        },

        {this.ignoreCaseRule, new PasswordData(USERNAME, VALID_PASS), true, },
        {this.ignoreCaseRule, new PasswordData(USERNAME, USERID_PASS), false, },
        {
          this.ignoreCaseRule,
          new PasswordData(USERNAME, BACKWARDS_USERID_PASS),
          true,
        },
        {
          this.ignoreCaseRule,
          new PasswordData(USERNAME, UPPERCASE_USERID_PASS),
          false,
        },
        {
          this.ignoreCaseRule,
          new PasswordData(USERNAME, BACKWARDS_UPPERCASE_USERID_PASS),
          true,
        },

        {this.allRule, new PasswordData(USERNAME, VALID_PASS), true, },
        {this.allRule, new PasswordData(USERNAME, USERID_PASS), false, },
        {
          this.allRule,
          new PasswordData(USERNAME, BACKWARDS_USERID_PASS),
          false,
        },
        {
          this.allRule,
          new PasswordData(USERNAME, UPPERCASE_USERID_PASS),
          false,
        },
        {
          this.allRule,
          new PasswordData(USERNAME, BACKWARDS_UPPERCASE_USERID_PASS),
          false,
        },
      };
  }
}
