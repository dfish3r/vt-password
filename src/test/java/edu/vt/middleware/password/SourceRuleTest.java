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
 * Unit test for {@link SourceRule}.
 *
 * @author  Middleware Services
 * @version  $Revision$
 */
public class SourceRuleTest extends AbstractRuleTest
{

  /** Test password. */
  private static final String VALID_PASS = "t3stUs3r01";

  /** Test password. */
  private static final String SOURCE_PASS = "t3stUs3r04";

  /** For testing. */
  private SourceRule rule = new SourceRule();

  /** For testing. */
  private SourceRule digestRule = new SourceRule();

  /** For testing. */
  private SourceRule emptyRule = new SourceRule();


  /** Initialize rules for this test. */
  @BeforeClass(groups = {"passtest"})
  public void createRules()
  {
    this.rule.addSource("System A", "t3stUs3r04");

    this.digestRule.useDigest("SHA-1", new Base64Converter());
    this.digestRule.addSource("System B", "CJGTDMQRP+rmHApkcijC80aDV0o=");
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

        {this.rule, VALID_PASS, true},
        {this.rule, SOURCE_PASS, false},

        {this.digestRule, VALID_PASS, true},
        {this.digestRule, SOURCE_PASS, false},

        {this.emptyRule, VALID_PASS, true},
        {this.emptyRule, SOURCE_PASS, true},
      };
  }
}
