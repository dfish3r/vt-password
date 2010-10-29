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
  private static final Password VALID_PASS = new Password("p4zRcv8#n65");

  /** Test password. */
  private static final Password SEQ_PASS = new Password("p4zxcvb#n65");

  /** Test password. */
  private static final Password BACKWARDS_SEQ_PASS = new Password(
    "p4ytrew#n65");

  /** Test password. */
  private static final Password UPPERCASE_SEQ_PASS = new Password(
    "p4RTyUI#n65");

  /** Test password. */
  private static final Password BACKWARDS_UPPERCASE_SEQ_PASS = new Password(
    "p4][POi#n65");

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

        {this.rule, new PasswordData(VALID_PASS), true, },
        {this.rule, new PasswordData(SEQ_PASS), false, },
        {this.rule, new PasswordData(BACKWARDS_SEQ_PASS), true, },
        {this.rule, new PasswordData(UPPERCASE_SEQ_PASS), true, },
        {this.rule, new PasswordData(BACKWARDS_UPPERCASE_SEQ_PASS), true, },

        {this.backwardsRule, new PasswordData(VALID_PASS), true, },
        {this.backwardsRule, new PasswordData(SEQ_PASS), false, },
        {this.backwardsRule, new PasswordData(BACKWARDS_SEQ_PASS), false, },
        {this.backwardsRule, new PasswordData(UPPERCASE_SEQ_PASS), true, },
        {
          this.backwardsRule,
          new PasswordData(BACKWARDS_UPPERCASE_SEQ_PASS),
          true,
        },

        {this.ignoreCaseRule, new PasswordData(VALID_PASS), true, },
        {this.ignoreCaseRule, new PasswordData(SEQ_PASS), false, },
        {this.ignoreCaseRule, new PasswordData(BACKWARDS_SEQ_PASS), true, },
        {this.ignoreCaseRule, new PasswordData(UPPERCASE_SEQ_PASS), false, },
        {
          this.ignoreCaseRule,
          new PasswordData(BACKWARDS_UPPERCASE_SEQ_PASS),
          true,
        },

        {this.allRule, new PasswordData(VALID_PASS), true, },
        {this.allRule, new PasswordData(SEQ_PASS), false, },
        {this.allRule, new PasswordData(BACKWARDS_SEQ_PASS), false, },
        {this.allRule, new PasswordData(UPPERCASE_SEQ_PASS), false, },
        {this.allRule, new PasswordData(BACKWARDS_UPPERCASE_SEQ_PASS), false, },
      };
  }
}
