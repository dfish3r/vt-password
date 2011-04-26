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

import edu.vt.middleware.crypt.digest.DigestAlgorithm;
import edu.vt.middleware.crypt.util.Converter;

/**
 * Provides common implementation for password rules that use a digest.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */
public abstract class AbstractDigester
{

  /** Digest object to use if comparing hashed passwords. */
  protected DigestAlgorithm digest;

  /** Converter to use in conjunction with the digest. */
  protected Converter converter;


  /**
   * This will set the supplied digest algorithm to be used when password
   * comparison are made. Useful if you need to compare password which are in a
   * hashed form. See {@link edu.vt.middleware.crypt.digest.DigestAlgorithm}.
   *
   * @param  algorithm  to use for hashing
   * @param  conv  to convert bytes to string
   */
  public void setDigest(final String algorithm, final Converter conv)
  {
    digest = DigestAlgorithm.newInstance(algorithm);
    converter = conv;
  }


  /**
   * Determines whether an undigested password matches a (possibly digested)
   * reference value.
   *
   * @param  undigested  candidate clear text password.
   * @param  reference  reference password (possibly digested).
   *
   * @return  true if passwords match, false otherwise.
   */
  protected boolean matches(final String undigested, final String reference)
  {
    final String compare;
    if (digest != null) {
      compare = digest.digest(undigested.getBytes(), converter);
    } else {
      compare = undigested;
    }
    return reference.equals(compare);
  }
}
