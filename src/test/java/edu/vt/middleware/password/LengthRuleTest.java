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
  private static final String MIN_VALID_PASS = "p4T3#6Tu";

  /** Test password. */
  private static final String MID_VALID_PASS = "p4T3t#6Tu";

  /** Test password. */
  private static final String MAX_VALID_PASS = "p4T3to#6Tu";

  /** Test password. */
  private static final String SHORT_PASS = "p4T36";

  /** Test password. */
  private static final String LONG_PASS = "p4T3j76rE@#";

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

        {this.rule, MIN_VALID_PASS, true},
        {this.rule, MID_VALID_PASS, true},
        {this.rule, MAX_VALID_PASS, true},
        {this.rule, SHORT_PASS, false},
        {this.rule, LONG_PASS, false},

        {this.minRule, MIN_VALID_PASS, true},
        {this.minRule, MID_VALID_PASS, false},
        {this.minRule, MAX_VALID_PASS, false},
        {this.minRule, SHORT_PASS, false},
        {this.minRule, LONG_PASS, false},
      };
  }
}
