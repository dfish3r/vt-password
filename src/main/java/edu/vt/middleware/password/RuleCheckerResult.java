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

import java.util.HashSet;
import java.util.Set;

/**
 * <code>PasswordDictionaryRuleResult</code> provides common implementation for
 * password dictionary rule result implementations.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */
public class RuleCheckerResult
  extends RuleResult<Set<RuleResult<?>>>
{


  /**
   * Default constructor.
   */
  public RuleCheckerResult()
  {
    this.details = new HashSet<RuleResult<?>>();
  }
}
