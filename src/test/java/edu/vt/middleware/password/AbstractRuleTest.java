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

  /** default message resolver.*/
  protected static final MessageResolver DEFAULT_RESOLVER =
    new MessageResolver();


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
      final RuleResult result = rule.validate(passwordData);
      AssertJUnit.assertTrue(result.isValid());
      AssertJUnit.assertTrue(result.getDetails().isEmpty());
    } else {
      final RuleResult result = rule.validate(passwordData);
      AssertJUnit.assertFalse(result.isValid());
      AssertJUnit.assertTrue(!result.getDetails().isEmpty());
    }
  }
}
