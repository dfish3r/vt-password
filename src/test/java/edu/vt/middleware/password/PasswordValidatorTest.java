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
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * Unit test for {@link RuleList}.
 *
 * @author  Middleware Services
 * @version  $Revision$
 */
public class PasswordValidatorTest
{

  /** Test password. */
  private static final Password VALID_PASS = new Password("aBcD3FgH1Jk");

  /** Test password. */
  private static final Password INVALID_PASS = new Password("aBcDeFgHiJk");

  /** For testing. */
  private static final String USER = "testuser";

  /** For testing. */
  private List<String> history = new ArrayList<String>();

  /** For testing. */
  private Map<String, String> sources = new HashMap<String, String>();

  /** Test checker. */
  private List<Rule> rules = new ArrayList<Rule>();

  /** Word list. */
  private Dictionary dict;

  /** For testing. */
  private PasswordValidator validator;


  /**
   * @param  dictFile  to load.
   *
   * @throws  Exception  On test failure.
   */
  @Parameters("dictionaryFile")
  @BeforeClass(groups = {"passtest"})
  public void createDictionary(final String dictFile)
    throws Exception
  {
    final ArrayWordList awl = WordLists.createFromReader(
      new FileReader[] {new FileReader(dictFile)},
      false,
      new ArraysSort());
    dict = new WordListDictionary(awl);
    validator = new PasswordValidator(rules);
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

    final DictionarySubstringRule dictRule = new DictionarySubstringRule(dict);
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
    history.add("safx/LW8+SsSy/o3PmCNy4VEm5s=");
    history.add("zurb9DyQ5nooY1la8h86Bh0n1iw=");
    history.add("bhqabXwE3S8E6xNJfX/d76MFOCs=");

    final SourceRule sourceRule = new SourceRule();
    sourceRule.setDigest("SHA-1", new Base64Converter());
    sources.put("System B", "CJGTDMQRP+rmHApkcijC80aDV0o=");

    rules.add(charRule);
    rules.add(whitespaceRule);
    rules.add(lengthRule);
    rules.add(dictRule);
    rules.add(qwertySeqRule);
    rules.add(alphaSeqRule);
    rules.add(numSeqRule);
    rules.add(dupSeqRule);
    rules.add(userIDRule);
    rules.add(historyRule);
    rules.add(sourceRule);
  }


  /** @throws  Exception  On test failure. */
  @Test(groups = {"passtest"})
  public void validate()
    throws Exception
  {
    final List<Rule> l = new ArrayList<Rule>();
    final PasswordValidator pv = new PasswordValidator(l);

    l.add(new LengthRule(8, 16));

    final CharacterCharacteristicsRule ccRule =
      new CharacterCharacteristicsRule();
    ccRule.getRules().add(new DigitCharacterRule(1));
    ccRule.getRules().add(new NonAlphanumericCharacterRule(1));
    ccRule.getRules().add(new UppercaseCharacterRule(1));
    ccRule.getRules().add(new LowercaseCharacterRule(1));
    ccRule.setNumberOfCharacteristics(3);
    l.add(ccRule);

    l.add(new QwertySequenceRule());
    l.add(new AlphabeticalSequenceRule());
    l.add(new NumericalSequenceRule());
    l.add(new RepeatCharacterRegexRule());

    final RuleResult resultPass = pv.validate(new PasswordData(VALID_PASS));
    AssertJUnit.assertTrue(resultPass.isValid());
    AssertJUnit.assertTrue(pv.getMessages(resultPass).size() == 0);

    final RuleResult resultFail = pv.validate(new PasswordData(INVALID_PASS));
    AssertJUnit.assertFalse(resultFail.isValid());
    AssertJUnit.assertTrue(pv.getMessages(resultFail).size() > 0);

    l.add(new UsernameRule(true, true));

    try {
      pv.validate(new PasswordData(VALID_PASS));
      AssertJUnit.fail("Should have thrown NullPointerException");
    } catch (NullPointerException e) {
      AssertJUnit.assertEquals(e.getClass(), NullPointerException.class);
    } catch (Exception e) {
      AssertJUnit.fail(
        "Should have thrown NullPointerException, threw " + e.getMessage());
    }

    final PasswordData valid = new PasswordData(VALID_PASS);
    valid.setUsername(USER);
    AssertJUnit.assertTrue(pv.validate(valid).isValid());

    final PasswordData invalid = new PasswordData(INVALID_PASS);
    invalid.setUsername(USER);
    AssertJUnit.assertFalse(pv.validate(invalid).isValid());
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
          PasswordData.newInstance(
            new Password("4326789032"), USER, history, sources),
          false,
        },

        /** all non-alphanumeric */
        {
          PasswordData.newInstance(
            new Password("$&!$#@*{{>"), USER, history, sources),
          false,
        },

        /** all lowercase */
        {
          PasswordData.newInstance(
            new Password("aycdopezss"), USER, history, sources),
          false,
        },

        /** all uppercase */
        {
          PasswordData.newInstance(
            new Password("AYCDOPEZSS"), USER, history, sources),
          false,
        },

        /** digits and non-alphanumeric */
        {
          PasswordData.newInstance(
            new Password("@&3*(%5{}^"), USER, history, sources),
          false,
        },

        /** digits and lowercase */
        {
          PasswordData.newInstance(
            new Password("ay3dop5zss"), USER, history, sources),
          false,
        },

        /** digits and uppercase */
        {
          PasswordData.newInstance(
            new Password("AY3DOP5ZSS"), USER, history, sources),
          false,
        },

        /** non-alphanumeric and lowercase */
        {
          PasswordData.newInstance(
            new Password("a&c*o%ea}s"), USER, history, sources),
          false,
        },

        /** non-alphanumeric and uppercase */
        {
          PasswordData.newInstance(
            new Password("A&C*O%EA}S"), USER, history, sources),
          false,
        },

        /** uppercase and lowercase */
        {
          PasswordData.newInstance(
            new Password("AycDOPdsyz"), USER, history, sources),
          false,
        },

        /** invalid whitespace rule passwords. */

        /** contains a space */
        {
          PasswordData.newInstance(
            new Password("AycD Pdsyz"), USER, history, sources),
          false,
        },

        /** contains a tab */
        {
          PasswordData.newInstance(
            new Password("AycD    Psyz"), USER, history, sources),
          false,
        },

        /** invalid length rule passwords. */

        /** too short */
        {
          PasswordData.newInstance(
            new Password("p4T3t#"), USER, history, sources),
          false,
        },

        /** too long */
        {
          PasswordData.newInstance(
            new Password("p4t3t#n6574632vbad#@!8"),
            USER,
            history,
            sources),
          false,
        },

        /** invalid dictionary rule passwords. */

        /** matches dictionary word 'none' */
        {
          PasswordData.newInstance(
            new Password("p4t3t#none"), USER, history, sources),
          false,
        },

        /** matches dictionary word 'none' backwards */
        {
          PasswordData.newInstance(
            new Password("p4t3t#enon"), USER, history, sources),
          false,
        },

        /** invalid sequence rule passwords. */

        /** matches sequence 'zxcvb' */
        {
          PasswordData.newInstance(
            new Password("p4zxcvb#n65"), USER, history, sources),
          false,
        },

        /** matches sequence 'werty' backwards */
        {
          PasswordData.newInstance(
            new Password("p4ytrew#n65"), USER, history, sources),
          false,
        },

        /** matches sequence 'iop[]' ignore case */
        {
          PasswordData.newInstance(
            new Password("p4iOP[]#n65"), USER, history, sources),
          false,
        },

        /** invalid userid rule passwords. */

        /** contains userid 'testuser' */
        {
          PasswordData.newInstance(
            new Password("p4testuser#n65"), USER, history, sources),
          false,
        },

        /** contains userid 'testuser' backwards */
        {
          PasswordData.newInstance(
            new Password("p4resutset#n65"), USER, history, sources),
          false,
        },

        /** contains userid 'testuser' ignore case */
        {
          PasswordData.newInstance(
            new Password("p4TeStusEr#n65"), USER, history, sources),
          false,
        },

        /** invalid history rule passwords. */

        /** contains history password */
        {
          PasswordData.newInstance(
            new Password("t3stUs3r02"), USER, history, sources),
          false,
        },

        /** contains history password */
        {
          PasswordData.newInstance(
            new Password("t3stUs3r03"), USER, history, sources),
          false,
        },

        /** contains source password */
        {
          PasswordData.newInstance(
            new Password("t3stUs3r04"), USER, history, sources),
          false,
        },

        /** valid passwords. */

        /** digits, non-alphanumeric, lowercase, uppercase */
        {
          PasswordData.newInstance(
            new Password("p4T3t#N65"), USER, history, sources),
          true,
        },

        /** digits, non-alphanumeric, lowercase */
        {
          PasswordData.newInstance(
            new Password("p4t3t#n65"), USER, history, sources),
          true,
        },

        /** digits, non-alphanumeric, uppercase */
        {
          PasswordData.newInstance(
            new Password("P4T3T#N65"), USER, history, sources),
          true,
        },

        /** digits, uppercase, lowercase */
        {
          PasswordData.newInstance(
            new Password("p4t3tCn65"), USER, history, sources),
          true,
        },

        /** non-alphanumeric, lowercase, uppercase */
        {
          PasswordData.newInstance(
            new Password("pxT%t#Nwq"), USER, history, sources),
          true,
        },
      };
  }


  /**
   * @param  passwordData  to check
   * @param  valid  whether the supplied rule data should pass the check
   *
   * @throws  Exception  On test failure.
   */
  @Test(
    groups = {"passtest"},
    dataProvider = "passwords"
  )
  public void checkPassword(
    final PasswordData passwordData,
    final boolean valid)
    throws Exception
  {
    if (valid) {
      final RuleResult result = validator.validate(passwordData);
      AssertJUnit.assertTrue(result.isValid());
      AssertJUnit.assertTrue(result.getDetails().isEmpty());
    } else {
      final RuleResult result = validator.validate(passwordData);
      AssertJUnit.assertFalse(result.isValid());
      AssertJUnit.assertTrue(!result.getDetails().isEmpty());
    }
  }
}
