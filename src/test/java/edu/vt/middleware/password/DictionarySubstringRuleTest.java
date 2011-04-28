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

import java.io.FileReader;
import edu.vt.middleware.dictionary.ArrayWordList;
import edu.vt.middleware.dictionary.WordListDictionary;
import edu.vt.middleware.dictionary.WordLists;
import edu.vt.middleware.dictionary.sort.ArraysSort;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * Unit test for {@link DictionarySubstringRule}.
 *
 * @author  Middleware Services
 * @version  $Revision$
 */
public class DictionarySubstringRuleTest extends AbstractRuleTest
{

  /** Test password. */
  private static final Password VALID_PASS = new Password("p4t3t#7wd5gew");

  /** Test password. */
  private static final Password DICT_PASS = new Password("p4tlancely5gew");

  /** Test password. */
  private static final Password BACKWARDS_DICT_PASS = new Password(
    "p4tylecnal5gew");

  /** Test password. */
  private static final Password UPPERCASE_DICT_PASS = new Password(
    "p4tlAnCeLy5gew");

  /** Test password. */
  private static final Password BACKWARDS_UPPERCASE_DICT_PASS = new Password(
    "p4tyLeCnAl5gew");

  /** For testing. */
  private DictionarySubstringRule rule = new DictionarySubstringRule();

  /** For testing. */
  private DictionarySubstringRule backwardsRule = new DictionarySubstringRule();

  /** For testing. */
  private DictionarySubstringRule ignoreCaseRule =
    new DictionarySubstringRule();

  /** For testing. */
  private DictionarySubstringRule allRule = new DictionarySubstringRule();


  /**
   * Initialize rules for this test.
   *
   * @param  dictFile  dictionary file to read
   *
   * @throws  Exception  if dictionary files cannot be read
   */
  @Parameters("dictionaryFile")
  @BeforeClass(groups = {"passtest"})
  public void createRules(final String dictFile)
    throws Exception
  {
    final ArrayWordList caseSensitiveWordList = WordLists.createFromReader(
      new FileReader[] {new FileReader(dictFile)},
      true,
      new ArraysSort());
    final WordListDictionary caseSensitiveDict = new WordListDictionary(
      caseSensitiveWordList);

    final ArrayWordList caseInsensitiveWordList = WordLists.createFromReader(
      new FileReader[] {new FileReader(dictFile)},
      false,
      new ArraysSort());
    final WordListDictionary caseInsensitiveDict = new WordListDictionary(
      caseInsensitiveWordList);

    rule.setDictionary(caseSensitiveDict);

    backwardsRule.setDictionary(caseSensitiveDict);
    backwardsRule.setMatchBackwards(true);

    ignoreCaseRule.setDictionary(caseInsensitiveDict);

    allRule.setDictionary(caseInsensitiveDict);
    allRule.setMatchBackwards(true);
  }


  /**
   * @return  Test data.
   *
   * @throws  Exception  On test data generation failure.
   */
  @DataProvider(name = "passwords")
  public Object[][] passwords()
    throws Exception
  {
    return
      new Object[][] {

        {rule, new PasswordData(VALID_PASS), true, },
        {rule, new PasswordData(DICT_PASS), false, },
        {rule, new PasswordData(BACKWARDS_DICT_PASS), true, },
        {rule, new PasswordData(UPPERCASE_DICT_PASS), true, },
        {rule, new PasswordData(BACKWARDS_UPPERCASE_DICT_PASS), true, },

        {backwardsRule, new PasswordData(VALID_PASS), true, },
        {backwardsRule, new PasswordData(DICT_PASS), false, },
        {backwardsRule, new PasswordData(BACKWARDS_DICT_PASS), false, },
        {backwardsRule, new PasswordData(UPPERCASE_DICT_PASS), true, },
        {
          backwardsRule,
          new PasswordData(BACKWARDS_UPPERCASE_DICT_PASS),
          true,
        },

        {ignoreCaseRule, new PasswordData(VALID_PASS), true, },
        {ignoreCaseRule, new PasswordData(DICT_PASS), false, },
        {ignoreCaseRule, new PasswordData(BACKWARDS_DICT_PASS), true, },
        {ignoreCaseRule, new PasswordData(UPPERCASE_DICT_PASS), false, },
        {
          ignoreCaseRule,
          new PasswordData(BACKWARDS_UPPERCASE_DICT_PASS),
          true,
        },

        {allRule, new PasswordData(VALID_PASS), true, },
        {allRule, new PasswordData(DICT_PASS), false, },
        {allRule, new PasswordData(BACKWARDS_DICT_PASS), false, },
        {allRule, new PasswordData(UPPERCASE_DICT_PASS), false, },
        {
          allRule,
          new PasswordData(BACKWARDS_UPPERCASE_DICT_PASS),
          false,
        },
      };
  }


  /**
   * @throws  Exception  On test failure.
   */
  @Test(groups = {"passtest"})
  public void resolveMessage()
    throws Exception
  {
    RuleResult result = rule.validate(new PasswordData(DICT_PASS));
    for (RuleResultDetail detail : result.getDetails()) {
      AssertJUnit.assertEquals(
        String.format("Password contains the dictionary word '%s'.", "lance"),
        DEFAULT_RESOLVER.resolve(detail));
      AssertJUnit.assertNotNull(EMPTY_RESOLVER.resolve(detail));
    }

    result = rule.validate(new PasswordData(BACKWARDS_DICT_PASS));
    for (RuleResultDetail detail : result.getDetails()) {
      AssertJUnit.assertEquals(
        String.format(
          "Password contains the reversed dictionary word '%s'.", "lance"),
        DEFAULT_RESOLVER.resolve(detail));
      AssertJUnit.assertNotNull(EMPTY_RESOLVER.resolve(detail));
    }
  }
}
