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

import edu.vt.middleware.crypt.util.Base64Converter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

/**
 * Unit test for {@link HistoryRule}.
 *
 * @author  Middleware Services
 * @version  $Revision$
 */
public class HistoryRuleTest extends AbstractRuleTest
{

  /** Test password. */
  private static final Password VALID_PASS = new Password("t3stUs3r00");

  /** Test password. */
  private static final Password HISTORY_PASS1 = new Password("t3stUs3r01");

  /** Test password. */
  private static final Password HISTORY_PASS2 = new Password("t3stUs3r02");

  /** Test password. */
  private static final Password HISTORY_PASS3 = new Password("t3stUs3r03");

  /** For testing. */
  private HistoryRule rule = new HistoryRule();

  /** For testing. */
  private HistoryRule digestRule = new HistoryRule();

  /** For testing. */
  private HistoryRule emptyRule = new HistoryRule();

  /** For testing. */
  private Username user = new Username("testuser");

  /** For testing. */
  private Username digestUser = new Username("testuser");

  /** For testing. */
  private Username emptyUser = new Username("testuser");


  /** Initialize rules for this test. */
  @BeforeClass(groups = {"passtest"})
  public void createRules()
  {
    this.user.getPasswordHistory().add(HISTORY_PASS1.getText());
    this.user.getPasswordHistory().add(HISTORY_PASS2.getText());
    this.user.getPasswordHistory().add(HISTORY_PASS3.getText());

    this.digestRule.setDigest("SHA-1", new Base64Converter());
    this.digestUser.getPasswordHistory().add("safx/LW8+SsSy/o3PmCNy4VEm5s=");
    this.digestUser.getPasswordHistory().add("zurb9DyQ5nooY1la8h86Bh0n1iw=");
    this.digestUser.getPasswordHistory().add("bhqabXwE3S8E6xNJfX/d76MFOCs=");
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

        {this.rule, new PasswordData(this.user, VALID_PASS), true, },
        {this.rule, new PasswordData(this.user, HISTORY_PASS1), false, },
        {this.rule, new PasswordData(this.user, HISTORY_PASS2), false, },
        {this.rule, new PasswordData(this.user, HISTORY_PASS3), false, },

        {
          this.digestRule,
          new PasswordData(this.digestUser, VALID_PASS),
          true,
        },
        {
          this.digestRule,
          new PasswordData(this.digestUser, HISTORY_PASS1),
          false,
        },
        {
          this.digestRule,
          new PasswordData(this.digestUser, HISTORY_PASS2),
          false,
        },
        {
          this.digestRule,
          new PasswordData(this.digestUser, HISTORY_PASS3),
          false,
        },

        {this.emptyRule, new PasswordData(this.emptyUser, VALID_PASS), true, },
        {
          this.emptyRule,
          new PasswordData(this.emptyUser, HISTORY_PASS1),
          true,
        },
        {
          this.emptyRule,
          new PasswordData(this.emptyUser, HISTORY_PASS2),
          true,
        },
        {
          this.emptyRule,
          new PasswordData(this.emptyUser, HISTORY_PASS3),
          true,
        },
      };
  }
}
