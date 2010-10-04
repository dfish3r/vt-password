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
   * @param  pass  to check
   * @param  valid  whether the supplied password should pass the check
   *
   * @throws  Exception  On test failure.
   */
  @Test(
    groups = {"passtest"},
    dataProvider = "passwords"
  )
  public void checkPassword(
    final Rule rule, final String pass, final boolean valid)
    throws Exception
  {
    if (valid) {
      AssertJUnit.assertTrue(rule.verifyPassword(new Password(pass)).isValid());
    } else {
      AssertJUnit.assertFalse(
        rule.verifyPassword(new Password(pass)).isValid());
    }
  }
}
