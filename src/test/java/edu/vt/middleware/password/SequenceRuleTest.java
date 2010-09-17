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
 * Unit test for {@link SequenceRule}.
 *
 * @author  Middleware Services
 * @version  $Revision$
 */
public class SequenceRuleTest extends AbstractRuleTest
{

  /** Test password. */
  private static final String VALID_PASS = "p4zRcv8#n65";

  /** Test password. */
  private static final String SEQ_PASS = "p4zxcvb#n65";

  /** Test password. */
  private static final String BACKWARDS_SEQ_PASS = "p4ytrew#n65";

  /** Test password. */
  private static final String UPPERCASE_SEQ_PASS = "p4RTyUI#n65";

  /** Test password. */
  private static final String BACKWARDS_UPPERCASE_SEQ_PASS = "p4][POi#n65";

  /** For testing. */
  private SequenceRule rule = new SequenceRule();

  /** For testing. */
  private SequenceRule backwardsRule = new SequenceRule();

  /** For testing. */
  private SequenceRule ignoreCaseRule = new SequenceRule();

  /** For testing. */
  private SequenceRule allRule = new SequenceRule();


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
        {this.rule, SEQ_PASS, false},
        {this.rule, BACKWARDS_SEQ_PASS, true},
        {this.rule, UPPERCASE_SEQ_PASS, true},
        {this.rule, BACKWARDS_UPPERCASE_SEQ_PASS, true},

        {this.backwardsRule, VALID_PASS, true},
        {this.backwardsRule, SEQ_PASS, false},
        {this.backwardsRule, BACKWARDS_SEQ_PASS, false},
        {this.backwardsRule, UPPERCASE_SEQ_PASS, true},
        {this.backwardsRule, BACKWARDS_UPPERCASE_SEQ_PASS, true},

        {this.ignoreCaseRule, VALID_PASS, true},
        {this.ignoreCaseRule, SEQ_PASS, false},
        {this.ignoreCaseRule, BACKWARDS_SEQ_PASS, true},
        {this.ignoreCaseRule, UPPERCASE_SEQ_PASS, false},
        {this.ignoreCaseRule, BACKWARDS_UPPERCASE_SEQ_PASS, true},

        {this.allRule, VALID_PASS, true},
        {this.allRule, SEQ_PASS, false},
        {this.allRule, BACKWARDS_SEQ_PASS, false},
        {this.allRule, UPPERCASE_SEQ_PASS, false},
        {this.allRule, BACKWARDS_UPPERCASE_SEQ_PASS, false},
      };
  }
}
