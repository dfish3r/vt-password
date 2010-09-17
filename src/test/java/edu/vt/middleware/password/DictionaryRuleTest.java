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
  private static final String VALID_PASS = "Pullm@n1z3";

  /** Test password. */
  private static final String DICT_PASS = "Pullmanize";

  /** Test password. */
  private static final String BACKWARDS_DICT_PASS = "ezinamlluP";

  /** Test password. */
  private static final String UPPERCASE_DICT_PASS = "PuLLmanIZE";

  /** Test password. */
  private static final String BACKWARDS_UPPERCASE_DICT_PASS = "EZInamLLuP";

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

        {this.rule, VALID_PASS, true},
        {this.rule, DICT_PASS, false},
        {this.rule, BACKWARDS_DICT_PASS, true},
        {this.rule, UPPERCASE_DICT_PASS, true},
        {this.rule, BACKWARDS_UPPERCASE_DICT_PASS, true},

        {this.backwardsRule, VALID_PASS, true},
        {this.backwardsRule, DICT_PASS, false},
        {this.backwardsRule, BACKWARDS_DICT_PASS, false},
        {this.backwardsRule, UPPERCASE_DICT_PASS, true},
        {this.backwardsRule, BACKWARDS_UPPERCASE_DICT_PASS, true},

        {this.ignoreCaseRule, VALID_PASS, true},
        {this.ignoreCaseRule, DICT_PASS, false},
        {this.ignoreCaseRule, BACKWARDS_DICT_PASS, true},
        {this.ignoreCaseRule, UPPERCASE_DICT_PASS, false},
        {this.ignoreCaseRule, BACKWARDS_UPPERCASE_DICT_PASS, true},

        {this.allRule, VALID_PASS, true},
        {this.allRule, DICT_PASS, false},
        {this.allRule, BACKWARDS_DICT_PASS, false},
        {this.allRule, UPPERCASE_DICT_PASS, false},
        {this.allRule, BACKWARDS_UPPERCASE_DICT_PASS, false},
      };
  }
}
