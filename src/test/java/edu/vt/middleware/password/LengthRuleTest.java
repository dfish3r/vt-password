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

        {this.rule, null, MIN_VALID_PASS, true},
        {this.rule, null, MID_VALID_PASS, true},
        {this.rule, null, MAX_VALID_PASS, true},
        {this.rule, null, SHORT_PASS, false},
        {this.rule, null, LONG_PASS, false},

        {this.minRule, null, MIN_VALID_PASS, true},
        {this.minRule, null, MID_VALID_PASS, false},
        {this.minRule, null, MAX_VALID_PASS, false},
        {this.minRule, null, SHORT_PASS, false},
        {this.minRule, null, LONG_PASS, false},
      };
  }
}
