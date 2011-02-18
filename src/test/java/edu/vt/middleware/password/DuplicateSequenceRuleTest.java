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
 * Unit test for {@link DuplicateSequenceRule}.
 *
 * @author  Middleware Services
 * @version  $Revision$
 */
public class DuplicateSequenceRuleTest extends AbstractRuleTest
{

  /** Test password. */
  private static final Password VALID_PASS = new Password("p4zRcv8#n65");

  /** Test password. */
  private static final Password SEQ_PASS = new Password("p4&&&&&#n65");

  /** Test password. */
  private static final Password LONG_SEQ_PASS = new Password("p4vvvvvvv#n65");

  /** For testing. */
  private DuplicateSequenceRule rule = new DuplicateSequenceRule(5);

  /** For testing. */
  private DuplicateSequenceRule longRule = new DuplicateSequenceRule(7);


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

        {this.longRule, new PasswordData(VALID_PASS), true, },
        {this.longRule, new PasswordData(SEQ_PASS), true, },
        {this.longRule, new PasswordData(LONG_SEQ_PASS), false, },
      };
  }
}
