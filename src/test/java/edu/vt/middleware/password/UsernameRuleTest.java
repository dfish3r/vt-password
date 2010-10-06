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
  private static final String VALID_PASS = "p4t3stu$er#n65";

  /** Test password. */
  private static final String USERID_PASS = "p4testuser#n65";

  /** Test password. */
  private static final String BACKWARDS_USERID_PASS = "p4resutset#n65";

  /** Test password. */
  private static final String UPPERCASE_USERID_PASS = "p4TEStuSER#n65";

  /** Test password. */
  private static final String BACKWARDS_UPPERCASE_USERID_PASS =
    "p4RESUTsET#n65";

  /** For testing. */
  private UsernameRule rule = new UsernameRule("testuser");

  /** For testing. */
  private UsernameRule backwardsRule = new UsernameRule("testuser");

  /** For testing. */
  private UsernameRule ignoreCaseRule = new UsernameRule("testuser");

  /** For testing. */
  private UsernameRule allRule = new UsernameRule("testuser");


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

        {this.rule, VALID_PASS, true},
        {this.rule, USERID_PASS, false},
        {this.rule, BACKWARDS_USERID_PASS, true},
        {this.rule, UPPERCASE_USERID_PASS, true},
        {this.rule, BACKWARDS_UPPERCASE_USERID_PASS, true},

        {this.backwardsRule, VALID_PASS, true},
        {this.backwardsRule, USERID_PASS, false},
        {this.backwardsRule, BACKWARDS_USERID_PASS, false},
        {this.backwardsRule, UPPERCASE_USERID_PASS, true},
        {this.backwardsRule, BACKWARDS_UPPERCASE_USERID_PASS, true},

        {this.ignoreCaseRule, VALID_PASS, true},
        {this.ignoreCaseRule, USERID_PASS, false},
        {this.ignoreCaseRule, BACKWARDS_USERID_PASS, true},
        {this.ignoreCaseRule, UPPERCASE_USERID_PASS, false},
        {this.ignoreCaseRule, BACKWARDS_UPPERCASE_USERID_PASS, true},

        {this.allRule, VALID_PASS, true},
        {this.allRule, USERID_PASS, false},
        {this.allRule, BACKWARDS_USERID_PASS, false},
        {this.allRule, UPPERCASE_USERID_PASS, false},
        {this.allRule, BACKWARDS_UPPERCASE_USERID_PASS, false},
      };
  }
}
