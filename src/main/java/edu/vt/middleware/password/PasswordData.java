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
  private Username username;


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
   * This will create a new <code>PasswordData</code> with the supplied uesrname
   * and password.
   *
   * @param  u  <code>Username</code>
   * @param  p  <code>Password</code>
   */
  public PasswordData(final Username u, final Password p)
  {
    this.setUsername(u);
    this.setPassword(p);
  }


  /**
   * Set the password.
   *
   * @param  p  <code>Password</code>
   */
  public void setPassword(final Password p)
  {
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
   * @param  u  <code>Username</code>
   */
  public void setUsername(final Username u)
  {
    this.username = u;
  }


  /**
   * Get the username.
   *
   * @return  <code>Username</code>
   */
  public Username getUsername()
  {
    return this.username;
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
      "%s@%h::username=%s,password=%s",
      this.getClass().getName(),
      this.hashCode(),
      this.username,
      this.password);
  }
}
