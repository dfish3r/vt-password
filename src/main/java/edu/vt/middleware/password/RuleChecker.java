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

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import edu.vt.middleware.dictionary.FileWordList;
import edu.vt.middleware.dictionary.TernaryTreeDictionary;

/**
 * <code>RuleChecker</code> contains methods for setting password rules and
 * then determining if a password meets the requirements of all the rules.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */

public final class RuleChecker
{

  /** rules to apply when checking a password. */
  private List<Rule> rules = new ArrayList<Rule>();


  /**
   * This will return the rules being used by this <code>PasswordChecker</code>.
   *
   * @return  <code>List</code> of rules
   */
  public List<Rule> getPasswordRules()
  {
    return this.rules;
  }


  /**
   * This will set the rules to be used by this <code>PasswordChecker</code>.
   *
   * @param  l  <code>List</code> of rules
   */
  public void setPasswordRules(final List<Rule> l)
  {
    this.rules = l;
  }


  /**
   * This will check the supplied password against the rules that have been
   * added to this <code>RuleChecker</code>.
   *
   * @param  password  <code>Password</code> to check
   *
   * @return  <code>boolean</code> - whether the supplied password met the
   * requirements of all rules
   */
  public RuleCheckerResult checkPassword(final Password password)
  {
    final RuleCheckerResult result = new RuleCheckerResult();
    result.setValid(true);
    for (Rule rule : this.rules) {
      final RuleResult<?> prr = rule.verifyPassword(password);
      if (!prr.isValid()) {
        result.setValid(false);
        result.getDetails().add(prr);
      }
    }
    return result;
  }


  /**
   * This provides command line access to a <code>RuleChecker</code>.
   *
   * @param  args  <code>String[]</code>
   *
   * @throws  Exception  if an error occurs
   */
  public static void main(final String[] args)
    throws Exception
  {
    final RuleChecker checker = new RuleChecker();
    String password = null;
    try {
      if (args.length < 2) {
        throw new ArrayIndexOutOfBoundsException();
      }
      for (int i = 0; i < args.length; i++) {
        if ("-l".equals(args[i])) {
          final int min = Integer.parseInt(args[++i]);
          final int max = Integer.parseInt(args[++i]);
          final LengthRule rule = new LengthRule(min, max);
          checker.getPasswordRules().add(rule);
        } else if ("-c".equals(args[i])) {
          final CharacterRule rule = new CharacterRule();
          rule.setNumberOfDigits(Integer.parseInt(args[++i]));
          rule.setNumberOfAlphabetical(Integer.parseInt(args[++i]));
          rule.setNumberOfNonAlphanumeric(Integer.parseInt(args[++i]));
          rule.setNumberOfUppercase(Integer.parseInt(args[++i]));
          rule.setNumberOfLowercase(Integer.parseInt(args[++i]));
          rule.setNumberOfCharacteristics(Integer.parseInt(args[++i]));
          checker.getPasswordRules().add(rule);
        } else if ("-d".equals(args[i])) {
          final TernaryTreeDictionary dict = new TernaryTreeDictionary(
            new FileWordList(new RandomAccessFile(args[++i], "r"), false));
          final DictionarySubstringRule rule = new DictionarySubstringRule(
            dict);
          rule.setMatchBackwards(true);
          rule.setWordLength(Integer.parseInt(args[++i]));
          checker.getPasswordRules().add(rule);
        } else if ("-u".equals(args[i])) {
          final UserIDRule rule = new UserIDRule(args[++i]);
          rule.setIgnoreCase(true);
          rule.setMatchBackwards(true);
          checker.getPasswordRules().add(rule);
        } else if ("-k".equals(args[i])) {
          final SequenceRule rule = new SequenceRule();
          rule.setIgnoreCase(true);
          rule.setMatchBackwards(true);
          checker.getPasswordRules().add(rule);
        } else if ("-h".equals(args[i])) {
          throw new ArrayIndexOutOfBoundsException();
        } else {
          password = args[i];
        }
      }

      if (password == null) {
        throw new ArrayIndexOutOfBoundsException();
      } else {
        final RuleCheckerResult result =
          checker.checkPassword(new Password(password));
        if (result.isValid()) {
          System.out.println("Valid password");
        } else {
          for (RuleResult<?> prr : result.getDetails()) {
            System.out.println(prr.getDetails());
          }
        }
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
