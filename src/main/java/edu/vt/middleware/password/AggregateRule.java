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
 * <code>AggregateRule</code> contains methods for setting password rules and
 * then determining if a password meets the requirements of all the rules.
 *
 * @param  <T>  type of rules to validate
 *
 * @author  Middleware Services
 * @version  $Revision$ $Date$
 */

public class AggregateRule<T extends Rule> implements Rule
{

  /** rules to apply when checking a password. */
  protected List<T> rules = new ArrayList<T>();


  /**
   * This will return the rules being used by this <code>AggregateRule</code>.
   *
   * @return  <code>List</code> of rules
   */
  public List<T> getRules()
  {
    return this.rules;
  }


  /**
   * This will set the rules to be used by this <code>AggregateRule</code>.
   *
   * @param  l  <code>List</code> of rules
   */
  public void setRules(final List<T> l)
  {
    this.rules = l;
  }


  /** {@inheritDoc} */
  public RuleResult validate(final Password password)
  {
    final RuleResult result = new RuleResult(true);
    for (Rule rule : this.rules) {
      final RuleResult rr = rule.validate(password);
      if (!rr.isValid()) {
        result.setValid(false);
        result.getDetails().addAll(rr.getDetails());
      }
    }
    return result;
  }


  /**
   * This provides command line access to an <code>AggregateRule</code>.
   *
   * @param  args  <code>String[]</code>
   *
   * @throws  Exception  if an error occurs
   */
  public static void main(final String[] args)
    throws Exception
  {
    final AggregateRule<Rule> aggregateRule = new AggregateRule<Rule>();
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
          aggregateRule.getRules().add(rule);
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
          aggregateRule.getRules().add(rule);
        } else if ("-d".equals(args[i])) {
          final TernaryTreeDictionary dict = new TernaryTreeDictionary(
            new FileWordList(new RandomAccessFile(args[++i], "r"), false));
          final DictionarySubstringRule rule = new DictionarySubstringRule(
            dict);
          rule.setMatchBackwards(true);
          rule.setWordLength(Integer.parseInt(args[++i]));
          aggregateRule.getRules().add(rule);
        } else if ("-u".equals(args[i])) {
          final UsernameRule rule = new UsernameRule(args[++i]);
          rule.setIgnoreCase(true);
          rule.setMatchBackwards(true);
          aggregateRule.getRules().add(rule);
        } else if ("-k".equals(args[i])) {
          final SequenceRule rule = new SequenceRule();
          rule.setIgnoreCase(true);
          rule.setMatchBackwards(true);
          aggregateRule.getRules().add(rule);
        } else if ("-h".equals(args[i])) {
          throw new ArrayIndexOutOfBoundsException();
        } else {
          password = args[i];
        }
      }

      if (password == null) {
        throw new ArrayIndexOutOfBoundsException();
      } else {
        final RuleResult result =
          aggregateRule.validate(new Password(password));
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
        "Usage: java " + aggregateRule.getClass().getName() + " <password> \\");
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
