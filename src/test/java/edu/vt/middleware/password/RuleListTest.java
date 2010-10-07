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
  private Username user = new Username("testuser");

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

    final SequenceRule seqRule = new SequenceRule();
    seqRule.setIgnoreCase(true);
    seqRule.setMatchBackwards(true);

    final UsernameRule userIDRule = new UsernameRule();
    userIDRule.setIgnoreCase(true);
    userIDRule.setMatchBackwards(true);

    final HistoryRule historyRule = new HistoryRule();
    historyRule.setDigest("SHA-1", new Base64Converter());
    this.user.getPasswordHistory().add("safx/LW8+SsSy/o3PmCNy4VEm5s=");
    this.user.getPasswordHistory().add("zurb9DyQ5nooY1la8h86Bh0n1iw=");
    this.user.getPasswordHistory().add("bhqabXwE3S8E6xNJfX/d76MFOCs=");

    final SourceRule sourceRule = new SourceRule();
    sourceRule.setDigest("SHA-1", new Base64Converter());
    this.user.addPasswordSource("System B", "CJGTDMQRP+rmHApkcijC80aDV0o=");

    this.rule.getRules().add(charRule);
    this.rule.getRules().add(whitespaceRule);
    this.rule.getRules().add(lengthRule);
    this.rule.getRules().add(dictRule);
    this.rule.getRules().add(seqRule);
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
        {this.rule, this.user, new Password("4326789032"), false},

        /** all non-alphanumeric */
        {this.rule, this.user, new Password("$&!$#@*{{>"), false},

        /** all lowercase */
        {this.rule, this.user, new Password("aycdopezss"), false},

        /** all uppercase */
        {this.rule, this.user, new Password("AYCDOPEZSS"), false},

        /** digits and non-alphanumeric */
        {this.rule, this.user, new Password("@&3*(%5{}^"), false},

        /** digits and lowercase */
        {this.rule, this.user, new Password("ay3dop5zss"), false},

        /** digits and uppercase */
        {this.rule, this.user, new Password("AY3DOP5ZSS"), false},

        /** non-alphanumeric and lowercase */
        {this.rule, this.user, new Password("a&c*o%ea}s"), false},

        /** non-alphanumeric and uppercase */
        {this.rule, this.user, new Password("A&C*O%EA}S"), false},

        /** uppercase and lowercase */
        {this.rule, this.user, new Password("AycDOPdsyz"), false},

        /** invalid whitespace rule passwords. */

        /** contains a space */
        {this.rule, this.user, new Password("AycD Pdsyz"), false},

        /** contains a tab */
        {this.rule, this.user, new Password("AycD    Psyz"), false},

        /** invalid length rule passwords. */

        /** too short */
        {this.rule, this.user, new Password("p4T3t#"), false},

        /** too long */
        {this.rule, this.user, new Password("p4t3t#n6574632vbad#@!8"), false},

        /** invalid dictionary rule passwords. */

        /** matches dictionary word 'none' */
        {this.rule, this.user, new Password("p4t3t#none"), false},

        /** matches dictionary word 'none' backwards */
        {this.rule, this.user, new Password("p4t3t#enon"), false},

        /** invalid sequence rule passwords. */

        /** matches sequence 'zxcvb' */
        {this.rule, this.user, new Password("p4zxcvb#n65"), false},

        /** matches sequence 'werty' backwards */
        {this.rule, this.user, new Password("p4ytrew#n65"), false},

        /** matches sequence 'iop[]' ignore case */
        {this.rule, this.user, new Password("p4iOP[]#n65"), false},

        /** invalid userid rule passwords. */

        /** contains userid 'testuser' */
        {this.rule, this.user, new Password("p4testuser#n65"), false},

        /** contains userid 'testuser' backwards */
        {this.rule, this.user, new Password("p4resutset#n65"), false},

        /** contains userid 'testuser' ignore case */
        {this.rule, this.user, new Password("p4TeStusEr#n65"), false},

        /** invalid history rule passwords. */

        /** contains history password */
        {this.rule, this.user, new Password("t3stUs3r02"), false},

        /** contains history password */
        {this.rule, this.user, new Password("t3stUs3r03"), false},

        /** contains source password */
        {this.rule, this.user, new Password("t3stUs3r04"), false},

        /** valid passwords. */

        /** digits, non-alphanumeric, lowercase, uppercase */
        {this.rule, this.user, new Password("p4T3t#N65"), true},

        /** digits, non-alphanumeric, lowercase */
        {this.rule, this.user, new Password("p4t3t#n65"), true},

        /** digits, non-alphanumeric, uppercase */
        {this.rule, this.user, new Password("P4T3T#N65"), true},

        /** digits, uppercase, lowercase */
        {this.rule, this.user, new Password("p4t3tCn65"), true},

        /** non-alphanumeric, lowercase, uppercase */
        {this.rule, this.user, new Password("pxT%t#Nwq"), true},
      };
  }
}
