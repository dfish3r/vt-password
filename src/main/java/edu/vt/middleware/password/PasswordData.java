/*
  $Id: Username.java 1643 2010-10-07 17:14:08Z dfisher $

  Copyright (C) 2003-2010 Virginia Tech.
  All rights reserved.

  SEE LICENSE FOR MORE INFORMATION

  Author:  Middleware Services
  Email:   middleware@vt.edu
  Version: $Revision: 1643 $
  Updated: $Date: 2010-10-07 13:14:08 -0400 (Thu, 07 Oct 2010) $
*/
package edu.vt.middleware.password;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <code>PasswordData</code> contains the information used by rules to perform
 * password validation.
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

  /** password history. */
  private List<String> passwordHistory = new ArrayList<String>();

  /** password sources. */
  private Map<String, String> passwordSources = new HashMap<String, String>();


  /**
   * This will create a new <code>PasswordData</code>.
   */
  public PasswordData() {}


  /**
   * This will create a new <code>PasswordData</code> with the supplied
   * password.
   *
   * @param  p  <code>Password</code>
   */
  public PasswordData(final Password p)
  {
    this.setPassword(p);
  }


  /**
   * Set the password.
   *
   * @param  p  <code>Password</code>
   */
  public void setPassword(final Password p)
  {
    if (p == null) {
      throw new NullPointerException("Password cannot be null");
    }
    this.password = p;
  }


  /**
   * Get the password.
   *
   * @return  <code>Password</code>
   */
  public Password getPassword()
  {
    return this.password;
  }


  /**
   * Set the username.
   *
   * @param  s  <code>String</code>
   */
  public void setUsername(final String s)
  {
    if (s == null) {
      throw new NullPointerException("Username cannot be null");
    }
    this.username = s;
  }


  /**
   * Get the username.
   *
   * @return  <code>String</code>
   */
  public String getUsername()
  {
    return this.username;
  }


  /**
   * This will return the password history.
   *
   * @return  <code>List</code> of password history
   */
  public List<String> getPasswordHistory()
  {
    return this.passwordHistory;
  }


  /**
   * This will set the password history.
   *
   * @param  l  <code>List</code> of password history
   */
  public void setPasswordHistory(final List<String> l)
  {
    this.passwordHistory = l;
  }


  /**
   * This will return the password sources.
   *
   * @return  <code>Map</code> of password sources
   */
  public Map<String, String> getPasswordSources()
  {
    return this.passwordSources;
  }


  /**
   * This will set the password sources.
   *
   * @param  m  <code>Map</code> of password sources
   */
  public void setPasswordSources(final Map<String, String> m)
  {
    this.passwordSources = m;
  }


  /**
   * This will add the supplied password as a password source.
   *
   * @param  source  <code>String</code> label
   * @param  pass  <code>String</code> to add
   * @throws  NullPointerException if source or password is null
   */
  public void addPasswordSource(final String source, final String pass)
  {
    if (source == null) {
      throw new NullPointerException("Source cannot be null");
    }
    if (pass == null) {
      throw new NullPointerException("Password cannot be null");
    }
    this.passwordSources.put(source, pass);
  }


  /**
   * This returns a string representation of this object.
   *
   * @return  <code>String</code>
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
