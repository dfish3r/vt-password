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

/**
 * <code>CharacterRule</code> is a marker interface of rules implementing
 * character enforcement.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */
public interface CharacterRule extends Rule
{


  /**
   * This sets the number of characters to require in a password.
   *
   * @param  n  <code>int</code> number of characters to require where n > 0
   */
  void setNumberOfCharacters(int n);


  /**
   * This returns the number of characters which must exist in order
   * for a password to meet the requirements of this rule.
   *
   * @return  <code>int</code> number of characters to require
   */
  int getNumberOfCharacters();


  /**
   * Returns the characters that are considered valid for this rule.
   *
   * @return  <code>char[]</code>
   */
  String getValidCharacters();
}
