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

import org.testng.annotations.DataProvider;

/**
 * Unit test for {@link QwertySequenceRule}.
 *
 * @author  Middleware Services
 * @version  $Revision$
 */
public class QwertySequenceRuleTest extends AbstractRuleTest
{

  /** Test password. */
  private static final Password VALID_PASS = new Password("p4zRcv8#n65");

  /** Test password. */
  private static final Password SEQ_PASS = new Password("p4zxcvb#n65");

  /** Test password. */
  private static final Password LONG_SEQ_PASS = new Password("p4dfghjkl#n65");

  /** Test password. */
  private static final Password BACKWARDS_SEQ_PASS = new Password(
    "p4ytrew#n65");

  /** Test password. */
  private static final Password UPPERCASE_SEQ_PASS = new Password(
    "p4RTyUI#n65");

  /** Test password. */
  private static final Password BACKWARDS_UPPERCASE_SEQ_PASS = new Password(
    "p4][POi#n65");

  /** Test password. */
  private static final Password CIRCULAR_SEQ_PASS = new Password("p4l;'as#n65");

  /** For testing. */
  private QwertySequenceRule rule = new QwertySequenceRule(
    5, false, false, false);

  /** For testing. */
  private QwertySequenceRule longRule = new QwertySequenceRule(
    7, false, false, false);

  /** For testing. */
  private QwertySequenceRule backwardsRule = new QwertySequenceRule(
    5, false, true, false);

  /** For testing. */
  private QwertySequenceRule ignoreCaseRule = new QwertySequenceRule(
    5, false, false, true);

  /** For testing. */
  private QwertySequenceRule circularRule = new QwertySequenceRule(
    5, true, false, false);

  /** For testing. */
  private QwertySequenceRule allRule = new QwertySequenceRule(
    5, true, true, true);


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
        {this.rule, new PasswordData(LONG_SEQ_PASS), false, },
        {this.rule, new PasswordData(BACKWARDS_SEQ_PASS), true, },
        {this.rule, new PasswordData(UPPERCASE_SEQ_PASS), true, },
        {this.rule, new PasswordData(BACKWARDS_UPPERCASE_SEQ_PASS), true, },
        {this.rule, new PasswordData(CIRCULAR_SEQ_PASS), true, },

        {this.longRule, new PasswordData(VALID_PASS), true, },
        {this.longRule, new PasswordData(SEQ_PASS), true, },
        {this.longRule, new PasswordData(LONG_SEQ_PASS), false, },
        {this.longRule, new PasswordData(BACKWARDS_SEQ_PASS), true, },
        {this.longRule, new PasswordData(UPPERCASE_SEQ_PASS), true, },
        {this.longRule, new PasswordData(BACKWARDS_UPPERCASE_SEQ_PASS), true, },
        {this.longRule, new PasswordData(CIRCULAR_SEQ_PASS), true, },

        {this.backwardsRule, new PasswordData(VALID_PASS), true, },
        {this.backwardsRule, new PasswordData(SEQ_PASS), false, },
        {this.backwardsRule, new PasswordData(LONG_SEQ_PASS), false, },
        {this.backwardsRule, new PasswordData(BACKWARDS_SEQ_PASS), false, },
        {this.backwardsRule, new PasswordData(UPPERCASE_SEQ_PASS), true, },
        {
          this.backwardsRule,
          new PasswordData(BACKWARDS_UPPERCASE_SEQ_PASS),
          true,
        },
        {this.backwardsRule, new PasswordData(CIRCULAR_SEQ_PASS), true, },

        {this.ignoreCaseRule, new PasswordData(VALID_PASS), true, },
        {this.ignoreCaseRule, new PasswordData(SEQ_PASS), false, },
        {this.ignoreCaseRule, new PasswordData(LONG_SEQ_PASS), false, },
        {this.ignoreCaseRule, new PasswordData(BACKWARDS_SEQ_PASS), true, },
        {this.ignoreCaseRule, new PasswordData(UPPERCASE_SEQ_PASS), false, },
        {
          this.ignoreCaseRule,
          new PasswordData(BACKWARDS_UPPERCASE_SEQ_PASS),
          true,
        },
        {this.ignoreCaseRule, new PasswordData(CIRCULAR_SEQ_PASS), true, },

        {this.circularRule, new PasswordData(VALID_PASS), true, },
        {this.circularRule, new PasswordData(SEQ_PASS), false, },
        {this.circularRule, new PasswordData(LONG_SEQ_PASS), false, },
        {this.circularRule, new PasswordData(BACKWARDS_SEQ_PASS), true, },
        {this.circularRule, new PasswordData(UPPERCASE_SEQ_PASS), true, },
        {
          this.circularRule,
          new PasswordData(BACKWARDS_UPPERCASE_SEQ_PASS),
          true,
        },
        {this.circularRule, new PasswordData(CIRCULAR_SEQ_PASS), false, },

        {this.allRule, new PasswordData(VALID_PASS), true, },
        {this.allRule, new PasswordData(SEQ_PASS), false, },
        {this.allRule, new PasswordData(LONG_SEQ_PASS), false, },
        {this.allRule, new PasswordData(BACKWARDS_SEQ_PASS), false, },
        {this.allRule, new PasswordData(UPPERCASE_SEQ_PASS), false, },
        {this.allRule, new PasswordData(BACKWARDS_UPPERCASE_SEQ_PASS), false, },
        {this.allRule, new PasswordData(CIRCULAR_SEQ_PASS), false, },
      };
  }
}
