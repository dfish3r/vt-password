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
 * Unit test for {@link AlphabeticalSequenceRule}.
 *
 * @author  Middleware Services
 * @version  $Revision$
 */
public class AlphabeticalSequenceRuleTest extends AbstractRuleTest
{

  /** Test password. */
  private static final Password VALID_PASS = new Password("p4zRcv8#n65");

  /** Test password. */
  private static final Password SEQ_PASS = new Password("p4hijkl#n65");

  /** Test password. */
  private static final Password LONG_SEQ_PASS = new Password("p4lmnopqr#n65");

  /** Test password. */
  private static final Password BACKWARDS_SEQ_PASS = new Password(
    "p4qponm#n65");

  /** Test password. */
  private static final Password UPPERCASE_SEQ_PASS = new Password(
    "p4DefGH#n65");

  /** Test password. */
  private static final Password BACKWARDS_UPPERCASE_SEQ_PASS = new Password(
    "p4ONmLk#n65");

  /** Test password. */
  private static final Password CIRCULAR_SEQ_PASS = new Password("p4xyzab#n65");

  /** For testing. */
  private AlphabeticalSequenceRule rule = new AlphabeticalSequenceRule(
    5, false, false, false);

  /** For testing. */
  private AlphabeticalSequenceRule longRule = new AlphabeticalSequenceRule(
    7, false, false, false);

  /** For testing. */
  private AlphabeticalSequenceRule backwardsRule = new AlphabeticalSequenceRule(
    5, false, true, false);

  /** For testing. */
  private AlphabeticalSequenceRule ignoreCaseRule =
    new AlphabeticalSequenceRule(5, false, false, true);

  /** For testing. */
  private AlphabeticalSequenceRule circularRule = new AlphabeticalSequenceRule(
    5, true, false, false);

  /** For testing. */
  private AlphabeticalSequenceRule allRule = new AlphabeticalSequenceRule(
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
