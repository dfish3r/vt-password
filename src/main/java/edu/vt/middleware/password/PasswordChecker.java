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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import edu.vt.middleware.dictionary.Dictionary;

/**
 * <code>PasswordChecker</code> contains methods for setting pasword rules and
 * then determining if a password meets the requirements of all the rules.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */

public final class PasswordChecker
{

  /** rules to apply when checking a password. */
  private List<PasswordRule> rules = new ArrayList<PasswordRule>();


  /**
   * This will add a new rule to the <code>PasswordChecker</code>.
   *
   * @param  rule  <code>PasswordRule</code> to add
   */
  public void addPasswordRule(final PasswordRule rule)
  {
    this.rules.add(rule);
  }


  /**
   * This will remove a rule from the <code>PasswordChecker</code>. This method
   * does nothing if the supplied rule does not exist.
   *
   * @param  rule  <code>PasswordRule</code> to remove
   */
  public void removePasswordRule(final PasswordRule rule)
  {
    this.rules.remove(rule);
  }


  /**
   * This will return all the rules currently being used by this <code>
   * PasswordChecker</code>.
   *
   * @return  <code>List</code> of rules
   */
  public List<PasswordRule> getPasswordRules()
  {
    return this.rules;
  }


  /**
   * This will check the supplied password against the rules that have been
   * added to this <code>PasswordChecker</code>.
   *
   * @param  password  <code>Password</code> to check
   *
   * @return  <code>boolean</code> - whether the supplied password met the
   * requirements of all rules
   *
   * @throws  PasswordException  if the supplied password does not meet all
   * requirements of all rules
   */
  public boolean checkPassword(final Password password)
    throws PasswordException
  {
    for (PasswordRule rule : this.rules) {
      if (!rule.verifyPassword(password)) {
        throw new PasswordException(rule);
      }
    }
    return true;
  }


  /**
   * This provides command line access to a <code>PasswordChecker</code>.
   *
   * @param  args  <code>String[]</code>
   *
   * @throws  Exception  if an error occurs
   */
  public static void main(final String[] args)
    throws Exception
  {
    final PasswordChecker checker = new PasswordChecker();
    String password = null;
    try {
      if (args.length < 2) {
        throw new ArrayIndexOutOfBoundsException();
      }
      for (int i = 0; i < args.length; i++) {
        if ("-l".equals(args[i])) {
          final int min = Integer.parseInt(args[++i]);
          final int max = Integer.parseInt(args[++i]);
          final PasswordLengthRule rule = new PasswordLengthRule(min, max);
          checker.addPasswordRule(rule);
        } else if ("-c".equals(args[i])) {
          final PasswordCharacterRule rule = new PasswordCharacterRule();
          rule.setNumberOfDigits(Integer.parseInt(args[++i]));
          rule.setNumberOfAlphabetical(Integer.parseInt(args[++i]));
          rule.setNumberOfNonAlphanumeric(Integer.parseInt(args[++i]));
          rule.setNumberOfUppercase(Integer.parseInt(args[++i]));
          rule.setNumberOfLowercase(Integer.parseInt(args[++i]));
          rule.setNumberOfCharacteristics(Integer.parseInt(args[++i]));
          checker.addPasswordRule(rule);
        } else if ("-d".equals(args[i])) {
          final Dictionary dict = new Dictionary();
          dict.useMedian();
          dict.ignoreCase();
          dict.insert(new File(args[++i]));
          dict.build();

          final PasswordDictionaryRule rule = new PasswordDictionaryRule(dict);
          rule.matchBackwards();
          rule.setNumberOfCharacters(Integer.parseInt(args[++i]));
          checker.addPasswordRule(rule);
        } else if ("-u".equals(args[i])) {
          final PasswordUserIDRule rule = new PasswordUserIDRule(args[++i]);
          rule.ignoreCase();
          rule.matchBackwards();
          checker.addPasswordRule(rule);
        } else if ("-k".equals(args[i])) {
          final PasswordSequenceRule rule = new PasswordSequenceRule();
          rule.ignoreCase();
          rule.matchBackwards();
          checker.addPasswordRule(rule);
        } else if ("-h".equals(args[i])) {
          throw new ArrayIndexOutOfBoundsException();
        } else {
          password = args[i];
        }
      }

      if (password == null) {
        throw new ArrayIndexOutOfBoundsException();
      } else if (checker.checkPassword(new Password(password))) {
        System.out.println("Valid password");
      }

    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.println(
        "Usage: java " + checker.getClass().getName() + " <password> \\");
      System.out.println("       -l (Set the min & max password length) \\");
      System.out.println("          <min> \\");
      System.out.println("          <max> \\");
      System.out.println(
        "       -c (Set the characters which must be present" +
        " in the password) \\");
      System.out.println("          (Each of the following must be >= 0) \\");
      System.out.println("          <digits> \\");
      System.out.println("          <alphabetical> \\");
      System.out.println("          <non-alphanumeric> \\");
      System.out.println("          <uppercase> \\");
      System.out.println("          <lowercase> \\");
      System.out.println(
        "          <num> (Number of these rules to" +
        " enforce) \\");
      System.out.println("       -d (Test password against a dictionary) \\");
      System.out.println("          <file> (dictionary files) \\");
      System.out.println(
        "          <num> (number of characters in matching" +
        " words) \\");
      System.out.println("       -u (Test for a user id) \\");
      System.out.println("          <userid> \\");
      System.out.println("       -k (Test for keyboard sequences) \\");
      System.out.println("       -h (Print this message) \\");
      System.exit(1);
    }
  }
}
