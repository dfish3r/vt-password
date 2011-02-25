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

import org.testng.annotations.DataProvider;

/**
 * Unit test for {@link LengthRule}.
 *
 * @author  Middleware Services
 * @version  $Revision$
 */
public class LengthRuleTest extends AbstractRuleTest
{

  /** Test password. */
  private static final Password MIN_VALID_PASS = new Password("p4T3#6Tu");

  /** Test password. */
  private static final Password MID_VALID_PASS = new Password("p4T3t#6Tu");

  /** Test password. */
  private static final Password MAX_VALID_PASS = new Password("p4T3to#6Tu");

  /** Test password. */
  private static final Password SHORT_PASS = new Password("p4T36");

  /** Test password. */
  private static final Password LONG_PASS = new Password("p4T3j76rE@#");

  /** For testing. */
  private LengthRule rule = new LengthRule(8, 10);

  /** For testing. */
  private LengthRule minRule = new LengthRule(8);


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

        {this.rule, new PasswordData(MIN_VALID_PASS), true, },
        {this.rule, new PasswordData(MID_VALID_PASS), true, },
        {this.rule, new PasswordData(MAX_VALID_PASS), true, },
        {this.rule, new PasswordData(SHORT_PASS), false, },
        {this.rule, new PasswordData(LONG_PASS), false, },

        {this.minRule, new PasswordData(MIN_VALID_PASS), true, },
        {this.minRule, new PasswordData(MID_VALID_PASS), false, },
        {this.minRule, new PasswordData(MAX_VALID_PASS), false, },
        {this.minRule, new PasswordData(SHORT_PASS), false, },
        {this.minRule, new PasswordData(LONG_PASS), false, },
      };
  }
}
