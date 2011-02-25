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
import java.util.List;

/**
 * <code>RuleResult</code> provides common implementation for password rule
 * result implementations.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */
public class RuleResult
{

  /** whether password rule was successful. */
  protected boolean valid;

  /** details associated with a password rule result. */
  protected List<RuleResultDetail> details = new ArrayList<RuleResultDetail>();


  /** Default constructor. */
  public RuleResult() {}


  /**
   * Creates a new <code>RuleResult</code> with the supplied validity.
   *
   * @param  b  result validity
   */
  public RuleResult(final boolean b)
  {
    this.setValid(b);
  }


  /**
   * Creates a new <code>RuleResult</code> with the supplied validity and
   * details.
   *
   * @param  b  result validity
   * @param  rrd  details associated with this result
   */
  public RuleResult(final boolean b, final RuleResultDetail rrd)
  {
    this.setValid(b);
    this.details.add(rrd);
  }


  /**
   * Returns whether the result of the rule verification is a valid password.
   *
   * @return  valid password for this rule
   */
  public boolean isValid()
  {
    return this.valid;
  }


  /**
   * Sets whether the result of the rule verification is a valid password.
   *
   * @param  b  valid password for this rule
   */
  public void setValid(final boolean b)
  {
    this.valid = b;
  }


  /**
   * Returns any details associated with the rule verification. May be null.
   *
   * @return  rule result details
   */
  public List<RuleResultDetail> getDetails()
  {
    return this.details;
  }


  /**
   * Sets any details associated with the rule verification.
   *
   * @param  rrd  rule result details
   */
  public void setDetails(final List<RuleResultDetail> rrd)
  {
    this.details = rrd;
  }
}
