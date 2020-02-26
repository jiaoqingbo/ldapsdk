/*
 * Copyright 2010-2020 Ping Identity Corporation
 * All Rights Reserved.
 */
/*
 * Copyright 2010-2020 Ping Identity Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
 * Copyright (C) 2010-2020 Ping Identity Corporation
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License (GPLv2 only)
 * or the terms of the GNU Lesser General Public License (LGPLv2.1 only)
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses>.
 */
package com.unboundid.ldap.sdk.unboundidds;



import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import com.unboundid.ldap.sdk.LDAPSDKTestCase;
import com.unboundid.ldap.sdk.ResultCode;
import com.unboundid.util.CommandLineTool;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;



/**
 * This class provides a set of test cases for the {@code Launcher} class.
 */
public final class LauncherTestCase
       extends LDAPSDKTestCase
{
  /**
   * Provides a test case for launching the tool with a null set of arguments.
   */
  @Test()
  public void testNullArgs()
  {
    final PrintStream originalSystemOut = System.out;
    final PrintStream originalSystemErr = System.err;

    try
    {
      final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
      final ByteArrayOutputStream errStream = new ByteArrayOutputStream();

      System.setOut(new PrintStream(outStream));
      System.setErr(new PrintStream(errStream));

      Launcher.main((String[]) null);

      assertTrue(outStream.size() > 0);
      assertTrue(errStream.size() == 0);
    }
    finally
    {
      System.setOut(originalSystemOut);
      System.setErr(originalSystemErr);
    }
  }



  /**
   * Provides a test case for launching the tool with an empty set of arguments.
   */
  @Test()
  public void testEmptyArgs()
  {
    final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    final ByteArrayOutputStream errStream = new ByteArrayOutputStream();

    assertEquals(Launcher.main(outStream, errStream), ResultCode.SUCCESS);

    assertTrue(outStream.size() > 0);
    assertTrue(errStream.size() == 0);
  }



  /**
   * Provides a test case for launching the tool with a valid tool name and a
   * valid set of arguments for that tool.
   *
   * @param  toolName  The name of a valid tool.
   */
  @Test(dataProvider="valid-tool-names")
  public void testValidToolName(final String toolName)
  {
    final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    final ByteArrayOutputStream errStream = new ByteArrayOutputStream();

    assertEquals(Launcher.main(outStream, errStream, toolName, "--help"),
         ResultCode.SUCCESS);

    assertTrue(outStream.size() > 0);
    assertTrue(errStream.size() == 0);
  }



  /**
   * Retrieves a set of valid tool names.
   *
   * @return  A set of valid tool names.
   */
  @DataProvider(name="valid-tool-names")
  public Object[][] getValidToolNames()
  {
    return new Object[][]
    {
      new Object[] { "authrate" },
      new Object[] { "base64" },
      new Object[] { "collect-support-data" },
      new Object[] { "deliver-one-time-password" },
      new Object[] { "deliver-password-reset-token" },
      new Object[] { "dump-dns" },
      new Object[] { "generate-schema-from-source" },
      new Object[] { "generate-source-from-schema" },
      new Object[] { "generate-totp-shared-secret" },
      new Object[] { "identify-references-to-missing-entries" },
      new Object[] { "identify-unique-attribute-conflicts" },
      new Object[] { "in-memory-directory-server" },
      new Object[] { "indent-ldap-filter" },
      new Object[] { "ldapcompare" },
      new Object[] { "ldapdelete" },
      new Object[] { "ldapmodify" },
      new Object[] { "ldapsearch" },
      new Object[] { "ldap-debugger" },
      new Object[] { "manage-account" },
      new Object[] { "manage-certificates" },
      new Object[] { "modrate" },
      new Object[] { "move-subtree" },
      new Object[] { "register-yubikey-otp-device" },
      new Object[] { "searchrate" },
      new Object[] { "search-and-mod-rate" },
      new Object[] { "split-ldif" },
      new Object[] { "subtree-accessibility" },
      new Object[] { "summarize-access-log" },
      new Object[] { "tls-cipher-suite-selector" },
      new Object[] { "transform-ldif" },
      new Object[] { "validate-ldif" },
      new Object[] { "version" }
    };
  }



  /**
   * Provides a test case for launching the tool with an invalid tool name.
   */
  @Test()
  public void testInvalidToolName()
  {
    final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    final ByteArrayOutputStream errStream = new ByteArrayOutputStream();

    assertFalse(Launcher.main(outStream, errStream, "invalid", "--help").equals(
         ResultCode.SUCCESS));

    assertTrue(outStream.size() == 0);
    assertTrue(errStream.size() > 0);
  }



  /**
   * Tests to ensure that it's possible to get an instance of all of the
   * defined tools.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test()
  public void testGetToolInstance()
         throws Exception
  {
    for (final Class<?> c : Launcher.getToolClasses())
    {
      final ByteArrayOutputStream out = new ByteArrayOutputStream();
      final ByteArrayOutputStream err = new ByteArrayOutputStream();

      final CommandLineTool tool = Launcher.getToolInstance(c, out, err);
      assertNotNull(tool);
    }
  }
}
