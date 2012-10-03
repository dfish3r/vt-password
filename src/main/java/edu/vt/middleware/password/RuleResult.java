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
 * Result of a password rule validation.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */
public class RuleResult
{

  /** Whether password rule was successful. */
  protected boolean valid;

  /** Details associated with a password rule result. */
  protected List<RuleResultDetail> details = new ArrayList<RuleResultDetail>();


  /** Default constructor. */
  public RuleResult() {}


  /**
   * Creates a new rule result.
   *
   * @param  b  result validity
   */
  public RuleResult(final boolean b)
  {
    setValid(b);
  }


  /**
   * Creates a new rule result.
   *
   * @param  b  result validity
   * @param  rrd  details associated with this result
   */
  public RuleResult(final boolean b, final RuleResultDetail rrd)
  {
    setValid(b);
    details.add(rrd);
  }


  /**
   * Returns whether the result of the rule verification is a valid password.
   *
   * @return  valid password for this rule
   */
  public boolean isValid()
  {
    return valid;
  }


  /**
   * Sets whether the result of the rule verification is a valid password.
   *
   * @param  b  valid password for this rule
   */
  public void setValid(final boolean b)
  {
    valid = b;
  }


  /**
   * Returns any details associated with the rule verification.
   *
   * @return  rule result details
   */
  public List<RuleResultDetail> getDetails()
  {
    return details;
  }


  /**
   * Sets any details associated with the rule verification.
   *
   * @param  rrd  rule result details
   */
  public void setDetails(final List<RuleResultDetail> rrd)
  {
    details = rrd;
  }


  /** {@inheritDoc} */
  @Override
  public String toString()
  {
    return
      String.format(
        "%s@%h::valid=%s,details=%s",
        getClass().getName(),
        hashCode(),
        valid,
        details);
  }
}
