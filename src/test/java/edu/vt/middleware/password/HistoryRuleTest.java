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

import edu.vt.middleware.crypt.util.Base64Converter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

/**
 * Unit test for {@link HistoryRule}.
 *
 * @author  Middleware Services
 * @version  $Revision$
 */
public class HistoryRuleTest extends AbstractRuleTest
{

  /** Test password. */
  private static final String VALID_PASS = "t3stUs3r00";

  /** Test password. */
  private static final String HISTORY_PASS1 = "t3stUs3r01";

  /** Test password. */
  private static final String HISTORY_PASS2 = "t3stUs3r02";

  /** Test password. */
  private static final String HISTORY_PASS3 = "t3stUs3r03";

  /** For testing. */
  private HistoryRule rule = new HistoryRule();

  /** For testing. */
  private HistoryRule digestRule = new HistoryRule();

  /** For testing. */
  private HistoryRule emptyRule = new HistoryRule();


  /** Initialize rules for this test. */
  @BeforeClass(groups = {"passtest"})
  public void createRules()
  {
    rule.getHistory().add(HISTORY_PASS1);
    rule.getHistory().add(HISTORY_PASS2);
    rule.getHistory().add(HISTORY_PASS3);

    digestRule.setDigest("SHA-1", new Base64Converter());
    digestRule.getHistory().add("safx/LW8+SsSy/o3PmCNy4VEm5s=");
    digestRule.getHistory().add("zurb9DyQ5nooY1la8h86Bh0n1iw=");
    digestRule.getHistory().add("bhqabXwE3S8E6xNJfX/d76MFOCs=");
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
        {this.rule, HISTORY_PASS1, false},
        {this.rule, HISTORY_PASS2, false},
        {this.rule, HISTORY_PASS3, false},

        {this.digestRule, VALID_PASS, true},
        {this.digestRule, HISTORY_PASS1, false},
        {this.digestRule, HISTORY_PASS2, false},
        {this.digestRule, HISTORY_PASS3, false},

        {this.emptyRule, VALID_PASS, true},
        {this.emptyRule, HISTORY_PASS1, true},
        {this.emptyRule, HISTORY_PASS2, true},
        {this.emptyRule, HISTORY_PASS3, true},
      };
  }
}
