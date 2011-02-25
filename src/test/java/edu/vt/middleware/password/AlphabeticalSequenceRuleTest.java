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
 * Unit test for {@link AlphabeticalSequenceRule}.
 *
 * @author  Middleware Services
 * @version  $Revision$
 */
public class AlphabeticalSequenceRuleTest extends AbstractRuleTest
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
          new AlphabeticalSequenceRule(),
          new PasswordData(new Password("p4zRcv8#n65")),
          true,
        },
        // Has alphabetical sequence
        {
          new AlphabeticalSequenceRule(7, false),
          new PasswordData(new Password("phijklmn#n65")),
          false,
        },
        // Has wrapping alphabetical sequence with wrap=false
        {
          new AlphabeticalSequenceRule(4, false),
          new PasswordData(new Password("pXyza#n65")),
          true,
        },
        // Has wrapping alphabetical sequence with wrap=true
        {
          new AlphabeticalSequenceRule(4, true),
          new PasswordData(new Password("pxyzA#n65")),
          false,
        },
        // Has backward alphabetical sequence
        {
          new AlphabeticalSequenceRule(),
          new PasswordData(new Password("ptSrqp#n65")),
          false,
        },
        // Has backward wrapping alphabetical sequence with wrap=false
        {
          new AlphabeticalSequenceRule(8, false),
          new PasswordData(new Password("pcBazyXwv#n65")),
          true,
        },
        // Has backward wrapping alphabetical sequence with wrap=true
        {
          new AlphabeticalSequenceRule(8, true),
          new PasswordData(new Password("pcbazyxwv#n65")),
          false,
        },
      };
  }
}
