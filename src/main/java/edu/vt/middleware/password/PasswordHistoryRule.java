/*
  $Id$

  Copyright (C) 2003-2008 Virginia Tech.
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
import edu.vt.middleware.crypt.digest.DigestAlgorithm;
import edu.vt.middleware.crypt.util.Converter;

/**
 * <code>PasswordHistoryRule</code> contains methods for determining if a
 * password matching one of any previous password a user has choosen. If no
 * password history has been set or an empty history has been set, then
 * passwords will meet this rule.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */

public class PasswordHistoryRule extends AbstractPasswordRule
{

  /** password history. */
  private List<String> history = new ArrayList<String>();

  /** digest object to use if comparing hashed passwords. */
  private DigestAlgorithm digest;

  /** converter to use in conjunction with the digest. */
  private Converter converter;


  /**
   * This will add the supplied password to the list of history passwords. The
   * password must be a Base64 encoding if hashed passwords are being compared.
   *
   * @param  password  <code>String</code> to add to history
   */
  public void addHistory(final String password)
  {
    if (password != null) {
      this.history.add(password);
    }
  }


  /**
   * This will add the supplied passwords to the list of history passwords. The
   * passwords must be a Base64 encoding if hashed passwords are being compared.
   *
   * @param  passwords  <code>String[]</code> to add to history
   */
  public void addHistory(final String[] passwords)
  {
    if (passwords != null) {
      for (String s : passwords) {
        this.addHistory(s);
      }
    }
  }


  /**
   * This will add the supplied passwords to the list of history passwords. The
   * passwords must be a Base64 encoding if hashed passwords are being compared.
   *
   * @param  passwords  <code>List</code> to add to history
   */
  public void addHistory(final List<String> passwords)
  {
    if (passwords != null) {
      for (String s : passwords) {
        this.addHistory(s);
      }
    }
  }


  /**
   * This will set the supplied digest algorithm to be used when password
   * comparision are made. Useful if you need to compare password which are in a
   * hashed form. See {@link edu.vt.middleware.crypt.digest.DigestAlgorithm}.
   *
   * @param  algorithm  <code>String</code> to use for hashing
   * @param  converter  <code>Converter</code> to convert bytes to string
   */
  public void useDigest(final String algorithm, final Converter converter)
  {
    this.digest = DigestAlgorithm.newInstance(algorithm);
    this.converter = converter;
  }


  /** {@inheritDoc}. */
  public boolean verifyPassword(final Password password)
  {
    boolean success = false;

    if (password != null) {
      if (this.history.size() == 0) {
        success = true;
      } else {
        for (String p : this.history) {
          if (this.digest != null) {
            final String hash = this.digest.digest(
              password.getText().getBytes(),
              this.converter);
            if (p.equals(hash)) {
              success = false;
              this.setMessage(
                String.format(
                  "Password matches one of %s previous passwords",
                  this.history.size()));
              break;
            } else {
              success = true;
            }
          } else {
            if (p.equals(password.getText())) {
              success = false;
              this.setMessage(
                  String.format(
                    "Password matches one of %s previous passwords",
                    this.history.size()));
              break;
            } else {
              success = true;
            }
          }
        }
      }
    } else {
      this.setMessage("Password cannot be null");
    }
    return success;
  }
}
