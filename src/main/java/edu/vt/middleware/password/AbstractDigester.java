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
      final byte[] hash = converter.toBytes(reference);
      final int saltLength = hash.length - digest.getDigest().getDigestSize();
      // check if hash is longer than expected digest size
      if (saltLength > 0) {
        // extract the salt from the reference password
        final byte[] salt = extractSalt(hash, saltLength);
        // concatenate the salt to both the digest and the resulting output
        final byte[] saltedHash = addSalt(
          digest.digest(addSalt(undigested.getBytes(), salt)), salt);
        compare = converter.fromBytes(saltedHash);
      } else {
        compare = digest.digest(undigested.getBytes(), converter);
      }
    } else {
      compare = undigested;
    }
    return reference.equals(compare);
  }


  /**
   * Extracts a salt from the end of the supplied hash.
   *
   * @param  hash  to read salt from
   * @param  length  of the salt to extract
   *
   * @return  salt bytes
   */
  protected byte[] extractSalt(final byte[] hash, final int length)
  {
    final byte[] salt = new byte[length];
    System.arraycopy(hash, hash.length - length, salt, 0, length);
    return salt;
  }


  /**
   * Concatenates the supplied salt to the supplied data.
   *
   * @param  data  to concatenate salt to
   * @param  salt  to concatenate
   *
   * @return  new byte array of concatenated data
   */
  protected byte[] addSalt(final byte[] data, final byte[] salt)
  {
    final byte[] saltedData = new byte[data.length + salt.length];
    System.arraycopy(data, 0, saltedData, 0, data.length);
    System.arraycopy(salt, 0, saltedData, data.length, salt.length);
    return saltedData;
  }
}
