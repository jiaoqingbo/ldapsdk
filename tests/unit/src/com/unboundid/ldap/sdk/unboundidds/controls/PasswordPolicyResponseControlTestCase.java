/*
 * Copyright 2007-2022 Ping Identity Corporation
 * All Rights Reserved.
 */
/*
 * Copyright 2007-2022 Ping Identity Corporation
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
 * Copyright (C) 2007-2022 Ping Identity Corporation
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
package com.unboundid.ldap.sdk.unboundidds.controls;



import org.testng.annotations.Test;

import com.unboundid.asn1.ASN1Element;
import com.unboundid.asn1.ASN1Enumerated;
import com.unboundid.asn1.ASN1Integer;
import com.unboundid.asn1.ASN1OctetString;
import com.unboundid.asn1.ASN1Sequence;
import com.unboundid.ldap.sdk.Control;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.LDAPResult;
import com.unboundid.ldap.sdk.LDAPSDKTestCase;
import com.unboundid.ldap.sdk.ResultCode;
import com.unboundid.util.Base64;
import com.unboundid.util.json.JSONField;
import com.unboundid.util.json.JSONObject;



/**
 * This class provides a set of test cases for the PasswordPolicyResponseControl
 * class.
 */
public class PasswordPolicyResponseControlTestCase
       extends LDAPSDKTestCase
{
  /**
   * Tests the first constructor.
   */
  @Test()
  public void testConstructor1()
  {
    new PasswordPolicyResponseControl();
  }



  /**
   * Tests the second constructor with all element types.
   */
  @Test()
  public void testConstructor2AllTypes()
  {
    PasswordPolicyResponseControl c =
         new PasswordPolicyResponseControl(
                  PasswordPolicyWarningType.GRACE_LOGINS_REMAINING, 2,
                  PasswordPolicyErrorType.PASSWORD_EXPIRED);

    assertNotNull(c.getWarningType());
    assertEquals(c.getWarningType(),
                 PasswordPolicyWarningType.GRACE_LOGINS_REMAINING);
    assertEquals(c.getWarningValue(), 2);

    assertNotNull(c.getErrorType());
    assertEquals(c.getErrorType(),
                 PasswordPolicyErrorType.PASSWORD_EXPIRED);

    assertNotNull(c.getControlName());
    assertNotNull(c.toString());
  }



  /**
   * Tests the second constructor with only a warning element.
   */
  @Test()
  public void testConstructor2OnlyWarning()
  {
    PasswordPolicyResponseControl c =
         new PasswordPolicyResponseControl(
                  PasswordPolicyWarningType.TIME_BEFORE_EXPIRATION, 12345,
                  null);

    assertNotNull(c.getWarningType());
    assertEquals(c.getWarningType(),
                 PasswordPolicyWarningType.TIME_BEFORE_EXPIRATION);
    assertEquals(c.getWarningValue(), 12345);

    assertNull(c.getErrorType());

    assertNotNull(c.getControlName());
    assertNotNull(c.toString());
  }



  /**
   * Tests the second constructor with only an error element.
   */
  @Test()
  public void testConstructor2OnlyError()
  {
    PasswordPolicyResponseControl c =
         new PasswordPolicyResponseControl(null, -1,
                  PasswordPolicyErrorType.ACCOUNT_LOCKED);

    assertNull(c.getWarningType());
    assertEquals(c.getWarningValue(), -1);

    assertNotNull(c.getErrorType());
    assertEquals(c.getErrorType(), PasswordPolicyErrorType.ACCOUNT_LOCKED);

    assertNotNull(c.getControlName());
    assertNotNull(c.toString());
  }



  /**
   * Tests the second constructor with all no types.
   */
  @Test()
  public void testConstructor2NoTypes()
  {
    PasswordPolicyResponseControl c =
         new PasswordPolicyResponseControl(null, -1, null);

    assertNull(c.getWarningType());
    assertEquals(c.getWarningValue(), -1);

    assertNull(c.getErrorType());

    assertNotNull(c.getControlName());
    assertNotNull(c.toString());
  }



  /**
   * Tests the third constructor with all element types.
   */
  @Test()
  public void testConstructor3AllTypes()
  {
    PasswordPolicyResponseControl c =
         new PasswordPolicyResponseControl(
                  PasswordPolicyWarningType.GRACE_LOGINS_REMAINING, 2,
                  PasswordPolicyErrorType.PASSWORD_EXPIRED, false);

    assertNotNull(c.getWarningType());
    assertEquals(c.getWarningType(),
                 PasswordPolicyWarningType.GRACE_LOGINS_REMAINING);
    assertEquals(c.getWarningValue(), 2);

    assertNotNull(c.getErrorType());
    assertEquals(c.getErrorType(),
                 PasswordPolicyErrorType.PASSWORD_EXPIRED);

    assertNotNull(c.getControlName());
    assertNotNull(c.toString());
  }



  /**
   * Tests the third constructor with only a warning element.
   */
  @Test()
  public void testConstructor3OnlyWarning()
  {
    PasswordPolicyResponseControl c =
         new PasswordPolicyResponseControl(
                  PasswordPolicyWarningType.TIME_BEFORE_EXPIRATION, 12345, null,
                  false);

    assertNotNull(c.getWarningType());
    assertEquals(c.getWarningType(),
                 PasswordPolicyWarningType.TIME_BEFORE_EXPIRATION);
    assertEquals(c.getWarningValue(), 12345);

    assertNull(c.getErrorType());

    assertNotNull(c.getControlName());
    assertNotNull(c.toString());
  }



  /**
   * Tests the third constructor with only an error element.
   */
  @Test()
  public void testConstructor3OnlyError()
  {
    PasswordPolicyResponseControl c =
         new PasswordPolicyResponseControl(null, -1,
                  PasswordPolicyErrorType.ACCOUNT_LOCKED, false);

    assertNull(c.getWarningType());
    assertEquals(c.getWarningValue(), -1);

    assertNotNull(c.getErrorType());
    assertEquals(c.getErrorType(), PasswordPolicyErrorType.ACCOUNT_LOCKED);

    assertNotNull(c.getControlName());
    assertNotNull(c.toString());
  }



  /**
   * Tests the third constructor with all no types.
   */
  @Test()
  public void testConstructor3NoTypes()
  {
    PasswordPolicyResponseControl c =
         new PasswordPolicyResponseControl(null, -1, null, true);

    assertNull(c.getWarningType());
    assertEquals(c.getWarningValue(), -1);

    assertNull(c.getErrorType());

    assertNotNull(c.getControlName());
    assertNotNull(c.toString());
  }



  /**
   * Tests the {@code decodeControl} method with a valid set of information
   * with all element types.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test()
  public void testDecodeControlAllTypes()
         throws Exception
  {
    ASN1Element[] valueElements =
    {
      new ASN1Element((byte) 0xA0,
                      new ASN1Integer((byte) 0x80, 12345).encode()),
      new ASN1Enumerated((byte) 0x81, 0)
    };

    ASN1OctetString value =
         new ASN1OctetString(new ASN1Sequence(valueElements).encode());

    PasswordPolicyResponseControl c =
         new PasswordPolicyResponseControl().decodeControl(
                  "1.3.6.1.4.1.42.2.27.8.5.1", false, value);

    assertNotNull(c.getWarningType());
    assertEquals(c.getWarningType(),
                 PasswordPolicyWarningType.TIME_BEFORE_EXPIRATION);
    assertEquals(c.getWarningValue(), 12345);

    assertNotNull(c.getErrorType());
    assertEquals(c.getErrorType(),
                 PasswordPolicyErrorType.PASSWORD_EXPIRED);

    assertNotNull(c.getControlName());
    assertNotNull(c.toString());
  }



  /**
   * Tests the {@code decodeControl} method with a valid set of information
   * with only a warning element.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test()
  public void testDecodeControlOnlyWarning()
         throws Exception
  {
    ASN1Element[] valueElements =
    {
      new ASN1Element((byte) 0xA0,
                      new ASN1Integer((byte) 0x81, 2).encode())
    };

    ASN1OctetString value =
         new ASN1OctetString(new ASN1Sequence(valueElements).encode());

    PasswordPolicyResponseControl c =
         new PasswordPolicyResponseControl().decodeControl(
                  "1.3.6.1.4.1.42.2.27.8.5.1", false, value);

    assertNotNull(c.getWarningType());
    assertEquals(c.getWarningType(),
                 PasswordPolicyWarningType.GRACE_LOGINS_REMAINING);
    assertEquals(c.getWarningValue(), 2);

    assertNull(c.getErrorType());

    assertNotNull(c.getControlName());
    assertNotNull(c.toString());
  }



  /**
   * Tests the {@code decodeControl} method with a valid set of information
   * with only an error element.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test()
  public void testDecodeControlOnlyError()
         throws Exception
  {
    ASN1Element[] valueElements =
    {
      new ASN1Enumerated((byte) 0x81, 1)
    };

    ASN1OctetString value =
         new ASN1OctetString(new ASN1Sequence(valueElements).encode());

    PasswordPolicyResponseControl c =
         new PasswordPolicyResponseControl().decodeControl(
                  "1.3.6.1.4.1.42.2.27.8.5.1", false, value);

    assertNull(c.getWarningType());
    assertEquals(c.getWarningValue(), -1);

    assertNotNull(c.getErrorType());
    assertEquals(c.getErrorType(),
                 PasswordPolicyErrorType.ACCOUNT_LOCKED);

    assertNotNull(c.getControlName());
    assertNotNull(c.toString());
  }



  /**
   * Tests the {@code decodeControl} method with a valid set of information
   * with no element types.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test()
  public void testDecodeControlNoTypes()
         throws Exception
  {
    ASN1Element[] valueElements = new ASN1Element[0];

    ASN1OctetString value =
         new ASN1OctetString(new ASN1Sequence(valueElements).encode());

    PasswordPolicyResponseControl c =
         new PasswordPolicyResponseControl().decodeControl(
                  "1.3.6.1.4.1.42.2.27.8.5.1", false, value);

    assertNull(c.getWarningType());
    assertEquals(c.getWarningValue(), -1);

    assertNull(c.getErrorType());

    assertNotNull(c.getControlName());
    assertNotNull(c.toString());
  }



  /**
   * Tests the {@code decodeControl} method with a {@code null} value.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test(expectedExceptions = { LDAPException.class })
  public void testDecodeControlNullValue()
         throws Exception
  {
    new PasswordPolicyResponseControl().decodeControl(
             "1.3.6.1.4.1.42.2.27.8.5.1", false, null);
  }



  /**
   * Tests the {@code decodeControl} method with a value that can't be decoded
   * as a sequence.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test(expectedExceptions = { LDAPException.class })
  public void testDecodeControlValueNotSequence()
         throws Exception
  {
    new PasswordPolicyResponseControl().decodeControl(
             "1.3.6.1.4.1.42.2.27.8.5.1", false,
             new ASN1OctetString(new ASN1Integer(0).encode()));
  }



  /**
   * Tests the {@code decodeControl} method with a value sequence with too many
   * elements.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test(expectedExceptions = { LDAPException.class })
  public void testDecodeControlValueSequenceTooManyElements()
         throws Exception
  {
    ASN1Element[] valueElements =
    {
      new ASN1Element((byte) 0xA0,
                      new ASN1Integer((byte) 0x80, 12345).encode()),
      new ASN1Enumerated((byte) 0x81, 0),
      new ASN1Integer(0)
    };

    ASN1OctetString value =
         new ASN1OctetString(new ASN1Sequence(valueElements).encode());

    new PasswordPolicyResponseControl().decodeControl(
             "1.3.6.1.4.1.42.2.27.8.5.1", false, value);
  }



  /**
   * Tests the {@code decodeControl} method with a value sequence with an
   * invalid element type.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test(expectedExceptions = { LDAPException.class })
  public void testDecodeControlValueSequenceInvalidElementType()
         throws Exception
  {
    ASN1Element[] valueElements =
    {
      new ASN1Integer(5)
    };

    ASN1OctetString value =
         new ASN1OctetString(new ASN1Sequence(valueElements).encode());

    new PasswordPolicyResponseControl().decodeControl(
             "1.3.6.1.4.1.42.2.27.8.5.1", false, value);
  }



  /**
   * Tests the {@code decodeControl} method with a value sequence with multiple
   * warning elements.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test(expectedExceptions = { LDAPException.class })
  public void testDecodeControlValueSequenceMultipleWarningElements()
         throws Exception
  {
    ASN1Element[] valueElements =
    {
      new ASN1Element((byte) 0xA0,
                      new ASN1Integer((byte) 0x80, 12345).encode()),
      new ASN1Element((byte) 0xA0,
                      new ASN1Integer((byte) 0x81, 2).encode()),
    };

    ASN1OctetString value =
         new ASN1OctetString(new ASN1Sequence(valueElements).encode());

    new PasswordPolicyResponseControl().decodeControl(
             "1.3.6.1.4.1.42.2.27.8.5.1", false, value);
  }



  /**
   * Tests the {@code decodeControl} method with a value sequence multiple value
   * elements.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test(expectedExceptions = { LDAPException.class })
  public void testDecodeControlValueSequenceMultipleValueElements()
         throws Exception
  {
    ASN1Element[] valueElements =
    {
      new ASN1Enumerated((byte) 0x81, 0),
      new ASN1Enumerated((byte) 0x81, 1),
    };

    ASN1OctetString value =
         new ASN1OctetString(new ASN1Sequence(valueElements).encode());

    new PasswordPolicyResponseControl().decodeControl(
             "1.3.6.1.4.1.42.2.27.8.5.1", false, value);
  }



  /**
   * Tests the {@code decodeControl} method with a value sequence with an
   * invalid warning type.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test(expectedExceptions = { LDAPException.class })
  public void testDecodeControlValueSequenceInvalidWarningType()
         throws Exception
  {
    ASN1Element[] valueElements =
    {
      new ASN1Element((byte) 0xA0,
                      new ASN1Integer((byte) 0x82, 12345).encode())
    };

    ASN1OctetString value =
         new ASN1OctetString(new ASN1Sequence(valueElements).encode());

    new PasswordPolicyResponseControl().decodeControl(
             "1.3.6.1.4.1.42.2.27.8.5.1", false, value);
  }



  /**
   * Tests the {@code decodeControl} method with a value sequence with a
   * warning element that can't be decoded.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test(expectedExceptions = { LDAPException.class })
  public void testDecodeControlValueSequenceCannotDecodeWarningType()
         throws Exception
  {
    ASN1Element[] valueElements =
    {
      new ASN1Element((byte) 0xA0, new ASN1OctetString().encode())
    };

    ASN1OctetString value =
         new ASN1OctetString(new ASN1Sequence(valueElements).encode());

    new PasswordPolicyResponseControl().decodeControl(
             "1.3.6.1.4.1.42.2.27.8.5.1", false, value);
  }



  /**
   * Tests the {@code decodeControl} method with a value sequence with an
   * invalid error type.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test(expectedExceptions = { LDAPException.class })
  public void testDecodeControlValueSequenceInvalidErrorType()
         throws Exception
  {
    ASN1Element[] valueElements =
    {
      new ASN1Enumerated((byte) 0x81, 999)
    };

    ASN1OctetString value =
         new ASN1OctetString(new ASN1Sequence(valueElements).encode());

    new PasswordPolicyResponseControl().decodeControl(
             "1.3.6.1.4.1.42.2.27.8.5.1", false, value);
  }



  /**
   * Tests the {@code decodeControl} method with a value sequence with an error
   * element that can't be decoded.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test(expectedExceptions = { LDAPException.class })
  public void testDecodeControlValueSequenceCannotDecodeErrorType()
         throws Exception
  {
    ASN1Element[] valueElements =
    {
      new ASN1Element((byte) 0x81)
    };

    ASN1OctetString value =
         new ASN1OctetString(new ASN1Sequence(valueElements).encode());

    new PasswordPolicyResponseControl().decodeControl(
             "1.3.6.1.4.1.42.2.27.8.5.1", false, value);
  }



  /**
   * Tests the {@code get} method with a result that does not contain a password
   * policy response control.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test()
  public void testGetMissing()
         throws Exception
  {
    final Control[] controls = new Control[0];

    final LDAPResult r = new LDAPResult(1, ResultCode.SUCCESS);

    final PasswordPolicyResponseControl c =
         PasswordPolicyResponseControl.get(r);
    assertNull(c);
  }



  /**
   * Tests the {@code get} method with a result that contains a response control
   * that is already of the appropriate type.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test()
  public void testGetValidCorrectType()
         throws Exception
  {
    final Control[] controls =
    {
      new PasswordPolicyResponseControl(
           PasswordPolicyWarningType.TIME_BEFORE_EXPIRATION, 1234, null)
    };

    final LDAPResult r = new LDAPResult(1, ResultCode.SUCCESS, null, null,
         null, controls);

    final PasswordPolicyResponseControl c =
         PasswordPolicyResponseControl.get(r);
    assertNotNull(c);

    assertEquals(c.getWarningType(),
         PasswordPolicyWarningType.TIME_BEFORE_EXPIRATION);

    assertEquals(c.getWarningValue(), 1234);

    assertNull(c.getErrorType());
  }



  /**
   * Tests the {@code get} method with a result that contains a response control
   * that is a generic control that can be parsed as a password policy response
   * control.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test()
  public void testGetValidGenericType()
         throws Exception
  {
    final Control tmp = new PasswordPolicyResponseControl(
         PasswordPolicyWarningType.TIME_BEFORE_EXPIRATION, 1234, null);

    final Control[] controls =
    {
      new Control(tmp.getOID(), tmp.isCritical(), tmp.getValue())
    };

    final LDAPResult r = new LDAPResult(1, ResultCode.SUCCESS, null, null,
         null, controls);

    final PasswordPolicyResponseControl c =
         PasswordPolicyResponseControl.get(r);
    assertNotNull(c);

    assertEquals(c.getWarningType(),
         PasswordPolicyWarningType.TIME_BEFORE_EXPIRATION);

    assertEquals(c.getWarningValue(), 1234);

    assertNull(c.getErrorType());
  }



  /**
   * Tests the {@code get} method with a result that contains a response control
   * that is a generic control that cannot be parsed as a password policy
   * response control.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test(expectedExceptions = { LDAPException.class })
  public void testGetInvalidGenericType()
         throws Exception
  {
    final Control[] controls =
    {
      new Control(PasswordPolicyResponseControl.PASSWORD_POLICY_RESPONSE_OID,
           false, null)
    };

    final LDAPResult r = new LDAPResult(1, ResultCode.SUCCESS, null, null,
         null, controls);

    PasswordPolicyResponseControl.get(r);
  }



  /**
   * Tests the behavior when trying to encode and decode the control to and
   * from a JSON object when the value has neither a warning type nor an error
   * type.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test()
  public void testToJSONControlNeitherWarningTypeNorErrorType()
          throws Exception
  {
    final PasswordPolicyResponseControl c =
         new PasswordPolicyResponseControl(null, -1, null);

    final JSONObject controlObject = c.toJSONControl();

    assertNotNull(controlObject);
    assertEquals(controlObject.getFields().size(), 4);

    assertEquals(controlObject.getFieldAsString("oid"), c.getOID());

    assertNotNull(controlObject.getFieldAsString("control-name"));
    assertFalse(controlObject.getFieldAsString("control-name").isEmpty());
    assertFalse(controlObject.getFieldAsString("control-name").equals(
         controlObject.getFieldAsString("oid")));

    assertEquals(controlObject.getFieldAsBoolean("criticality"),
         Boolean.FALSE);

    assertFalse(controlObject.hasField("value-base64"));

    assertEquals(controlObject.getFieldAsObject("value-json"),
         JSONObject.EMPTY_OBJECT);


    PasswordPolicyResponseControl decodedControl =
         PasswordPolicyResponseControl.decodeJSONControl(controlObject, true);
    assertNotNull(decodedControl);

    assertEquals(decodedControl.getOID(), c.getOID());

    assertFalse(decodedControl.isCritical());

    assertNotNull(decodedControl.getValue());

    assertNull(decodedControl.getWarningType());

    assertEquals(decodedControl.getWarningValue(), -1);

    assertNull(decodedControl.getErrorType());


    decodedControl =
         (PasswordPolicyResponseControl)
         Control.decodeJSONControl(controlObject, true, false);
    assertNotNull(decodedControl);

    assertEquals(decodedControl.getOID(), c.getOID());

    assertFalse(decodedControl.isCritical());

    assertNotNull(decodedControl.getValue());

    assertNull(decodedControl.getWarningType());

    assertEquals(decodedControl.getWarningValue(), -1);

    assertNull(decodedControl.getErrorType());
  }



  /**
   * Tests the behavior when trying to encode and decode the control to and
   * from a JSON object when the value has both a warning type and an error
   * type.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test()
  public void testToJSONControlBothWarningTypeAndErrorType()
          throws Exception
  {
    final PasswordPolicyResponseControl c =
         new PasswordPolicyResponseControl(
              PasswordPolicyWarningType.TIME_BEFORE_EXPIRATION, 1234,
              PasswordPolicyErrorType.ACCOUNT_LOCKED);

    final JSONObject controlObject = c.toJSONControl();

    assertNotNull(controlObject);
    assertEquals(controlObject.getFields().size(), 4);

    assertEquals(controlObject.getFieldAsString("oid"), c.getOID());

    assertNotNull(controlObject.getFieldAsString("control-name"));
    assertFalse(controlObject.getFieldAsString("control-name").isEmpty());
    assertFalse(controlObject.getFieldAsString("control-name").equals(
         controlObject.getFieldAsString("oid")));

    assertEquals(controlObject.getFieldAsBoolean("criticality"),
         Boolean.FALSE);

    assertFalse(controlObject.hasField("value-base64"));

    assertEquals(controlObject.getFieldAsObject("value-json"),
         new JSONObject(
              new JSONField("warning", new JSONObject(
                   new JSONField("seconds-until-expiration", 1234))),
              new JSONField("error-type", "account-locked")));


    PasswordPolicyResponseControl decodedControl =
         PasswordPolicyResponseControl.decodeJSONControl(controlObject, true);
    assertNotNull(decodedControl);

    assertEquals(decodedControl.getOID(), c.getOID());

    assertFalse(decodedControl.isCritical());

    assertNotNull(decodedControl.getValue());

    assertEquals(decodedControl.getWarningType(),
         PasswordPolicyWarningType.TIME_BEFORE_EXPIRATION);

    assertEquals(decodedControl.getWarningValue(), 1234);

    assertEquals(decodedControl.getErrorType(),
         PasswordPolicyErrorType.ACCOUNT_LOCKED);


    decodedControl =
         (PasswordPolicyResponseControl)
         Control.decodeJSONControl(controlObject, true, false);
    assertNotNull(decodedControl);

    assertEquals(decodedControl.getOID(), c.getOID());

    assertFalse(decodedControl.isCritical());

    assertNotNull(decodedControl.getValue());

    assertEquals(decodedControl.getWarningType(),
         PasswordPolicyWarningType.TIME_BEFORE_EXPIRATION);

    assertEquals(decodedControl.getWarningValue(), 1234);

    assertEquals(decodedControl.getErrorType(),
         PasswordPolicyErrorType.ACCOUNT_LOCKED);
  }



  /**
   * Tests the behavior when trying to encode and decode the control with all
   * supported warning types.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test()
  public void testToJSONControlSupportedWarningTypes()
          throws Exception
  {
    PasswordPolicyResponseControl c = new PasswordPolicyResponseControl(
         PasswordPolicyWarningType.TIME_BEFORE_EXPIRATION, 1234, null);

    JSONObject controlObject = c.toJSONControl();

    assertNotNull(controlObject);
    assertEquals(controlObject.getFields().size(), 4);

    assertEquals(controlObject.getFieldAsString("oid"), c.getOID());

    assertNotNull(controlObject.getFieldAsString("control-name"));
    assertFalse(controlObject.getFieldAsString("control-name").isEmpty());
    assertFalse(controlObject.getFieldAsString("control-name").equals(
         controlObject.getFieldAsString("oid")));

    assertEquals(controlObject.getFieldAsBoolean("criticality"),
         Boolean.FALSE);

    assertFalse(controlObject.hasField("value-base64"));

    assertEquals(controlObject.getFieldAsObject("value-json"),
         new JSONObject(
              new JSONField("warning", new JSONObject(
                   new JSONField("seconds-until-expiration", 1234)))));


    PasswordPolicyResponseControl decodedControl =
         PasswordPolicyResponseControl.decodeJSONControl(controlObject, true);
    assertNotNull(decodedControl);

    assertEquals(decodedControl.getOID(), c.getOID());

    assertFalse(decodedControl.isCritical());

    assertNotNull(decodedControl.getValue());

    assertEquals(decodedControl.getWarningType(),
         PasswordPolicyWarningType.TIME_BEFORE_EXPIRATION);

    assertEquals(decodedControl.getWarningValue(), 1234);

    assertNull(decodedControl.getErrorType());


    c = new PasswordPolicyResponseControl(
         PasswordPolicyWarningType.GRACE_LOGINS_REMAINING, 5678, null);

    controlObject = c.toJSONControl();

    assertNotNull(controlObject);
    assertEquals(controlObject.getFields().size(), 4);

    assertEquals(controlObject.getFieldAsString("oid"), c.getOID());

    assertNotNull(controlObject.getFieldAsString("control-name"));
    assertFalse(controlObject.getFieldAsString("control-name").isEmpty());
    assertFalse(controlObject.getFieldAsString("control-name").equals(
         controlObject.getFieldAsString("oid")));

    assertEquals(controlObject.getFieldAsBoolean("criticality"),
         Boolean.FALSE);

    assertFalse(controlObject.hasField("value-base64"));

    assertEquals(controlObject.getFieldAsObject("value-json"),
         new JSONObject(
              new JSONField("warning", new JSONObject(
                   new JSONField("grace-logins-remaining", 5678)))));


    decodedControl =
         PasswordPolicyResponseControl.decodeJSONControl(controlObject, true);
    assertNotNull(decodedControl);

    assertEquals(decodedControl.getOID(), c.getOID());

    assertFalse(decodedControl.isCritical());

    assertNotNull(decodedControl.getValue());

    assertEquals(decodedControl.getWarningType(),
         PasswordPolicyWarningType.GRACE_LOGINS_REMAINING);

    assertEquals(decodedControl.getWarningValue(), 5678);
  }



  /**
   * Tests the behavior when trying to encode and decode the control with all
   * supported error types.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test()
  public void testToJSONControlSupportedErrorTypes()
          throws Exception
  {
    PasswordPolicyResponseControl c = new PasswordPolicyResponseControl(null,
         -1, PasswordPolicyErrorType.PASSWORD_EXPIRED);

    JSONObject controlObject = c.toJSONControl();

    assertNotNull(controlObject);
    assertEquals(controlObject.getFields().size(), 4);

    assertEquals(controlObject.getFieldAsString("oid"), c.getOID());

    assertNotNull(controlObject.getFieldAsString("control-name"));
    assertFalse(controlObject.getFieldAsString("control-name").isEmpty());
    assertFalse(controlObject.getFieldAsString("control-name").equals(
         controlObject.getFieldAsString("oid")));

    assertEquals(controlObject.getFieldAsBoolean("criticality"),
         Boolean.FALSE);

    assertFalse(controlObject.hasField("value-base64"));

    assertEquals(controlObject.getFieldAsObject("value-json"),
         new JSONObject(
              new JSONField("error-type", "password-expired")));


    PasswordPolicyResponseControl decodedControl =
         PasswordPolicyResponseControl.decodeJSONControl(controlObject, true);
    assertNotNull(decodedControl);

    assertEquals(decodedControl.getOID(), c.getOID());

    assertFalse(decodedControl.isCritical());

    assertNotNull(decodedControl.getValue());

    assertNull(decodedControl.getWarningType());

    assertEquals(decodedControl.getWarningValue(), -1);

    assertEquals(decodedControl.getErrorType(),
         PasswordPolicyErrorType.PASSWORD_EXPIRED);


    c = new PasswordPolicyResponseControl(null, -1,
         PasswordPolicyErrorType.ACCOUNT_LOCKED);

    controlObject = c.toJSONControl();

    assertNotNull(controlObject);
    assertEquals(controlObject.getFields().size(), 4);

    assertEquals(controlObject.getFieldAsString("oid"), c.getOID());

    assertNotNull(controlObject.getFieldAsString("control-name"));
    assertFalse(controlObject.getFieldAsString("control-name").isEmpty());
    assertFalse(controlObject.getFieldAsString("control-name").equals(
         controlObject.getFieldAsString("oid")));

    assertEquals(controlObject.getFieldAsBoolean("criticality"),
         Boolean.FALSE);

    assertFalse(controlObject.hasField("value-base64"));

    assertEquals(controlObject.getFieldAsObject("value-json"),
         new JSONObject(
              new JSONField("error-type", "account-locked")));


    decodedControl =
         PasswordPolicyResponseControl.decodeJSONControl(controlObject, true);
    assertNotNull(decodedControl);

    assertEquals(decodedControl.getOID(), c.getOID());

    assertFalse(decodedControl.isCritical());

    assertNotNull(decodedControl.getValue());

    assertNull(decodedControl.getWarningType());

    assertEquals(decodedControl.getWarningValue(), -1);

    assertEquals(decodedControl.getErrorType(),
         PasswordPolicyErrorType.ACCOUNT_LOCKED);


    c = new PasswordPolicyResponseControl(null, -1,
         PasswordPolicyErrorType.CHANGE_AFTER_RESET);

    controlObject = c.toJSONControl();

    assertNotNull(controlObject);
    assertEquals(controlObject.getFields().size(), 4);

    assertEquals(controlObject.getFieldAsString("oid"), c.getOID());

    assertNotNull(controlObject.getFieldAsString("control-name"));
    assertFalse(controlObject.getFieldAsString("control-name").isEmpty());
    assertFalse(controlObject.getFieldAsString("control-name").equals(
         controlObject.getFieldAsString("oid")));

    assertEquals(controlObject.getFieldAsBoolean("criticality"),
         Boolean.FALSE);

    assertFalse(controlObject.hasField("value-base64"));

    assertEquals(controlObject.getFieldAsObject("value-json"),
         new JSONObject(
              new JSONField("error-type", "change-after-reset")));


    decodedControl =
         PasswordPolicyResponseControl.decodeJSONControl(controlObject, true);
    assertNotNull(decodedControl);

    assertEquals(decodedControl.getOID(), c.getOID());

    assertFalse(decodedControl.isCritical());

    assertNotNull(decodedControl.getValue());

    assertNull(decodedControl.getWarningType());

    assertEquals(decodedControl.getWarningValue(), -1);

    assertEquals(decodedControl.getErrorType(),
         PasswordPolicyErrorType.CHANGE_AFTER_RESET);


    c = new PasswordPolicyResponseControl(null, -1,
         PasswordPolicyErrorType.PASSWORD_MOD_NOT_ALLOWED);

    controlObject = c.toJSONControl();

    assertNotNull(controlObject);
    assertEquals(controlObject.getFields().size(), 4);

    assertEquals(controlObject.getFieldAsString("oid"), c.getOID());

    assertNotNull(controlObject.getFieldAsString("control-name"));
    assertFalse(controlObject.getFieldAsString("control-name").isEmpty());
    assertFalse(controlObject.getFieldAsString("control-name").equals(
         controlObject.getFieldAsString("oid")));

    assertEquals(controlObject.getFieldAsBoolean("criticality"),
         Boolean.FALSE);

    assertFalse(controlObject.hasField("value-base64"));

    assertEquals(controlObject.getFieldAsObject("value-json"),
         new JSONObject(
              new JSONField("error-type", "password-mod-not-allowed")));


    decodedControl =
         PasswordPolicyResponseControl.decodeJSONControl(controlObject, true);
    assertNotNull(decodedControl);

    assertEquals(decodedControl.getOID(), c.getOID());

    assertFalse(decodedControl.isCritical());

    assertNotNull(decodedControl.getValue());

    assertNull(decodedControl.getWarningType());

    assertEquals(decodedControl.getWarningValue(), -1);

    assertEquals(decodedControl.getErrorType(),
         PasswordPolicyErrorType.PASSWORD_MOD_NOT_ALLOWED);


    c = new PasswordPolicyResponseControl(null, -1,
         PasswordPolicyErrorType.MUST_SUPPLY_OLD_PASSWORD);

    controlObject = c.toJSONControl();

    assertNotNull(controlObject);
    assertEquals(controlObject.getFields().size(), 4);

    assertEquals(controlObject.getFieldAsString("oid"), c.getOID());

    assertNotNull(controlObject.getFieldAsString("control-name"));
    assertFalse(controlObject.getFieldAsString("control-name").isEmpty());
    assertFalse(controlObject.getFieldAsString("control-name").equals(
         controlObject.getFieldAsString("oid")));

    assertEquals(controlObject.getFieldAsBoolean("criticality"),
         Boolean.FALSE);

    assertFalse(controlObject.hasField("value-base64"));

    assertEquals(controlObject.getFieldAsObject("value-json"),
         new JSONObject(
              new JSONField("error-type", "must-supply-old-password")));


    decodedControl =
         PasswordPolicyResponseControl.decodeJSONControl(controlObject, true);
    assertNotNull(decodedControl);

    assertEquals(decodedControl.getOID(), c.getOID());

    assertFalse(decodedControl.isCritical());

    assertNotNull(decodedControl.getValue());

    assertNull(decodedControl.getWarningType());

    assertEquals(decodedControl.getWarningValue(), -1);

    assertEquals(decodedControl.getErrorType(),
         PasswordPolicyErrorType.MUST_SUPPLY_OLD_PASSWORD);


    c = new PasswordPolicyResponseControl(null, -1,
         PasswordPolicyErrorType.INSUFFICIENT_PASSWORD_QUALITY);

    controlObject = c.toJSONControl();

    assertNotNull(controlObject);
    assertEquals(controlObject.getFields().size(), 4);

    assertEquals(controlObject.getFieldAsString("oid"), c.getOID());

    assertNotNull(controlObject.getFieldAsString("control-name"));
    assertFalse(controlObject.getFieldAsString("control-name").isEmpty());
    assertFalse(controlObject.getFieldAsString("control-name").equals(
         controlObject.getFieldAsString("oid")));

    assertEquals(controlObject.getFieldAsBoolean("criticality"),
         Boolean.FALSE);

    assertFalse(controlObject.hasField("value-base64"));

    assertEquals(controlObject.getFieldAsObject("value-json"),
         new JSONObject(
              new JSONField("error-type", "insufficient-password-quality")));


    decodedControl =
         PasswordPolicyResponseControl.decodeJSONControl(controlObject, true);
    assertNotNull(decodedControl);

    assertEquals(decodedControl.getOID(), c.getOID());

    assertFalse(decodedControl.isCritical());

    assertNotNull(decodedControl.getValue());

    assertNull(decodedControl.getWarningType());

    assertEquals(decodedControl.getWarningValue(), -1);

    assertEquals(decodedControl.getErrorType(),
         PasswordPolicyErrorType.INSUFFICIENT_PASSWORD_QUALITY);


    c = new PasswordPolicyResponseControl(null, -1,
         PasswordPolicyErrorType.PASSWORD_TOO_SHORT);

    controlObject = c.toJSONControl();

    assertNotNull(controlObject);
    assertEquals(controlObject.getFields().size(), 4);

    assertEquals(controlObject.getFieldAsString("oid"), c.getOID());

    assertNotNull(controlObject.getFieldAsString("control-name"));
    assertFalse(controlObject.getFieldAsString("control-name").isEmpty());
    assertFalse(controlObject.getFieldAsString("control-name").equals(
         controlObject.getFieldAsString("oid")));

    assertEquals(controlObject.getFieldAsBoolean("criticality"),
         Boolean.FALSE);

    assertFalse(controlObject.hasField("value-base64"));

    assertEquals(controlObject.getFieldAsObject("value-json"),
         new JSONObject(
              new JSONField("error-type", "password-too-short")));


    decodedControl =
         PasswordPolicyResponseControl.decodeJSONControl(controlObject, true);
    assertNotNull(decodedControl);

    assertEquals(decodedControl.getOID(), c.getOID());

    assertFalse(decodedControl.isCritical());

    assertNotNull(decodedControl.getValue());

    assertNull(decodedControl.getWarningType());

    assertEquals(decodedControl.getWarningValue(), -1);

    assertEquals(decodedControl.getErrorType(),
         PasswordPolicyErrorType.PASSWORD_TOO_SHORT);


    c = new PasswordPolicyResponseControl(null, -1,
         PasswordPolicyErrorType.PASSWORD_TOO_YOUNG);

    controlObject = c.toJSONControl();

    assertNotNull(controlObject);
    assertEquals(controlObject.getFields().size(), 4);

    assertEquals(controlObject.getFieldAsString("oid"), c.getOID());

    assertNotNull(controlObject.getFieldAsString("control-name"));
    assertFalse(controlObject.getFieldAsString("control-name").isEmpty());
    assertFalse(controlObject.getFieldAsString("control-name").equals(
         controlObject.getFieldAsString("oid")));

    assertEquals(controlObject.getFieldAsBoolean("criticality"),
         Boolean.FALSE);

    assertFalse(controlObject.hasField("value-base64"));

    assertEquals(controlObject.getFieldAsObject("value-json"),
         new JSONObject(
              new JSONField("error-type", "password-too-young")));


    decodedControl =
         PasswordPolicyResponseControl.decodeJSONControl(controlObject, true);
    assertNotNull(decodedControl);

    assertEquals(decodedControl.getOID(), c.getOID());

    assertFalse(decodedControl.isCritical());

    assertNotNull(decodedControl.getValue());

    assertNull(decodedControl.getWarningType());

    assertEquals(decodedControl.getWarningValue(), -1);

    assertEquals(decodedControl.getErrorType(),
         PasswordPolicyErrorType.PASSWORD_TOO_YOUNG);


    c = new PasswordPolicyResponseControl(null, -1,
         PasswordPolicyErrorType.PASSWORD_IN_HISTORY);

    controlObject = c.toJSONControl();

    assertNotNull(controlObject);
    assertEquals(controlObject.getFields().size(), 4);

    assertEquals(controlObject.getFieldAsString("oid"), c.getOID());

    assertNotNull(controlObject.getFieldAsString("control-name"));
    assertFalse(controlObject.getFieldAsString("control-name").isEmpty());
    assertFalse(controlObject.getFieldAsString("control-name").equals(
         controlObject.getFieldAsString("oid")));

    assertEquals(controlObject.getFieldAsBoolean("criticality"),
         Boolean.FALSE);

    assertFalse(controlObject.hasField("value-base64"));

    assertEquals(controlObject.getFieldAsObject("value-json"),
         new JSONObject(
              new JSONField("error-type", "password-in-history")));


    decodedControl =
         PasswordPolicyResponseControl.decodeJSONControl(controlObject, true);
    assertNotNull(decodedControl);

    assertEquals(decodedControl.getOID(), c.getOID());

    assertFalse(decodedControl.isCritical());

    assertNotNull(decodedControl.getValue());

    assertNull(decodedControl.getWarningType());

    assertEquals(decodedControl.getWarningValue(), -1);

    assertEquals(decodedControl.getErrorType(),
         PasswordPolicyErrorType.PASSWORD_IN_HISTORY);
  }



  /**
   * Tests the behavior when trying to decode a JSON object as a control when
   * the value is base64-encoded.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test()
  public void testDecodeJSONControlValueBase64()
          throws Exception
  {
    final PasswordPolicyResponseControl c =
         new PasswordPolicyResponseControl(
              PasswordPolicyWarningType.TIME_BEFORE_EXPIRATION, 1234,
              PasswordPolicyErrorType.ACCOUNT_LOCKED);

    final JSONObject controlObject = new JSONObject(
         new JSONField("oid", c.getOID()),
         new JSONField("criticality", c.isCritical()),
         new JSONField("value-base64", Base64.encode(c.getValue().getValue())));


    PasswordPolicyResponseControl decodedControl =
         PasswordPolicyResponseControl.decodeJSONControl(controlObject, true);
    assertNotNull(decodedControl);

    assertEquals(decodedControl.getOID(), c.getOID());

    assertFalse(decodedControl.isCritical());

    assertNotNull(decodedControl.getValue());

    assertEquals(decodedControl.getWarningType(),
         PasswordPolicyWarningType.TIME_BEFORE_EXPIRATION);

    assertEquals(decodedControl.getWarningValue(), 1234);

    assertEquals(decodedControl.getErrorType(),
         PasswordPolicyErrorType.ACCOUNT_LOCKED);


    decodedControl =
         (PasswordPolicyResponseControl)
         Control.decodeJSONControl(controlObject, true, false);
    assertNotNull(decodedControl);

    assertEquals(decodedControl.getOID(), c.getOID());

    assertFalse(decodedControl.isCritical());

    assertNotNull(decodedControl.getValue());

    assertEquals(decodedControl.getWarningType(),
         PasswordPolicyWarningType.TIME_BEFORE_EXPIRATION);

    assertEquals(decodedControl.getWarningValue(), 1234);

    assertEquals(decodedControl.getErrorType(),
         PasswordPolicyErrorType.ACCOUNT_LOCKED);
  }



  /**
   * Tests the behavior when trying to decode a JSON object as a control when
   * the value has an empty warning array.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test(expectedExceptions = { LDAPException.class })
  public void testDecodeJSONControlValueEmptyWarningArray()
          throws Exception
  {
    final PasswordPolicyResponseControl c =
         new PasswordPolicyResponseControl(
              PasswordPolicyWarningType.TIME_BEFORE_EXPIRATION, 1234,
              PasswordPolicyErrorType.ACCOUNT_LOCKED);

    final JSONObject controlObject = new JSONObject(
         new JSONField("oid", c.getOID()),
         new JSONField("criticality", c.isCritical()),
         new JSONField("value-json", new JSONObject(
              new JSONField("warning", JSONObject.EMPTY_OBJECT),
              new JSONField("error-type", "account-locked"))));

    PasswordPolicyResponseControl.decodeJSONControl(controlObject, true);
  }



  /**
   * Tests the behavior when trying to decode a JSON object as a control when
   * the value has an unrecognized warning type.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test(expectedExceptions = { LDAPException.class })
  public void testDecodeJSONControlValueUnrecognizedWarningType()
          throws Exception
  {
    final PasswordPolicyResponseControl c =
         new PasswordPolicyResponseControl(
              PasswordPolicyWarningType.TIME_BEFORE_EXPIRATION, 1234,
              PasswordPolicyErrorType.ACCOUNT_LOCKED);

    final JSONObject controlObject = new JSONObject(
         new JSONField("oid", c.getOID()),
         new JSONField("criticality", c.isCritical()),
         new JSONField("value-json", new JSONObject(
              new JSONField("warning", new JSONObject(
                   new JSONField("unrecognized", 1234))),
              new JSONField("error-type", "account-locked"))));

    PasswordPolicyResponseControl.decodeJSONControl(controlObject, true);
  }



  /**
   * Tests the behavior when trying to decode a JSON object as a control when
   * the value has multiple warning types.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test(expectedExceptions = { LDAPException.class })
  public void testDecodeJSONControlValueMultipleWarningTypes()
          throws Exception
  {
    final PasswordPolicyResponseControl c =
         new PasswordPolicyResponseControl(
              PasswordPolicyWarningType.TIME_BEFORE_EXPIRATION, 1234,
              PasswordPolicyErrorType.ACCOUNT_LOCKED);

    final JSONObject controlObject = new JSONObject(
         new JSONField("oid", c.getOID()),
         new JSONField("criticality", c.isCritical()),
         new JSONField("value-json", new JSONObject(
              new JSONField("warning", new JSONObject(
                   new JSONField("seconds-until-expiration", 1234),
                   new JSONField("grace-logins-remaining", 5678))),
              new JSONField("error-type", "account-locked"))));

    PasswordPolicyResponseControl.decodeJSONControl(controlObject, true);
  }



  /**
   * Tests the behavior when trying to decode a JSON object as a control when
   * the value has both recognized and unrecognized warning types.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test()
  public void testDecodeJSONControlValueRecognizedAndUnrecognizedWarningTypes()
          throws Exception
  {
    final PasswordPolicyResponseControl c =
         new PasswordPolicyResponseControl(
              PasswordPolicyWarningType.TIME_BEFORE_EXPIRATION, 1234,
              PasswordPolicyErrorType.ACCOUNT_LOCKED);

    final JSONObject controlObject = new JSONObject(
         new JSONField("oid", c.getOID()),
         new JSONField("criticality", c.isCritical()),
         new JSONField("value-json", new JSONObject(
              new JSONField("warning", new JSONObject(
                   new JSONField("seconds-until-expiration", 1234),
                   new JSONField("unrecognized", 5678))),
              new JSONField("error-type", "account-locked"))));

    try
    {
      PasswordPolicyResponseControl.decodeJSONControl(controlObject, true);
      fail("Expected an exception because of an unrecognized warning field " +
           "in strict mode");
    }
    catch (final LDAPException e)
    {
      assertEquals(e.getResultCode(), ResultCode.DECODING_ERROR);
    }


    PasswordPolicyResponseControl decodedControl =
         PasswordPolicyResponseControl.decodeJSONControl(controlObject, false);
    assertNotNull(decodedControl);

    assertEquals(decodedControl.getOID(), c.getOID());

    assertFalse(decodedControl.isCritical());

    assertNotNull(decodedControl.getValue());

    assertEquals(decodedControl.getWarningType(),
         PasswordPolicyWarningType.TIME_BEFORE_EXPIRATION);

    assertEquals(decodedControl.getWarningValue(), 1234);

    assertEquals(decodedControl.getErrorType(),
         PasswordPolicyErrorType.ACCOUNT_LOCKED);


    decodedControl =
         (PasswordPolicyResponseControl)
         Control.decodeJSONControl(controlObject, false, false);
    assertNotNull(decodedControl);

    assertEquals(decodedControl.getOID(), c.getOID());

    assertFalse(decodedControl.isCritical());

    assertNotNull(decodedControl.getValue());

    assertEquals(decodedControl.getWarningType(),
         PasswordPolicyWarningType.TIME_BEFORE_EXPIRATION);

    assertEquals(decodedControl.getWarningValue(), 1234);

    assertEquals(decodedControl.getErrorType(),
         PasswordPolicyErrorType.ACCOUNT_LOCKED);
  }



  /**
   * Tests the behavior when trying to decode a JSON object as a control when
   * the value has an unrecognized error type.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test(expectedExceptions = { LDAPException.class })
  public void testDecodeJSONControlValueUnrecognizedErrorType()
          throws Exception
  {
    final PasswordPolicyResponseControl c =
         new PasswordPolicyResponseControl(
              PasswordPolicyWarningType.TIME_BEFORE_EXPIRATION, 1234,
              PasswordPolicyErrorType.ACCOUNT_LOCKED);

    final JSONObject controlObject = new JSONObject(
         new JSONField("oid", c.getOID()),
         new JSONField("criticality", c.isCritical()),
         new JSONField("value-json", new JSONObject(
              new JSONField("warning", new JSONObject(
                   new JSONField("seconds-until-expiration", 1234))),
              new JSONField("error-type", "unrecognized"))));

    PasswordPolicyResponseControl.decodeJSONControl(controlObject, true);
  }



  /**
   * Tests the behavior when trying to decode a JSON object as a control when
   * the value has an unrecognized field in strict mode.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test(expectedExceptions = { LDAPException.class })
  public void testDecodeJSONControlValueUnrecognizedFieldStrict()
          throws Exception
  {
    final PasswordPolicyResponseControl c =
         new PasswordPolicyResponseControl(
              PasswordPolicyWarningType.TIME_BEFORE_EXPIRATION, 1234,
              PasswordPolicyErrorType.ACCOUNT_LOCKED);

    final JSONObject controlObject = new JSONObject(
         new JSONField("oid", c.getOID()),
         new JSONField("criticality", c.isCritical()),
         new JSONField("value-json", new JSONObject(
              new JSONField("warning", new JSONObject(
                   new JSONField("seconds-until-expiration", 1234))),
              new JSONField("error-type", "account-locked"),
              new JSONField("unrecognized", "foo"))));

    PasswordPolicyResponseControl.decodeJSONControl(controlObject, true);
  }



  /**
   * Tests the behavior when trying to decode a JSON object as a control when
   * the value has an unrecognized field in non-strict mode.
   *
   * @throws  Exception  If an unexpected problem occurs.
   */
  @Test()
  public void testDecodeJSONControlValueUnrecognizedFieldNonStrict()
          throws Exception
  {
    final PasswordPolicyResponseControl c =
         new PasswordPolicyResponseControl(
              PasswordPolicyWarningType.TIME_BEFORE_EXPIRATION, 1234,
              PasswordPolicyErrorType.ACCOUNT_LOCKED);

    final JSONObject controlObject = new JSONObject(
         new JSONField("oid", c.getOID()),
         new JSONField("criticality", c.isCritical()),
         new JSONField("value-json", new JSONObject(
              new JSONField("warning", new JSONObject(
                   new JSONField("seconds-until-expiration", 1234))),
              new JSONField("error-type", "account-locked"),
              new JSONField("unrecognized", "foo"))));


    PasswordPolicyResponseControl decodedControl =
         PasswordPolicyResponseControl.decodeJSONControl(controlObject, false);
    assertNotNull(decodedControl);

    assertEquals(decodedControl.getOID(), c.getOID());

    assertFalse(decodedControl.isCritical());

    assertNotNull(decodedControl.getValue());

    assertEquals(decodedControl.getWarningType(),
         PasswordPolicyWarningType.TIME_BEFORE_EXPIRATION);

    assertEquals(decodedControl.getWarningValue(), 1234);

    assertEquals(decodedControl.getErrorType(),
         PasswordPolicyErrorType.ACCOUNT_LOCKED);


    decodedControl =
         (PasswordPolicyResponseControl)
         Control.decodeJSONControl(controlObject, false, false);
    assertNotNull(decodedControl);

    assertEquals(decodedControl.getOID(), c.getOID());

    assertFalse(decodedControl.isCritical());

    assertNotNull(decodedControl.getValue());

    assertEquals(decodedControl.getWarningType(),
         PasswordPolicyWarningType.TIME_BEFORE_EXPIRATION);

    assertEquals(decodedControl.getWarningValue(), 1234);

    assertEquals(decodedControl.getErrorType(),
         PasswordPolicyErrorType.ACCOUNT_LOCKED);
  }
}
