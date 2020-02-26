/*
 * Copyright 2016-2020 Ping Identity Corporation
 * All Rights Reserved.
 */
/*
 * Copyright 2016-2020 Ping Identity Corporation
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
 * Copyright (C) 2016-2020 Ping Identity Corporation
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



import java.util.ArrayList;

import com.unboundid.asn1.ASN1Element;
import com.unboundid.asn1.ASN1OctetString;
import com.unboundid.asn1.ASN1Sequence;
import com.unboundid.ldap.sdk.BindResult;
import com.unboundid.ldap.sdk.Control;
import com.unboundid.ldap.sdk.InternalSDKHelper;
import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.ResultCode;
import com.unboundid.ldap.sdk.SASLBindRequest;
import com.unboundid.ldap.sdk.unboundidds.extensions.
            DeregisterYubiKeyOTPDeviceExtendedRequest;
import com.unboundid.ldap.sdk.unboundidds.extensions.
            RegisterYubiKeyOTPDeviceExtendedRequest;
import com.unboundid.util.Debug;
import com.unboundid.util.NotMutable;
import com.unboundid.util.StaticUtils;
import com.unboundid.util.ThreadSafety;
import com.unboundid.util.ThreadSafetyLevel;
import com.unboundid.util.Validator;

import static com.unboundid.ldap.sdk.unboundidds.UnboundIDDSMessages.*;



/**
 * This class provides an implementation of a SASL bind request that may be used
 * to authenticate to a Directory Server using the UNBOUNDID-YUBIKEY-OTP
 * mechanism.  The credentials include at least an authentication ID and a
 * one-time password generated by a YubiKey device.  The request may also
 * include a static password (which may or may not be required by the server)
 * and an optional authorization ID.
 * <BR>
 * <BLOCKQUOTE>
 *   <B>NOTE:</B>  This class, and other classes within the
 *   {@code com.unboundid.ldap.sdk.unboundidds} package structure, are only
 *   supported for use against Ping Identity, UnboundID, and
 *   Nokia/Alcatel-Lucent 8661 server products.  These classes provide support
 *   for proprietary functionality or for external specifications that are not
 *   considered stable or mature enough to be guaranteed to work in an
 *   interoperable way with other types of LDAP servers.
 * </BLOCKQUOTE>
 * <BR>
 * The UNBOUNDID-YUBIKEY-OTP bind request MUST include SASL credentials with the
 * following ASN.1 encoding:
 * <BR><BR>
 * <PRE>
 *   UnboundIDYubiKeyCredentials ::= SEQUENCE {
 *        authenticationID     [0] OCTET STRING,
 *        authorizationID      [1] OCTET STRING OPTIONAL,
 *        staticPassword       [2] OCTET STRING OPTIONAL,
 *        yubiKeyOTP           [3] OCTET STRING,
 *        ... }
 * </PRE>
 *
 *
 * @see  RegisterYubiKeyOTPDeviceExtendedRequest
 * @see  DeregisterYubiKeyOTPDeviceExtendedRequest
 */
@NotMutable()
@ThreadSafety(level=ThreadSafetyLevel.COMPLETELY_THREADSAFE)
public final class UnboundIDYubiKeyOTPBindRequest
       extends SASLBindRequest
{
  /**
   * The name for the UnboundID YubiKey SASL mechanism.
   */
  public static final String UNBOUNDID_YUBIKEY_OTP_MECHANISM_NAME =
       "UNBOUNDID-YUBIKEY-OTP";



  /**
   * The BER type for the authentication ID element of the credentials sequence.
   */
  private static final byte TYPE_AUTHENTICATION_ID = (byte) 0x80;



  /**
   * The BER type for the authorization ID element of the credentials sequence.
   */
  private static final byte TYPE_AUTHORIZATION_ID = (byte) 0x81;



  /**
   * The BER type for the static password element of the credentials sequence.
   */
  private static final byte TYPE_STATIC_PASSWORD = (byte) 0x82;



  /**
   * The BER type for the YubiKey OTP element of the credentials sequence.
   */
  private static final byte TYPE_YUBIKEY_OTP = (byte) 0x83;



  /**
   * The serial version UID for this serializable class.
   */
  private static final long serialVersionUID = -6124016046606933247L;



  // The static password for the user, if provided.
  private final ASN1OctetString staticPassword;

  // The message ID from the last LDAP message sent from this request.
  private volatile int messageID = -1;

  // The authentication ID for the user.
  private final String authenticationID;

  // The authorization ID for the bind request, if provided.
  private final String authorizationID;

  // The one-time password generated by a YubiKey device.
  private final String yubiKeyOTP;



  /**
   * Creates a new UNBOUNDID-YUBIKEY-OTP bind request with the provided
   * information.
   *
   * @param  authenticationID  The authentication ID for the bind request.  It
   *                           must not be {@code null}, and must have the form
   *                           "dn:" followed by the DN of the target user or
   *                           "u:" followed by the the username of the target
   *                           user.
   * @param  authorizationID   The authorization ID for the bind request.  It
   *                           may be {@code null} if the authorization identity
   *                           should be the same as the authentication
   *                           identity.
   * @param  staticPassword    The static password for the user specified as the
   *                           authentication identity.  It may be {@code null}
   *                           if authentication should be performed using only
   *                           the YubiKey OTP.
   * @param  yubiKeyOTP        The one-time password generated by the YubiKey
   *                           device.  It must not be {@code null}.
   * @param  controls          The set of controls to include in the bind
   *                           request.  It may be {@code null} or empty if
   *                           there should not be any request controls.
   */
  public UnboundIDYubiKeyOTPBindRequest(final String authenticationID,
                                        final String authorizationID,
                                        final String staticPassword,
                                        final String yubiKeyOTP,
                                        final Control... controls)
  {
    this(authenticationID, authorizationID, toASN1OctetString(staticPassword),
         yubiKeyOTP, controls);
  }



  /**
   * Creates a new UNBOUNDID-YUBIKEY-OTP bind request with the provided
   * information.
   *
   * @param  authenticationID  The authentication ID for the bind request.  It
   *                           must not be {@code null}, and must have the form
   *                           "dn:" followed by the DN of the target user or
   *                           "u:" followed by the the username of the target
   *                           user.
   * @param  authorizationID   The authorization ID for the bind request.  It
   *                           may be {@code null} if the authorization identity
   *                           should be the same as the authentication
   *                           identity.
   * @param  staticPassword    The static password for the user specified as the
   *                           authentication identity.  It may be {@code null}
   *                           if authentication should be performed using only
   *                           the YubiKey OTP.
   * @param  yubiKeyOTP        The one-time password generated by the YubiKey
   *                           device.  It must not be {@code null}.
   * @param  controls          The set of controls to include in the bind
   *                           request.  It may be {@code null} or empty if
   *                           there should not be any request controls.
   */
  public UnboundIDYubiKeyOTPBindRequest(final String authenticationID,
                                        final String authorizationID,
                                        final byte[] staticPassword,
                                        final String yubiKeyOTP,
                                        final Control... controls)
  {
    this(authenticationID, authorizationID, toASN1OctetString(staticPassword),
         yubiKeyOTP, controls);
  }



  /**
   * Creates a new UNBOUNDID-YUBIKEY-OTP bind request with the provided
   * information.
   *
   * @param  authenticationID  The authentication ID for the bind request.  It
   *                           must not be {@code null}, and must have the form
   *                           "dn:" followed by the DN of the target user or
   *                           "u:" followed by the the username of the target
   *                           user.
   * @param  authorizationID   The authorization ID for the bind request.  It
   *                           may be {@code null} if the authorization identity
   *                           should be the same as the authentication
   *                           identity.
   * @param  staticPassword    The static password for the user specified as the
   *                           authentication identity.  It may be {@code null}
   *                           if authentication should be performed using only
   *                           the YubiKey OTP.
   * @param  yubiKeyOTP        The one-time password generated by the YubiKey
   *                           device.  It must not be {@code null}.
   * @param  controls          The set of controls to include in the bind
   *                           request.  It may be {@code null} or empty if
   *                           there should not be any request controls.
   */
  private UnboundIDYubiKeyOTPBindRequest(final String authenticationID,
                                         final String authorizationID,
                                         final ASN1OctetString staticPassword,
                                         final String yubiKeyOTP,
                                         final Control... controls)
  {
    super(controls);

    Validator.ensureNotNull(authenticationID);
    Validator.ensureNotNull(yubiKeyOTP);

    this.authenticationID = authenticationID;
    this.authorizationID  = authorizationID;
    this.staticPassword   = staticPassword;
    this.yubiKeyOTP       = yubiKeyOTP;
  }



  /**
   * Retrieves an ASN.1 octet string that represents the appropriate encoding
   * for the provided password.
   *
   * @param  password  The password object to convert to an ASN.1 octet string.
   *                   It may be {@code null} if no static password is required.
   *                   Otherwise, it must either be a string or a byte array.
   *
   * @return  The ASN.1 octet string created from the provided password object,
   *          or {@code null} if the provided password object was null.
   */
  private static ASN1OctetString toASN1OctetString(final Object password)
  {
    if (password == null)
    {
      return null;
    }
    else if (password instanceof byte[])
    {
      return new ASN1OctetString(TYPE_STATIC_PASSWORD, (byte[]) password);
    }
    else
    {
      return new ASN1OctetString(TYPE_STATIC_PASSWORD,
           String.valueOf(password));
    }
  }



  /**
   * Creates a new UNBOUNDID-YUBIKEY-OTP SASL bind request decoded from the
   * provided SASL credentials.
   *
   * @param  saslCredentials  The SASL credentials to decode in order to create
   *                          the UNBOUNDID-YUBIKEY-OTP SASL bind request.  It
   *                          must not be {@code null}.
   * @param  controls         The set of controls to include in the bind
   *                          request.  This may be {@code null} or empty if no
   *                          controls should be included in the request.
   *
   * @return  The UNBOUNDID-YUBIKEY-OTP SASL bind request decoded from the
   *          provided credentials.
   *
   * @throws  LDAPException  If the provided credentials cannot be decoded to a
   *                         valid UNBOUNDID-YUBIKEY-OTP bind request.
   */
  public static UnboundIDYubiKeyOTPBindRequest decodeCredentials(
                     final ASN1OctetString saslCredentials,
                     final Control... controls)
         throws LDAPException
  {
    try
    {
      ASN1OctetString staticPassword = null;
      String authenticationID = null;
      String authorizationID  = null;
      String yubiKeyOTP = null;

      for (final ASN1Element e :
           ASN1Sequence.decodeAsSequence(saslCredentials.getValue()).elements())
      {
        switch (e.getType())
        {
          case TYPE_AUTHENTICATION_ID:
            authenticationID =
                 ASN1OctetString.decodeAsOctetString(e).stringValue();
            break;
          case TYPE_AUTHORIZATION_ID:
            authorizationID =
                 ASN1OctetString.decodeAsOctetString(e).stringValue();
            break;
          case TYPE_STATIC_PASSWORD:
            staticPassword = ASN1OctetString.decodeAsOctetString(e);
            break;
          case TYPE_YUBIKEY_OTP:
            yubiKeyOTP = ASN1OctetString.decodeAsOctetString(e).stringValue();
            break;
          default:
            throw new LDAPException(ResultCode.DECODING_ERROR,
                 ERR_YUBIKEY_OTP_DECODE_UNRECOGNIZED_CRED_ELEMENT.get(
                      UNBOUNDID_YUBIKEY_OTP_MECHANISM_NAME,
                      StaticUtils.toHex(e.getType())));
        }
      }

      if (authenticationID == null)
      {
        throw new LDAPException(ResultCode.DECODING_ERROR,
             ERR_YUBIKEY_OTP_DECODE_NO_AUTH_ID.get(
                  UNBOUNDID_YUBIKEY_OTP_MECHANISM_NAME));
      }

      if (yubiKeyOTP == null)
      {
        throw new LDAPException(ResultCode.DECODING_ERROR,
             ERR_YUBIKEY_OTP_NO_OTP.get(UNBOUNDID_YUBIKEY_OTP_MECHANISM_NAME));
      }

      return new UnboundIDYubiKeyOTPBindRequest(authenticationID,
           authorizationID, staticPassword, yubiKeyOTP, controls);
    }
    catch (final LDAPException le)
    {
      Debug.debugException(le);
      throw le;
    }
    catch (final Exception e)
    {
      Debug.debugException(e);
      throw new LDAPException(ResultCode.DECODING_ERROR,
           ERR_YUBIKEY_OTP_DECODE_ERROR.get(
                UNBOUNDID_YUBIKEY_OTP_MECHANISM_NAME,
                StaticUtils.getExceptionMessage(e)),
           e);
    }
  }



  /**
   * Retrieves the authentication ID for the bind request.
   *
   * @return  The authentication ID for the bind request.
   */
  public String getAuthenticationID()
  {
    return authenticationID;
  }



  /**
   * Retrieves the authorization ID for the bind request, if any.
   *
   * @return  The authorization ID for the bind request, or {@code null} if the
   *          authorization identity should match the authentication identity.
   */
  public String getAuthorizationID()
  {
    return authorizationID;
  }



  /**
   * Retrieves the string representation of the static password for the bind
   * request, if any.
   *
   * @return  The string representation of the static password for the bind
   *          request, or {@code null} if there is no static password.
   */
  public String getStaticPasswordString()
  {
    if (staticPassword == null)
    {
      return null;
    }
    else
    {
      return staticPassword.stringValue();
    }
  }



  /**
   * Retrieves the bytes that comprise the static password for the bind request,
   * if any.
   *
   * @return  The bytes that comprise the static password for the bind request,
   *          or {@code null} if there is no static password.
   */
  public byte[] getStaticPasswordBytes()
  {
    if (staticPassword == null)
    {
      return null;
    }
    else
    {
      return staticPassword.getValue();
    }
  }



  /**
   * Retrieves the YubiKey-generated one-time password to include in the bind
   * request.
   *
   * @return  The YubiKey-generated one-time password to include in the bind
   *          request.
   */
  public String getYubiKeyOTP()
  {
    return yubiKeyOTP;
  }



  /**
   * Sends this bind request to the target server over the provided connection
   * and returns the corresponding response.
   *
   * @param  connection  The connection to use to send this bind request to the
   *                     server and read the associated response.
   * @param  depth       The current referral depth for this request.  It should
   *                     always be one for the initial request, and should only
   *                     be incremented when following referrals.
   *
   * @return  The bind response read from the server.
   *
   * @throws  LDAPException  If a problem occurs while sending the request or
   *                         reading the response.
   */
  @Override()
  protected BindResult process(final LDAPConnection connection, final int depth)
            throws LDAPException
  {
    messageID = InternalSDKHelper.nextMessageID(connection);
    return sendBindRequest(connection, "", encodeCredentials(), getControls(),
         getResponseTimeoutMillis(connection));
  }



  /**
   * Retrieves an ASN.1 octet string containing the encoded credentials for this
   * bind request.
   *
   * @return  An ASN.1 octet string containing the encoded credentials for this
   *          bind request.
   */
  public ASN1OctetString encodeCredentials()
  {
    return encodeCredentials(authenticationID, authorizationID, staticPassword,
         yubiKeyOTP);
  }



  /**
   * Encodes the provided information into an ASN.1 octet string suitable for
   * use as the SASL credentials for an UNBOUNDID-YUBIKEY-OTP bind request.
   *
   * @param  authenticationID  The authentication ID for the bind request.  It
   *                           must not be {@code null}, and must have the form
   *                           "dn:" followed by the DN of the target user or
   *                           "u:" followed by the the username of the target
   *                           user.
   * @param  authorizationID   The authorization ID for the bind request.  It
   *                           may be {@code null} if the authorization identity
   *                           should be the same as the authentication
   *                           identity.
   * @param  staticPassword    The static password for the user specified as the
   *                           authentication identity.  It may be {@code null}
   *                           if authentication should be performed using only
   *                           the YubiKey OTP.
   * @param  yubiKeyOTP        The one-time password generated by the YubiKey
   *                           device.  It must not be {@code null}.
   *
   * @return  An ASN.1 octet string suitable for use as the SASL credentials for
   *          an UNBOUNDID-YUBIKEY-OTP bind request.
   */
  public static ASN1OctetString encodeCredentials(final String authenticationID,
                                     final String authorizationID,
                                     final ASN1OctetString staticPassword,
                                     final String yubiKeyOTP)
  {
    Validator.ensureNotNull(authenticationID);
    Validator.ensureNotNull(yubiKeyOTP);

    final ArrayList<ASN1Element> elements = new ArrayList<>(4);
    elements.add(new ASN1OctetString(TYPE_AUTHENTICATION_ID, authenticationID));

    if (authorizationID != null)
    {
      elements.add(new ASN1OctetString(TYPE_AUTHORIZATION_ID, authorizationID));
    }

    if (staticPassword != null)
    {
      elements.add(new ASN1OctetString(TYPE_STATIC_PASSWORD,
           staticPassword.getValue()));
    }

    elements.add(new ASN1OctetString(TYPE_YUBIKEY_OTP, yubiKeyOTP));

    return new ASN1OctetString(new ASN1Sequence(elements).encode());
  }



  /**
   * {@inheritDoc}
   */
  @Override()
  public UnboundIDYubiKeyOTPBindRequest duplicate()
  {
    return duplicate(getControls());
  }



  /**
   * {@inheritDoc}
   */
  @Override()
  public UnboundIDYubiKeyOTPBindRequest duplicate(final Control[] controls)
  {
    final UnboundIDYubiKeyOTPBindRequest bindRequest =
         new UnboundIDYubiKeyOTPBindRequest(authenticationID, authorizationID,
              staticPassword, yubiKeyOTP, controls);
    bindRequest.setResponseTimeoutMillis(getResponseTimeoutMillis(null));
    return bindRequest;
  }



  /**
   * {@inheritDoc}
   */
  @Override()
  public String getSASLMechanismName()
  {
    return UNBOUNDID_YUBIKEY_OTP_MECHANISM_NAME;
  }



  /**
   * {@inheritDoc}
   */
  @Override()
  public int getLastMessageID()
  {
    return messageID;
  }



  /**
   * {@inheritDoc}
   */
  @Override()
  public void toString(final StringBuilder buffer)
  {
    buffer.append("UnboundYubiKeyOTPBindRequest(authenticationID='");
    buffer.append(authenticationID);

    if (authorizationID != null)
    {
      buffer.append("', authorizationID='");
      buffer.append(authorizationID);
    }

    buffer.append("', staticPasswordProvided=");
    buffer.append(staticPassword != null);

    final Control[] controls = getControls();
    if (controls.length > 0)
    {
      buffer.append(", controls={");
      for (int i=0; i < controls.length; i++)
      {
        if (i > 0)
        {
          buffer.append(", ");
        }

        buffer.append(controls[i]);
      }
      buffer.append('}');
    }

    buffer.append(')');
  }
}
