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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Unit test for {@link RegexRule}.
 *
 * @author  Middleware Services
 * @version  $Revision$
 */
public class RegexRuleTest extends AbstractRuleTest
{


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
        // test valid password
        {
          new RegexRule("\\d\\d\\d\\d"),
          new PasswordData(new Password("p4zRcv8#n65")),
          true,
        },
        // test entire password
        {
          new RegexRule("^[\\p{Alpha}]+\\d\\d\\d\\d$"),
          new PasswordData(new Password("pwUiNh0248")),
          false,
        },
        // test find password
        {
          new RegexRule("\\d\\d\\d\\d"),
          new PasswordData(new Password("pwUi0248xwK")),
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
    final Rule rule = new RegexRule("\\d\\d\\d\\d");
    final RuleResult result = rule.validate(
      new PasswordData(new Password("pwUiNh0248")));
    for (RuleResultDetail detail : result.getDetails()) {
      AssertJUnit.assertEquals(
        String.format("Password matches the illegal sequence '%s'.", "0248"),
        DEFAULT_RESOLVER.resolve(detail));
    }
  }
}
