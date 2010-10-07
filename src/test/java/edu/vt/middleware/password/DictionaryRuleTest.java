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

import java.io.FileReader;
import edu.vt.middleware.dictionary.ArrayWordList;
import edu.vt.middleware.dictionary.WordListDictionary;
import edu.vt.middleware.dictionary.WordLists;
import edu.vt.middleware.dictionary.sort.ArraysSort;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

/**
 * Unit test for {@link DictionaryRule}.
 *
 * @author  Middleware Services
 * @version  $Revision$
 */
public class DictionaryRuleTest extends AbstractRuleTest
{

  /** Test password. */
  private static final Password VALID_PASS = new Password("Pullm@n1z3");

  /** Test password. */
  private static final Password DICT_PASS = new Password("Pullmanize");

  /** Test password. */
  private static final Password BACKWARDS_DICT_PASS =
    new Password("ezinamlluP");

  /** Test password. */
  private static final Password UPPERCASE_DICT_PASS =
    new Password("PuLLmanIZE");

  /** Test password. */
  private static final Password BACKWARDS_UPPERCASE_DICT_PASS =
    new Password("EZInamLLuP");

  /** For testing. */
  private DictionaryRule rule = new DictionaryRule();

  /** For testing. */
  private DictionaryRule backwardsRule = new DictionaryRule();

  /** For testing. */
  private DictionaryRule ignoreCaseRule = new DictionaryRule();

  /** For testing. */
  private DictionaryRule allRule = new DictionaryRule();


  /**
   * Initialize rules for this test.
   *
   * @param  dictFile  dictionary file to read
   * @throws  Exception  if dictionary files cannot be read
   */
  @Parameters({ "dictionaryFile" })
  @BeforeClass(groups = {"passtest"})
  public void createRules(final String dictFile)
    throws Exception
  {
    final ArrayWordList caseSensitiveWordList = WordLists.createFromReader(
      new FileReader[] {new FileReader(dictFile)},
      true,
      new ArraysSort());
    final WordListDictionary caseSensitiveDict =
      new WordListDictionary(caseSensitiveWordList);

    final ArrayWordList caseInsensitiveWordList = WordLists.createFromReader(
      new FileReader[] {new FileReader(dictFile)},
      false,
      new ArraysSort());
    final WordListDictionary caseInsensitiveDict =
      new WordListDictionary(caseInsensitiveWordList);

    this.rule.setDictionary(caseSensitiveDict);

    this.backwardsRule.setDictionary(caseSensitiveDict);
    this.backwardsRule.setMatchBackwards(true);

    this.ignoreCaseRule.setDictionary(caseInsensitiveDict);

    this.allRule.setDictionary(caseInsensitiveDict);
    this.allRule.setMatchBackwards(true);
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

        {this.rule, null, VALID_PASS, true},
        {this.rule, null, DICT_PASS, false},
        {this.rule, null, BACKWARDS_DICT_PASS, true},
        {this.rule, null, UPPERCASE_DICT_PASS, true},
        {this.rule, null, BACKWARDS_UPPERCASE_DICT_PASS, true},

        {this.backwardsRule, null, VALID_PASS, true},
        {this.backwardsRule, null, DICT_PASS, false},
        {this.backwardsRule, null, BACKWARDS_DICT_PASS, false},
        {this.backwardsRule, null, UPPERCASE_DICT_PASS, true},
        {this.backwardsRule, null, BACKWARDS_UPPERCASE_DICT_PASS, true},

        {this.ignoreCaseRule, null, VALID_PASS, true},
        {this.ignoreCaseRule, null, DICT_PASS, false},
        {this.ignoreCaseRule, null, BACKWARDS_DICT_PASS, true},
        {this.ignoreCaseRule, null, UPPERCASE_DICT_PASS, false},
        {this.ignoreCaseRule, null, BACKWARDS_UPPERCASE_DICT_PASS, true},

        {this.allRule, null, VALID_PASS, true},
        {this.allRule, null, DICT_PASS, false},
        {this.allRule, null, BACKWARDS_DICT_PASS, false},
        {this.allRule, null, UPPERCASE_DICT_PASS, false},
        {this.allRule, null, BACKWARDS_UPPERCASE_DICT_PASS, false},
      };
  }
}
