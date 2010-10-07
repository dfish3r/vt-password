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
  private static final Password VALID_PASS = new Password("t3stUs3r01");

  /** Test password. */
  private static final Password SOURCE_PASS = new Password("t3stUs3r04");

  /** For testing. */
  private SourceRule rule = new SourceRule();

  /** For testing. */
  private SourceRule digestRule = new SourceRule();

  /** For testing. */
  private SourceRule emptyRule = new SourceRule();

  /** For testing. */
  private Username user = new Username("testuser");

  /** For testing. */
  private Username digestUser = new Username("testuser");

  /** For testing. */
  private Username emptyUser = new Username("testuser");


  /** Initialize rules for this test. */
  @BeforeClass(groups = {"passtest"})
  public void createRules()
  {
    this.user.addPasswordSource("System A", "t3stUs3r04");

    this.digestRule.setDigest("SHA-1", new Base64Converter());
    this.digestUser.addPasswordSource(
      "System B", "CJGTDMQRP+rmHApkcijC80aDV0o=");
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

        {this.rule, this.user, VALID_PASS, true},
        {this.rule, this.user, SOURCE_PASS, false},

        {this.digestRule, this.digestUser, VALID_PASS, true},
        {this.digestRule, this.digestUser, SOURCE_PASS, false},

        {this.emptyRule, this.emptyUser, VALID_PASS, true},
        {this.emptyRule, this.emptyUser, SOURCE_PASS, true},
      };
  }
}
