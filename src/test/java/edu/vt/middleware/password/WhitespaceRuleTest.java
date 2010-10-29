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
  private static final Password VALID_PASS = new Password("AycDPdsyz");

  /** Test password. */
  private static final Password SPACE_PASS = new Password(
    "AycD" + " " + "Pdsyz");

  /** Test password. */
  private static final Password TAB_PASS = new Password(
    "Ayc" + "\t" + "DPdsyz");

  /** Test password. */
  private static final Password LINE_SEP_PASS = new Password(
    "AycDPs" + System.getProperty("line.separator") + "yz");

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

        {this.rule, new PasswordData(VALID_PASS), true, },
        {this.rule, new PasswordData(SPACE_PASS), false, },
        {this.rule, new PasswordData(TAB_PASS), false, },
        {this.rule, new PasswordData(LINE_SEP_PASS), false, },
      };
  }
}
