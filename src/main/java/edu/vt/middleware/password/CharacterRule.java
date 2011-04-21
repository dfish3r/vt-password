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

/**
 * Interface for rules implementing character enforcement.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */
public interface CharacterRule extends Rule
{


  /**
   * Sets the number of characters to require in a password.
   *
   * @param  n  number of characters to require where n > 0
   */
  void setNumberOfCharacters(int n);


  /**
   * Returns the number of characters which must exist in order for a
   * password to meet the requirements of this rule.
   *
   * @return  number of characters to require
   */
  int getNumberOfCharacters();


  /**
   * Returns the characters that are considered valid for this rule.
   *
   * @return  valid characters
   */
  String getValidCharacters();
}
