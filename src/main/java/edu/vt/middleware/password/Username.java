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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <code>Username</code> contains functions for determining data associated with
 * a username.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */
public class Username
{

  /** Stores the username. */
  private String username;

  /** password history. */
  private List<String> passwordHistory = new ArrayList<String>();

  /** password sources. */
  private Map<String, String> passwordSources = new HashMap<String, String>();


  /**
   * This will create a new <code>Username</code> with the supplied username
   * text.
   *
   * @param  text  <code>String</code> username
   * @throws  NullPointerException if the supplied text is null
   */
  public Username(final String text)
  {
    if (text == null) {
      throw new NullPointerException("Username cannot be null");
    }
    this.username = text;
  }


  /**
   * This returns the text of this <code>Username</code>.
   *
   * @return  <code>String</code> - username
   */
  public String getText()
  {
    return this.username;
  }


  /**
   * This returns the length of this <code>Username</code>.
   *
   * @return  <code>int</code> - username length
   */
  public int length()
  {
    return this.username.length();
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
   * @param  password  <code>String</code> to add
   * @throws  NullPointerException if source or password is null
   */
  public void addPasswordSource(final String source, final String password)
  {
    if (source == null) {
      throw new NullPointerException("Source cannot be null");
    }
    if (password == null) {
      throw new NullPointerException("Password cannot be null");
    }
    this.passwordSources.put(source, password);
  }


  /**
   * This returns a string representation of this object.
   *
   * @return  <code>String</code>
   */
  @Override
  public String toString()
  {
    return this.username;
  }
}
