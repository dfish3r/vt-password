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

import java.util.UUID;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Reports the time it takes to execute many password validations using sequence
 * rules.
 *
 * @author  Middleware Services
 * @version  $Revision$
 */
public class SequenceRulesPerfTest
{


  /**
   * Gets performance test data.
   *
   * @return  Array of test parameters including rules to test and number of
   * iterations for which rule should be evaluated on a random password.
   */
  @DataProvider(name = "perf-data")
  public Object[][] perfData()
  {
    return
      new Object[][] {
        new Object[] {
          new Rule[] {
            new AlphabeticalSequenceRule(),
            new NumericalSequenceRule(),
            new QwertySequenceRule(),
          },
          5000,
        },
      };
  }


  /**
   * Executes the performance test on the given rules.
   *
   * @param  rules  Password validation rules to test.
   * @param  iterations  Number of iterations of each test.
   */
  @Test(
    groups = {"seqperftest"},
    dataProvider = "perf-data",
    timeOut = 60000
  )
  public void execute(final Rule[] rules, final int iterations)
  {
    final PasswordData[] passwords = new PasswordData[iterations];
    for (int i = 0; i < iterations; i++) {
      passwords[i] = new PasswordData();
      passwords[i].setPassword(new Password(UUID.randomUUID().toString()));
    }

    final long t = System.currentTimeMillis();
    for (PasswordData password : passwords) {
      for (Rule rule : rules) {
        rule.validate(password);
      }
    }
    System.out.println(
      String.format(
        "%s:: execution completed in %s ms",
        this.getClass().getName(),
        System.currentTimeMillis() - t));
  }
}
