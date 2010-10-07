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
 * Unit test for {@link WhitespaceRule}.
 *
 * @author  Middleware Services
 * @version  $Revision$
 */
public class WhitespaceRuleTest extends AbstractRuleTest
{

  /** Test password. */
  private static final String VALID_PASS = "AycDPdsyz";

  /** Test password. */
  private static final String SPACE_PASS = "AycD" + " " + "Pdsyz";

  /** Test password. */
  private static final String TAB_PASS = "Ayc" + "\t" + "DPdsyz";

  /** Test password. */
  private static final String LINE_SEP_PASS =
    "AycDPs" + System.getProperty("line.separator") + "yz";

  /** For testing. */
  private WhitespaceRule rule = new WhitespaceRule();


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

        {this.rule, null, new Password(VALID_PASS), true},
        {this.rule, null, new Password(SPACE_PASS), false},
        {this.rule, null, new Password(TAB_PASS), false},
        {this.rule, null, new Password(LINE_SEP_PASS), false},
      };
  }
}
