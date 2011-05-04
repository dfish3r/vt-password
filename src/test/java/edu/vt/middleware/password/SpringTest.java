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

import java.util.ArrayList;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

/**
 * Unit test for Spring integration.
 *
 * @author  Middleware Services
 * @version  $Revision$
 */
public class SpringTest
{


  /**
   * Attempts to load all Spring application context XML files to verify proper
   * wiring.
   *
   * @throws  Exception  On test failure.
   */
  @Test(groups = {"passtest"})
  public void testSpringWiring()
    throws Exception
  {
    final ClassPathXmlApplicationContext context =
      new ClassPathXmlApplicationContext(
        new String[] {"/spring-context.xml", });
    AssertJUnit.assertTrue(context.getBeanDefinitionCount() > 0);

    final PasswordValidator validator = new PasswordValidator(
      new ArrayList<Rule>(context.getBeansOfType(Rule.class).values()));
    final PasswordData pd = new PasswordData(new Password("springtest"));
    pd.setUsername("springuser");
    final RuleResult result = validator.validate(pd);
    AssertJUnit.assertNotNull(result);
  }
}
