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

import java.util.HashMap;
import java.util.Map;
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

  /** Test username. */
  private static final String USER = "testuser";

  /** For testing. */
  private Map<String, String> sources = new HashMap<String, String>();

  /** For testing. */
  private Map<String, String> digestSources = new HashMap<String, String>();

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
    this.sources.put("System A", "t3stUs3r04");

    this.digestRule.setDigest("SHA-1", new Base64Converter());
    this.digestSources.put("System B", "CJGTDMQRP+rmHApkcijC80aDV0o=");
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
          this.rule,
          createPasswordData(VALID_PASS, USER, null, this.sources),
          true,
        },
        {
          this.rule,
          createPasswordData(SOURCE_PASS, USER, null, this.sources),
          false,
        },

        {
          this.digestRule,
          createPasswordData(VALID_PASS, USER, null, this.digestSources),
          true,
        },
        {
          this.digestRule,
          createPasswordData(SOURCE_PASS, USER, null, this.digestSources),
          false,
        },

        {
          this.emptyRule,
          createPasswordData(VALID_PASS, USER, null, null),
          true,
        },
        {
          this.emptyRule,
          createPasswordData(SOURCE_PASS, USER, null, null),
          true,
        },
      };
  }
}
