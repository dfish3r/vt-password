/*
  $Id$

  Copyright (C) 2003-2013 Virginia Tech.
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
  private final List<String> history = new ArrayList<String>();

  /** For testing. */
  private final List<String> digestHistory = new ArrayList<String>();

  /** For testing. */
  private final List<String> saltedDigestHistory = new ArrayList<String>();

  /** For testing. */
  private final HistoryRule rule = new HistoryRule();

  /** For testing. */
  private final HistoryRule digestRule = new HistoryRule();

  /** For testing. */
  private final HistoryRule saltedDigestRule = new HistoryRule();

  /** For testing. */
  private final HistoryRule emptyRule = new HistoryRule();


  /** Initialize rules for this test. */
  @BeforeClass(groups = {"passtest"})
  public void createRules()
  {
    history.add(HISTORY_PASS1.getText());
    history.add(HISTORY_PASS2.getText());
    history.add(HISTORY_PASS3.getText());

    digestRule.setDigest("SHA-1", new Base64Converter());
    digestHistory.add("safx/LW8+SsSy/o3PmCNy4VEm5s=");
    digestHistory.add("zurb9DyQ5nooY1la8h86Bh0n1iw=");
    digestHistory.add("bhqabXwE3S8E6xNJfX/d76MFOCs=");

    saltedDigestRule.setDigest("SHA-1", new Base64Converter());
    saltedDigestHistory.add("2DSZvOzGiMnm/Mbxt1M3zNAh7P1GebLG");
    saltedDigestHistory.add("rv1mF2DuarrF//LPP9+AFJal8bMc9G5z");
    saltedDigestHistory.add("3lABdWxtWhfGKtXBx4MfiWZ1737KnFuG");
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
          rule,
          PasswordData.newInstance(VALID_PASS, USER, history, null),
          null,
        },
        {
          rule,
          PasswordData.newInstance(HISTORY_PASS1, USER, history, null),
          codes(HistoryRule.ERROR_CODE),
        },
        {
          rule,
          PasswordData.newInstance(HISTORY_PASS2, USER, history, null),
          codes(HistoryRule.ERROR_CODE),
        },
        {
          rule,
          PasswordData.newInstance(HISTORY_PASS3, USER, history, null),
          codes(HistoryRule.ERROR_CODE),
        },

        {
          digestRule,
          PasswordData.newInstance(VALID_PASS, USER, digestHistory, null),
          null,
        },
        {
          digestRule,
          PasswordData.newInstance(HISTORY_PASS1, USER, digestHistory, null),
          codes(HistoryRule.ERROR_CODE),
        },
        {
          digestRule,
          PasswordData.newInstance(HISTORY_PASS2, USER, digestHistory, null),
          codes(HistoryRule.ERROR_CODE),
        },
        {
          digestRule,
          PasswordData.newInstance(HISTORY_PASS3, USER, digestHistory, null),
          codes(HistoryRule.ERROR_CODE),
        },

        {
          saltedDigestRule,
          PasswordData.newInstance(VALID_PASS, USER, saltedDigestHistory, null),
          null,
        },
        {
          saltedDigestRule,
          PasswordData.newInstance(
            HISTORY_PASS1, USER, saltedDigestHistory, null),
          codes(HistoryRule.ERROR_CODE),
        },
        {
          saltedDigestRule,
          PasswordData.newInstance(
            HISTORY_PASS2, USER, saltedDigestHistory, null),
          codes(HistoryRule.ERROR_CODE),
        },
        {
          saltedDigestRule,
          PasswordData.newInstance(
            HISTORY_PASS3, USER, saltedDigestHistory, null),
          codes(HistoryRule.ERROR_CODE),
        },

        {
          emptyRule,
          PasswordData.newInstance(VALID_PASS, USER, null, null),
          null,
        },
        {
          emptyRule,
          PasswordData.newInstance(HISTORY_PASS1, USER, null, null),
          null,
        },
        {
          emptyRule,
          PasswordData.newInstance(HISTORY_PASS2, USER, null, null),
          null,
        },
        {
          emptyRule,
          PasswordData.newInstance(HISTORY_PASS3, USER, null, null),
          null,
        },
      };
  }


  /** @throws  Exception  On test failure. */
  @Test(groups = {"passtest"})
  public void resolveMessage()
    throws Exception
  {
    final RuleResult result = rule.validate(
      PasswordData.newInstance(HISTORY_PASS1, USER, history, null));
    for (RuleResultDetail detail : result.getDetails()) {
      AssertJUnit.assertEquals(
        String.format(
          "Password matches one of %s previous passwords.",
          history.size()),
        DEFAULT_RESOLVER.resolve(detail));
      AssertJUnit.assertNotNull(EMPTY_RESOLVER.resolve(detail));
    }
  }
}
