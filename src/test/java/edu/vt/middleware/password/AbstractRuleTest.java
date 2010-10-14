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

import java.util.List;
import java.util.Map;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

/**
 * Base class for all rule tests.
 *
 * @author  Middleware Services
 * @version  $Revision$
 */
public abstract class AbstractRuleTest
{


  /**
   * @param  rule  to check password with
   * @param  passwordData  to check
   * @param  valid  whether the supplied rule data should pass the check
   *
   * @throws  Exception  On test failure.
   */
  @Test(
    groups = {"passtest"},
    dataProvider = "passwords"
  )
  public void checkPassword(
    final Rule rule,
    final PasswordData passwordData,
    final boolean valid)
    throws Exception
  {
    if (valid) {
      AssertJUnit.assertTrue(rule.validate(passwordData).isValid());
    } else {
      AssertJUnit.assertFalse(rule.validate(passwordData).isValid());
    }
  }


  /**
   * Creates a new <code>PasswordData</code> with the supplied input.
   *
   * @param  password  <code>Password</code>
   * @param username  <code>String</code>
   * @param history  <code>List</code>
   * @param sources  <code>Map</code>
   * @return  <code>PasswordData</code>
   */
  protected static PasswordData createPasswordData(
    final Password password,
    final String username,
    final List<String> history,
    final Map<String, String> sources)
  {
    final PasswordData pd = new PasswordData();
    if (password != null) {
      pd.setPassword(password);
    }
    if (username != null) {
      pd.setUsername(username);
    }
    if (history != null) {
      pd.setPasswordHistory(history);
    }
    if (sources != null) {
      pd.setPasswordSources(sources);
    }
    return pd;
  }
}
