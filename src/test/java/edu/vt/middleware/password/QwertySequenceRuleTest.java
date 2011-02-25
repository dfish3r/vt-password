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

import org.testng.annotations.DataProvider;

/**
 * Unit test for {@link QwertySequenceRule}.
 *
 * @author  Middleware Services
 * @version  $Revision$
 */
public class QwertySequenceRuleTest extends AbstractRuleTest
{


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
        // Test valid password
        {
          new QwertySequenceRule(),
          new PasswordData(new Password("p4zRcv8#n65")),
          true,
        },
        // Has qwerty sequence
        {
          new QwertySequenceRule(6, false),
          new PasswordData(new Password("pqwerty#n65")),
          false,
        },
        // Has wrapping qwerty sequence with wrap=false
        {
          new QwertySequenceRule(),
          new PasswordData(new Password("pkl;'a#n65")),
          true,
        },
        // Has wrapping qwerty sequence with wrap=true
        {
          new QwertySequenceRule(8, true),
          new PasswordData(new Password("piop{}|qw#n65")),
          false,
        },
        // Has backward qwerty sequence
        {
          new QwertySequenceRule(4, false),
          new PasswordData(new Password("p7^54#n65")),
          false,
        },
        // Has backward wrapping qwerty sequence with wrap=false
        {
          new QwertySequenceRule(8, false),
          new PasswordData(new Password("phgfdsa\";#n65")),
          true,
        },
        // Has backward wrapping qwerty sequence with wrap=true
        {
          new QwertySequenceRule(6, true),
          new PasswordData(new Password("p@1`+_0#n65")),
          false,
        },
      };
  }
}
