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

import java.util.ArrayList;
import java.util.List;
import edu.vt.middleware.crypt.util.Base64Converter;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

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

  /** Test username. */
  private static final String USER = "testuser";

  /** For testing. */
  private List<String> history = new ArrayList<String>();

  /** For testing. */
  private List<String> digestHistory = new ArrayList<String>();

  /** For testing. */
  private HistoryRule rule = new HistoryRule();

  /** For testing. */
  private HistoryRule digestRule = new HistoryRule();

  /** For testing. */
  private HistoryRule emptyRule = new HistoryRule();


  /** Initialize rules for this test. */
  @BeforeClass(groups = {"passtest"})
  public void createRules()
  {
    this.history.add(HISTORY_PASS1.getText());
    this.history.add(HISTORY_PASS2.getText());
    this.history.add(HISTORY_PASS3.getText());

    this.digestRule.setDigest("SHA-1", new Base64Converter());
    this.digestHistory.add("safx/LW8+SsSy/o3PmCNy4VEm5s=");
    this.digestHistory.add("zurb9DyQ5nooY1la8h86Bh0n1iw=");
    this.digestHistory.add("bhqabXwE3S8E6xNJfX/d76MFOCs=");
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

        {
          this.rule,
          PasswordData.newInstance(VALID_PASS, USER, this.history, null),
          true,
        },
        {
          this.rule,
          PasswordData.newInstance(HISTORY_PASS1, USER, this.history, null),
          false,
        },
        {
          this.rule,
          PasswordData.newInstance(HISTORY_PASS2, USER, this.history, null),
          false,
        },
        {
          this.rule,
          PasswordData.newInstance(HISTORY_PASS3, USER, this.history, null),
          false,
        },

        {
          this.digestRule,
          PasswordData.newInstance(
            VALID_PASS, USER, this.digestHistory, null),
          true,
        },
        {
          this.digestRule,
          PasswordData.newInstance(
            HISTORY_PASS1, USER, this.digestHistory, null),
          false,
        },
        {
          this.digestRule,
          PasswordData.newInstance(
            HISTORY_PASS2, USER, this.digestHistory, null),
          false,
        },
        {
          this.digestRule,
          PasswordData.newInstance(
            HISTORY_PASS3, USER, this.digestHistory, null),
          false,
        },

        {
          this.emptyRule,
          PasswordData.newInstance(VALID_PASS, USER, null, null),
          true,
        },
        {
          this.emptyRule,
          PasswordData.newInstance(HISTORY_PASS1, USER, null, null),
          true,
        },
        {
          this.emptyRule,
          PasswordData.newInstance(HISTORY_PASS2, USER, null, null),
          true,
        },
        {
          this.emptyRule,
          PasswordData.newInstance(HISTORY_PASS3, USER, null, null),
          true,
        },
      };
  }


  /**
   * @throws  Exception  On test failure.
   */
  @Test(groups = {"passtest"})
  public void resolveMessage()
    throws Exception
  {
    final RuleResult result = this.rule.validate(
      PasswordData.newInstance(HISTORY_PASS1, USER, this.history, null));
    for (RuleResultDetail detail : result.getDetails()) {
      AssertJUnit.assertEquals(
        String.format(
          "Password matches one of %s previous passwords.",
          this.history.size()),
        DEFAULT_RESOLVER.resolve(detail));
    }
  }
}
