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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import edu.vt.middleware.crypt.util.Base64Converter;
import edu.vt.middleware.dictionary.ArrayWordList;
import edu.vt.middleware.dictionary.Dictionary;
import edu.vt.middleware.dictionary.WordListDictionary;
import edu.vt.middleware.dictionary.WordLists;
import edu.vt.middleware.dictionary.sort.ArraysSort;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

/**
 * Unit test for {@link RuleList}.
 *
 * @author  Middleware Services
 * @version  $Revision$
 */
public class RuleListTest extends AbstractRuleTest
{

  /** For testing. */
  private static final String USER = "testuser";

  /** For testing. */
  private List<String> history = new ArrayList<String>();

  /** For testing. */
  private Map<String, String> sources = new HashMap<String, String>();

  /** Test checker. */
  private RuleList rule = new RuleList();

  /** Word list. */
  private Dictionary dict;


  /**
   * @param  dictFile  to load.
   *
   * @throws  Exception  On test failure.
   */
  @Parameters({ "dictionaryFile" })
  @BeforeClass(groups = {"passtest"})
  public void createDictionary(final String dictFile)
    throws Exception
  {
    final ArrayWordList awl = WordLists.createFromReader(
      new FileReader[] {new FileReader(dictFile)},
      false,
      new ArraysSort());
    this.dict = new WordListDictionary(awl);
  }


  /** @throws  Exception  On test failure. */
  @BeforeClass(
    groups = {"passtest"},
    dependsOnMethods = {"createDictionary"}
  )
  public void createChecker()
    throws Exception
  {
    final CharacterCharacteristicsRule charRule =
      new CharacterCharacteristicsRule();
    charRule.getRules().add(new DigitCharacterRule(1));
    charRule.getRules().add(new NonAlphanumericCharacterRule(1));
    charRule.getRules().add(new UppercaseCharacterRule(1));
    charRule.getRules().add(new LowercaseCharacterRule(1));
    charRule.setNumberOfCharacteristics(3);

    final WhitespaceRule whitespaceRule = new WhitespaceRule();

    final LengthRule lengthRule = new LengthRule(8, 16);

    final DictionarySubstringRule dictRule = new DictionarySubstringRule(
      this.dict);
    dictRule.setWordLength(4);
    dictRule.setMatchBackwards(true);

    final QwertySequenceRule qwertySeqRule = new QwertySequenceRule();

    final AlphabeticalSequenceRule alphaSeqRule =
      new AlphabeticalSequenceRule();

    final NumericalSequenceRule numSeqRule = new NumericalSequenceRule();

    final RepeatCharacterRegexRule dupSeqRule = new RepeatCharacterRegexRule();

    final UsernameRule userIDRule = new UsernameRule();
    userIDRule.setIgnoreCase(true);
    userIDRule.setMatchBackwards(true);

    final HistoryRule historyRule = new HistoryRule();
    historyRule.setDigest("SHA-1", new Base64Converter());
    this.history.add("safx/LW8+SsSy/o3PmCNy4VEm5s=");
    this.history.add("zurb9DyQ5nooY1la8h86Bh0n1iw=");
    this.history.add("bhqabXwE3S8E6xNJfX/d76MFOCs=");

    final SourceRule sourceRule = new SourceRule();
    sourceRule.setDigest("SHA-1", new Base64Converter());
    this.sources.put("System B", "CJGTDMQRP+rmHApkcijC80aDV0o=");

    this.rule.getRules().add(charRule);
    this.rule.getRules().add(whitespaceRule);
    this.rule.getRules().add(lengthRule);
    this.rule.getRules().add(dictRule);
    this.rule.getRules().add(qwertySeqRule);
    this.rule.getRules().add(alphaSeqRule);
    this.rule.getRules().add(numSeqRule);
    this.rule.getRules().add(dupSeqRule);
    this.rule.getRules().add(userIDRule);
    this.rule.getRules().add(historyRule);
    this.rule.getRules().add(sourceRule);
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

        /** invalid character rule passwords. */

        /** all digits */
        {
          this.rule,
          createPasswordData(
            new Password("4326789032"), USER, this.history, this.sources),
          false,
        },

        /** all non-alphanumeric */
        {
          this.rule,
          createPasswordData(
            new Password("$&!$#@*{{>"), USER, this.history, this.sources),
          false,
        },

        /** all lowercase */
        {
          this.rule,
          createPasswordData(
            new Password("aycdopezss"), USER, this.history, this.sources),
          false,
        },

        /** all uppercase */
        {
          this.rule,
          createPasswordData(
            new Password("AYCDOPEZSS"), USER, this.history, this.sources),
          false,
        },

        /** digits and non-alphanumeric */
        {
          this.rule,
          createPasswordData(
            new Password("@&3*(%5{}^"), USER, this.history, this.sources),
          false,
        },

        /** digits and lowercase */
        {
          this.rule,
          createPasswordData(
            new Password("ay3dop5zss"), USER, this.history, this.sources),
          false,
        },

        /** digits and uppercase */
        {
          this.rule,
          createPasswordData(
            new Password("AY3DOP5ZSS"), USER, this.history, this.sources),
          false,
        },

        /** non-alphanumeric and lowercase */
        {
          this.rule,
          createPasswordData(
            new Password("a&c*o%ea}s"), USER, this.history, this.sources),
          false,
        },

        /** non-alphanumeric and uppercase */
        {
          this.rule,
          createPasswordData(
            new Password("A&C*O%EA}S"), USER, this.history, this.sources),
          false,
        },

        /** uppercase and lowercase */
        {
          this.rule,
          createPasswordData(
            new Password("AycDOPdsyz"), USER, this.history, this.sources),
          false,
        },

        /** invalid whitespace rule passwords. */

        /** contains a space */
        {
          this.rule,
          createPasswordData(
            new Password("AycD Pdsyz"), USER, this.history, this.sources),
          false,
        },

        /** contains a tab */
        {
          this.rule,
          createPasswordData(
            new Password("AycD    Psyz"), USER, this.history, this.sources),
          false,
        },

        /** invalid length rule passwords. */

        /** too short */
        {
          this.rule,
          createPasswordData(
            new Password("p4T3t#"), USER, this.history, this.sources),
          false,
        },

        /** too long */
        {
          this.rule,
          createPasswordData(
            new Password("p4t3t#n6574632vbad#@!8"),
            USER,
            this.history,
            this.sources),
          false,
        },

        /** invalid dictionary rule passwords. */

        /** matches dictionary word 'none' */
        {
          this.rule,
          createPasswordData(
            new Password("p4t3t#none"), USER, this.history, this.sources),
          false,
        },

        /** matches dictionary word 'none' backwards */
        {
          this.rule,
          createPasswordData(
            new Password("p4t3t#enon"), USER, this.history, this.sources),
          false,
        },

        /** invalid sequence rule passwords. */

        /** matches sequence 'zxcvb' */
        {
          this.rule,
          createPasswordData(
            new Password("p4zxcvb#n65"), USER, this.history, this.sources),
          false,
        },

        /** matches sequence 'werty' backwards */
        {
          this.rule,
          createPasswordData(
            new Password("p4ytrew#n65"), USER, this.history, this.sources),
          false,
        },

        /** matches sequence 'iop[]' ignore case */
        {
          this.rule,
          createPasswordData(
            new Password("p4iOP[]#n65"), USER, this.history, this.sources),
          false,
        },

        /** invalid userid rule passwords. */

        /** contains userid 'testuser' */
        {
          this.rule,
          createPasswordData(
            new Password("p4testuser#n65"), USER, this.history, this.sources),
          false,
        },

        /** contains userid 'testuser' backwards */
        {
          this.rule,
          createPasswordData(
            new Password("p4resutset#n65"), USER, this.history, this.sources),
          false,
        },

        /** contains userid 'testuser' ignore case */
        {
          this.rule,
          createPasswordData(
            new Password("p4TeStusEr#n65"), USER, this.history, this.sources),
          false,
        },

        /** invalid history rule passwords. */

        /** contains history password */
        {
          this.rule,
          createPasswordData(
            new Password("t3stUs3r02"), USER, this.history, this.sources),
          false,
        },

        /** contains history password */
        {
          this.rule,
          createPasswordData(
            new Password("t3stUs3r03"), USER, this.history, this.sources),
          false,
        },

        /** contains source password */
        {
          this.rule,
          createPasswordData(
            new Password("t3stUs3r04"), USER, this.history, this.sources),
          false,
        },

        /** valid passwords. */

        /** digits, non-alphanumeric, lowercase, uppercase */
        {
          this.rule,
          createPasswordData(
            new Password("p4T3t#N65"), USER, this.history, this.sources),
          true,
        },

        /** digits, non-alphanumeric, lowercase */
        {
          this.rule,
          createPasswordData(
            new Password("p4t3t#n65"), USER, this.history, this.sources),
          true,
        },

        /** digits, non-alphanumeric, uppercase */
        {
          this.rule,
          createPasswordData(
            new Password("P4T3T#N65"), USER, this.history, this.sources),
          true,
        },

        /** digits, uppercase, lowercase */
        {
          this.rule,
          createPasswordData(
            new Password("p4t3tCn65"), USER, this.history, this.sources),
          true,
        },

        /** non-alphanumeric, lowercase, uppercase */
        {
          this.rule,
          createPasswordData(
            new Password("pxT%t#Nwq"), USER, this.history, this.sources),
          true,
        },
      };
  }
}
