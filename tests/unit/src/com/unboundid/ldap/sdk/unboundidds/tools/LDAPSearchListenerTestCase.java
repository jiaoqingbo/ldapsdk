/*
 * Copyright 2017-2020 Ping Identity Corporation
 * All Rights Reserved.
 */
/*
 * Copyright 2017-2020 Ping Identity Corporation
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
 * Copyright (C) 2017-2020 Ping Identity Corporation
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
package com.unboundid.ldap.sdk.unboundidds.tools;



import org.testng.annotations.Test;

import com.unboundid.ldap.sdk.Entry;
import com.unboundid.ldap.sdk.LDAPSDKTestCase;
import com.unboundid.ldap.sdk.SearchResultEntry;
import com.unboundid.ldap.sdk.SearchResultReference;



/**
 * This class provides a set of test cases for the {@code LDAPSearchListener}
 * class.
 */
public final class LDAPSearchListenerTestCase
       extends LDAPSDKTestCase
{
  /**
   * Tests the behavior of the {@code searchEntryReturned} method.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test()
  public void testSearchEntryReturned()
         throws Exception
  {
    final LDAPSearch ldapSearch = new LDAPSearch(null, null);

    final LDAPSearchListener listener =
         new LDAPSearchListener(new LDIFLDAPSearchOutputHandler(ldapSearch,
              Integer.MAX_VALUE), null);

    listener.searchEntryReturned(new SearchResultEntry(new Entry(
         "dn: dc=example,dc=com",
         "objectClass: top",
         "objectClass: domain",
         "dc: example")));

    listener.searchEntryReturned(new SearchResultEntry(new Entry(
         "dn: dc=example,dc=com",
         "objectClass: top",
         "objectClass: domain",
         "dc: example")));
  }



  /**
   * Tests the behavior of the {@code searchReferenceReturned} method.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test()
  public void testSearchReferenceReturned()
         throws Exception
  {
    final LDAPSearch ldapSearch = new LDAPSearch(null, null);

    final LDAPSearchListener listener =
         new LDAPSearchListener(new LDIFLDAPSearchOutputHandler(ldapSearch,
              Integer.MAX_VALUE), null);

    final String[] referralURLs =
    {
      "ldap://ds1.example.com:389/dc=example,dc=com",
      "ldap://ds2.example.com:389/dc=example,dc=com"
    };

    listener.searchReferenceReturned(
         new SearchResultReference(referralURLs, null));

    listener.searchReferenceReturned(
         new SearchResultReference(referralURLs, null));
  }
}
