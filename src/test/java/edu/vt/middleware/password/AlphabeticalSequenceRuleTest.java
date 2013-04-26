/*
  $Id$

  Copyright (C) 2003-2013 Virginia Tech.
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
 * Unit test for {@link AlphabeticalSequenceRule}.
 *
 * @author  Middleware Services
 * @version  $Revision$
 */
public class AlphabeticalSequenceRuleTest extends AbstractRuleTest
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
        // Test valid password
        {
          new AlphabeticalSequenceRule(),
          new PasswordData(new Password("p4zRcv8#n65")),
          null,
        },
        // Has alphabetical sequence
        {
          new AlphabeticalSequenceRule(7, false),
          new PasswordData(new Password("phijklmn#n65")),
          codes(AlphabeticalSequenceRule.ERROR_CODE),
        },
        // Has wrapping alphabetical sequence with wrap=false
        {
          new AlphabeticalSequenceRule(4, false),
          new PasswordData(new Password("pXyza#n65")),
          null,
        },
        // Has wrapping alphabetical sequence with wrap=true
        {
          new AlphabeticalSequenceRule(4, true),
          new PasswordData(new Password("pxyzA#n65")),
          codes(AlphabeticalSequenceRule.ERROR_CODE),
        },
        // Has backward alphabetical sequence
        {
          new AlphabeticalSequenceRule(),
          new PasswordData(new Password("ptSrqp#n65")),
          codes(AlphabeticalSequenceRule.ERROR_CODE),
        },
        // Has backward wrapping alphabetical sequence with wrap=false
        {
          new AlphabeticalSequenceRule(8, false),
          new PasswordData(new Password("pcBazyXwv#n65")),
          null,
        },
        // Has backward wrapping alphabetical sequence with wrap=true
        {
          new AlphabeticalSequenceRule(8, true),
          new PasswordData(new Password("pcbazyxwv#n65")),
          codes(AlphabeticalSequenceRule.ERROR_CODE),
        },
      };
  }


  /** @throws  Exception  On test failure. */
  @Test(groups = {"passtest"})
  public void resolveMessage()
    throws Exception
  {
    final Rule rule = new AlphabeticalSequenceRule();
    final RuleResult result = rule.validate(
      new PasswordData(new Password("phijkl#n65")));
    for (RuleResultDetail detail : result.getDetails()) {
      AssertJUnit.assertEquals(
        String.format("Password contains the illegal sequence '%s'.", "hijkl"),
        DEFAULT_RESOLVER.resolve(detail));
      AssertJUnit.assertNotNull(EMPTY_RESOLVER.resolve(detail));
    }
  }
}
