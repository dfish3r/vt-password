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

import java.io.RandomAccessFile;
import edu.vt.middleware.dictionary.FileWordList;
import edu.vt.middleware.dictionary.TernaryTreeDictionary;

/**
 * <code>PasswordCli</code> provides a simple command line interface to password
 * validation.
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */

public final class PasswordCli
{


  /** Default constructor. */
  private PasswordCli() {}


  /**
   * This provides command line access to password rules.
   *
   * @param  args  <code>String[]</code>
   *
   * @throws  Exception  if an error occurs
   */
  public static void main(final String[] args)
    throws Exception
  {
    final RuleList ruleList = new RuleList();
    String username = null;
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
          ruleList.getRules().add(rule);
        } else if ("-c".equals(args[i])) {
          final CharacterCharacteristicsRule rule =
            new CharacterCharacteristicsRule();
          rule.getRules().add(
            new DigitCharacterRule(Integer.parseInt(args[++i])));
          rule.getRules().add(
            new AlphabeticalCharacterRule(Integer.parseInt(args[++i])));
          rule.getRules().add(
            new NonAlphanumericCharacterRule(Integer.parseInt(args[++i])));
          rule.getRules().add(
            new UppercaseCharacterRule(Integer.parseInt(args[++i])));
          rule.getRules().add(
            new LowercaseCharacterRule(Integer.parseInt(args[++i])));
          rule.setNumberOfCharacteristics(Integer.parseInt(args[++i]));
          ruleList.getRules().add(rule);
        } else if ("-d".equals(args[i])) {
          final TernaryTreeDictionary dict = new TernaryTreeDictionary(
            new FileWordList(new RandomAccessFile(args[++i], "r"), false));
          final DictionarySubstringRule rule = new DictionarySubstringRule(
            dict);
          rule.setMatchBackwards(true);
          rule.setWordLength(Integer.parseInt(args[++i]));
          ruleList.getRules().add(rule);
        } else if ("-u".equals(args[i])) {
          ruleList.getRules().add(new UsernameRule(true, true));
          username = args[++i];
        } else if ("-s".equals(args[i])) {
          ruleList.getRules().add(new QwertySequenceRule());
          ruleList.getRules().add(new AlphabeticalSequenceRule());
          ruleList.getRules().add(new NumericalSequenceRule());
          ruleList.getRules().add(new RepeatCharacterRegexRule());
        } else if ("-h".equals(args[i])) {
          throw new ArrayIndexOutOfBoundsException();
        } else {
          password = args[i];
        }
      }

      if (password == null) {
        throw new ArrayIndexOutOfBoundsException();
      } else {
        RuleResult result = null;
        final PasswordData pd = new PasswordData(new Password(password));
        if (username == null) {
          result = PasswordValidator.validate(ruleList, pd);
        } else {
          pd.setUsername(username);
          result = PasswordValidator.validate(ruleList, pd);
        }
        if (result.isValid()) {
          System.out.println("Valid password");
        } else {
          for (RuleResultDetail rrd : result.getDetails()) {
            System.out.println(rrd.getMessage());
          }
        }
      }

    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.println(
        "Usage: java " + PasswordValidator.class.getName() + " <password> \\");
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
      System.out.println("       -s (Test for sequences) \\");
      System.out.println("       -h (Print this message) \\");
      System.exit(1);
    }
  }
}
