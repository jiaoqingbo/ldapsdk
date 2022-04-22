/*
 * Copyright 2008-2022 Ping Identity Corporation
 * All Rights Reserved.
 */
/*
 * Copyright 2008-2022 Ping Identity Corporation
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
 * Copyright (C) 2008-2022 Ping Identity Corporation
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
package com.unboundid.ldap.sdk;



import java.util.ArrayList;
import java.util.List;



/**
 * This class provides a simple unsolicited notification handler that may be
 * used for testing purposes.
 */
public class TestUnsolicitedNotificationHandler
       implements UnsolicitedNotificationHandler
{
  // The list of unsolicited notifications that were provided.
  private final List<ExtendedResult> notifications;



  /**
   * Creates a new instance of this test notification handler.
   */
  public TestUnsolicitedNotificationHandler()
  {
    notifications = new ArrayList<>();
  }



  /**
   * Retrieves the number of times this unsolicited notification handler has
   * been invoked.
   *
   * @return  The number of times this unsolicited notification handler has been
   *          invoked.
   */
  public synchronized int getNotificationCount()
  {
    return notifications.size();
  }



  /**
   * Retrieves a list of the unsolicited notifications that were returned.
   *
   * @return  A list of the unsolicited notifications that were returned.
   */
  public synchronized List<ExtendedResult> getNotifications()
  {
    return new ArrayList<>(notifications);
  }



  /**
   * {@inheritDoc}
   */
  @Override()
  public synchronized void handleUnsolicitedNotification(
              final LDAPConnection connection,
              final ExtendedResult notification)
  {
    notifications.add(notification);
  }
}

