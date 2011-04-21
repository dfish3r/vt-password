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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contains password related information used by rules to perform password
 * validation.
 *
 * @author  Middleware Services
 * @version  $Revision: 1643 $ $Date: 2010-10-07 13:14:08 -0400 (Thu, 07 Oct 2010) $
 */
public class PasswordData
{

  /** Stores the password. */
  private Password password;

  /** Stores the username. */
  private String username;

  /** Password history. */
  private List<String> passwordHistory = new ArrayList<String>();

  /** Password sources. */
  private Map<String, String> passwordSources = new HashMap<String, String>();


  /** Default constructor. */
  public PasswordData() {}


  /**
   * Creates a new password data.
   *
   * @param  p  password
   */
  public PasswordData(final Password p)
  {
    this.setPassword(p);
  }


  /**
   * Sets the password.
   *
   * @param  p  password
   */
  public void setPassword(final Password p)
  {
    if (p == null) {
      throw new NullPointerException("Password cannot be null");
    }
    this.password = p;
  }


  /**
   * Returns the password.
   *
   * @return  password
   */
  public Password getPassword()
  {
    return this.password;
  }


  /**
   * Sets the username.
   *
   * @param  s  username
   */
  public void setUsername(final String s)
  {
    if (s == null) {
      throw new NullPointerException("Username cannot be null");
    }
    this.username = s;
  }


  /**
   * Returns the username.
   *
   * @return  username
   */
  public String getUsername()
  {
    return this.username;
  }


  /**
   * Returns the password history.
   *
   * @return  password history
   */
  public List<String> getPasswordHistory()
  {
    return this.passwordHistory;
  }


  /**
   * Sets the password history.
   *
   * @param  l  password history
   */
  public void setPasswordHistory(final List<String> l)
  {
    this.passwordHistory = l;
  }


  /**
   * Returns the password sources.
   *
   * @return  password sources
   */
  public Map<String, String> getPasswordSources()
  {
    return this.passwordSources;
  }


  /**
   * Sets the password sources.
   *
   * @param  m  password sources
   */
  public void setPasswordSources(final Map<String, String> m)
  {
    this.passwordSources = m;
  }


  /**
   * Convenience method for creating a password data with all of it's
   * properties. Properties are ignored if they are null or if collections are
   * empty.
   *
   * @param  p  password
   * @param  u  username
   * @param  h  history
   * @param  s  sources
   * @return  password data
   */
  public static PasswordData newInstance(
    final Password p,
    final String u,
    final List<String> h,
    final Map<String, String> s)
  {
    final PasswordData pd = new PasswordData();
    if (p != null) {
      pd.setPassword(p);
    }
    if (u != null) {
      pd.setUsername(u);
    }
    if (h != null && !h.isEmpty()) {
      pd.setPasswordHistory(h);
    }
    if (s != null && !s.isEmpty()) {
      pd.setPasswordSources(s);
    }
    return pd;
  }


  /**
   * Returns a string representation of this object.
   *
   * @return  string representation
   */
  @Override
  public String toString()
  {
    return
      String.format(
        "%s@%h::username=%s,password=%s,passwordHistory=%s,passwordSources=%s",
        this.getClass().getName(),
        this.hashCode(),
        this.username,
        this.password,
        this.passwordHistory,
        this.passwordSources);
  }
}
