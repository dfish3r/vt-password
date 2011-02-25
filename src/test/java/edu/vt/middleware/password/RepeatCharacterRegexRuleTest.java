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
 * Unit test for {@link RepeatCharacterRegexRule}.
 *
 * @author  Middleware Services
 * @version  $Revision$
 */
public class RepeatCharacterRegexRuleTest extends AbstractRuleTest
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
        // test valid password
        {
          new RepeatCharacterRegexRule(),
          new PasswordData(new Password("p4zRcv8#n65")),
          true,
        },
        // test repeating character
        {
          new RepeatCharacterRegexRule(),
          new PasswordData(new Password("p4&&&&&#n65")),
          false,
        },
        // test longer repeating character
        {
          new RepeatCharacterRegexRule(),
          new PasswordData(new Password("p4vvvvvvv#n65")),
          false,
        },

        // test valid password for long regex
        {
          new RepeatCharacterRegexRule(7),
          new PasswordData(new Password("p4zRcv8#n65")),
          true,
        },
        // test long regex with short repeat
        {
          new RepeatCharacterRegexRule(7),
          new PasswordData(new Password("p4&&&&&#n65")),
          true,
        },
        // test long regex with long repeat
        {
          new RepeatCharacterRegexRule(7),
          new PasswordData(new Password("p4vvvvvvv#n65")),
          false,
        },
      };
  }
}
