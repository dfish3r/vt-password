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

import edu.vt.middleware.crypt.digest.DigestAlgorithm;
import edu.vt.middleware.crypt.util.Converter;

/**
 * <code>AbstractDigestRule</code> provides core methods for password rules that
 * use a digest.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */

public abstract class AbstractDigestRule extends AbstractPasswordRule
{

  /** digest object to use if comparing hashed passwords. */
  protected DigestAlgorithm digest;

  /** converter to use in conjunction with the digest. */
  protected Converter converter;


  /**
   * This will set the supplied digest algorithm to be used when password
   * comparison are made. Useful if you need to compare password which are in a
   * hashed form. See {@link edu.vt.middleware.crypt.digest.DigestAlgorithm}.
   *
   * @param  algorithm  <code>String</code> to use for hashing
   * @param  conv  <code>Converter</code> to convert bytes to string
   */
  public void useDigest(final String algorithm, final Converter conv)
  {
    this.digest = DigestAlgorithm.newInstance(algorithm);
    this.converter = conv;
  }
}
