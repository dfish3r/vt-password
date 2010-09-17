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
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * Unit test for {@link RuleChecker}.
 *
 * @author  Middleware Services
 * @version  $Revision$
 */
public class RuleCheckerTest
{

  /** Test checker. */
  private RuleChecker checker = new RuleChecker();

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
    final CharacterRule charRule = new CharacterRule();
    charRule.setNumberOfDigits(1);
    charRule.setNumberOfNonAlphanumeric(1);
    charRule.setNumberOfUppercase(1);
    charRule.setNumberOfLowercase(1);
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

    final UserIDRule userIDRule = new UserIDRule("testuser");
    userIDRule.setIgnoreCase(true);
    userIDRule.setMatchBackwards(true);

    final HistoryRule historyRule = new HistoryRule();
    historyRule.useDigest("SHA-1", new Base64Converter());
    historyRule.addHistory("safx/LW8+SsSy/o3PmCNy4VEm5s=");
    historyRule.addHistory("zurb9DyQ5nooY1la8h86Bh0n1iw=");
    historyRule.addHistory("bhqabXwE3S8E6xNJfX/d76MFOCs=");

    final SourceRule sourceRule = new SourceRule();
    sourceRule.useDigest("SHA-1", new Base64Converter());
    sourceRule.addSource("System B", "CJGTDMQRP+rmHApkcijC80aDV0o=");

    this.checker.getPasswordRules().add(charRule);
    this.checker.getPasswordRules().add(whitespaceRule);
    this.checker.getPasswordRules().add(lengthRule);
    this.checker.getPasswordRules().add(dictRule);
    this.checker.getPasswordRules().add(seqRule);
    this.checker.getPasswordRules().add(userIDRule);
    this.checker.getPasswordRules().add(historyRule);
    this.checker.getPasswordRules().add(sourceRule);
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
        {"4326789032", false},

        /** all non-alphanumeric */
        {"$&!$#@*{{>", false},

        /** all lowercase */
        {"aycdopezss", false},

        /** all uppercase */
        {"AYCDOPEZSS", false},

        /** digits and non-alphanumeric */
        {"@&3*(%5{}^", false},

        /** digits and lowercase */
        {"ay3dop5zss", false},

        /** digits and uppercase */
        {"AY3DOP5ZSS", false},

        /** non-alphanumeric and lowercase */
        {"a&c*o%ea}s", false},

        /** non-alphanumeric and uppercase */
        {"A&C*O%EA}S", false},

        /** uppercase and lowercase */
        {"AycDOPdsyz", false},

        /** invalid whitespace rule passwords. */

        /** contains a space */
        {"AycD Pdsyz", false},

        /** contains a tab */
        {"AycD    Psyz", false},

        /** invalid length rule passwords. */

        /** too short */
        {"p4T3t#", false},

        /** too long */
        {"p4t3t#n6574632vbad#@!8", false},

        /** invalid dictionary rule passwords. */

        /** matches dictionary word 'none' */
        {"p4t3t#none", false},

        /** matches dictionary word 'none' backwards */
        {"p4t3t#enon", false},

        /** invalid sequence rule passwords. */

        /** matches sequence 'zxcvb' */
        {"p4zxcvb#n65", false},

        /** matches sequence 'werty' backwards */
        {"p4ytrew#n65", false},

        /** matches sequence 'iop[]' ignore case */
        {"p4iOP[]#n65", false},

        /** invalid userid rule passwords. */

        /** contains userid 'testuser' */
        {"p4testuser#n65", false},

        /** contains userid 'testuser' backwards */
        {"p4resutset#n65", false},

        /** contains userid 'testuser' ignore case */
        {"p4TeStusEr#n65", false},

        /** invalid history rule passwords. */

        /** contains history password */
        {"t3stUs3r02", false},

        /** contains history password */
        {"t3stUs3r03", false},

        /** contains source password */
        {"t3stUs3r04", false},

        /** valid passwords. */

        /** digits, non-alphanumeric, lowercase, uppercase */
        {"p4T3t#N65", true},

        /** digits, non-alphanumeric, lowercase */
        {"p4t3t#n65", true},

        /** digits, non-alphanumeric, uppercase */
        {"P4T3T#N65", true},

        /** digits, uppercase, lowercase */
        {"p4t3tCn65", true},

        /** non-alphanumeric, lowercase, uppercase */
        {"pxT%t#Nwq", true},
      };
  }


  /**
   * @param  pass  to check
   * @param  valid  whether the supplied password should pass the check
   *
   * @throws  Exception  On test failure.
   */
  @Test(
    groups = {"passtest"},
    dataProvider = "passwords"
  )
  public void checkPassword(final String pass, final boolean valid)
    throws Exception
  {
    if (valid) {
      AssertJUnit.assertTrue(
        this.checker.checkPassword(new Password(pass)).isValid());
    } else {
      AssertJUnit.assertFalse(
        this.checker.checkPassword(new Password(pass)).isValid());
    }
  }
}
