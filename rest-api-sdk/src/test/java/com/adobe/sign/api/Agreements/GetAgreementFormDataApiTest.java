/*
*  Copyright 2016 Adobe Systems Incorporated. All rights reserved.
*  This file is licensed to you under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License. You may obtain a copy
*  of the License at http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software distributed under
*  the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR REPRESENTATIONS
*  OF ANY KIND, either express or implied. See the License for the specific language
*  governing permissions and limitations under the License.
*
*/

package com.adobe.sign.api.Agreements;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.adobe.sign.api.AgreementsApi;
import com.adobe.sign.utils.AgreementsUtils;
import com.adobe.sign.utils.ApiException;
import com.adobe.sign.utils.ApiUtils;
import com.adobe.sign.utils.Context;
import com.adobe.sign.utils.Retry;
import com.adobe.sign.utils.TestData;
import com.adobe.sign.utils.SdkErrorCodes;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

/**
 * Junit test cases for Get Agreement Form Data endpoint.
 */
public class GetAgreementFormDataApiTest {
  private static AgreementsApi agreementsApi = null;
  private static String agreementId = null;


  @Rule
  public Retry retry = new Retry();

  @BeforeClass
  public static void setup() throws ApiException {
    ApiUtils.configureProperty();
    agreementsApi = AgreementsUtils.getAgreementsApi();
    agreementId = AgreementsUtils.getResourceId(TestData.AGREEMENT_NAME);
  }

  /**
   * Test for fetching an agreement's form data through the getFormData endpoint. Negative scenarios covered:
   * NO_ACCESS_TOKEN_HEADER: null access token.
   * INVALID_ACCESS_TOKEN: empty access token.
   *
   * @throws ApiException
   */
  @Test
  public void testNullAndEmptyAccessToken() throws ApiException {

    try {
      byte[] formData = agreementsApi.getFormData(ApiUtils.getNullAccessTokenHeaderParams(),
                                                  agreementId);
      assertNotNull(formData);
    }
    catch (ApiException e) {
      assertTrue(e.getMessage(), SdkErrorCodes.NO_ACCESS_TOKEN_HEADER.getApiCode().equals(e.getApiCode()));
    }

    try {
      byte[] formData = agreementsApi.getFormData(ApiUtils.getEmptyAccessTokenHeaderParams(),
                                                  agreementId);
      assertNotNull(formData);
    }
    catch (ApiException e) {
      assertTrue(e.getMessage(), SdkErrorCodes.INVALID_ACCESS_TOKEN.getApiCode().equals(e.getApiCode()));
    }
  }

  /**
   * Test for fetching an agreement's form data through the getFormData endpoint. Negative scenarios covered:
   * INVALID_X_API_USER_HEADER: empty xApiUser.
   *
   * @throws ApiException
   */
  @Test
  public void testInvalidXApiUser() throws ApiException {

    try {
      byte[] formData = agreementsApi.getFormData(ApiUtils.getEmptyXApiUserHeaderParams(),
                                                  agreementId);
      assertNotNull(formData);
    }
    catch (ApiException e) {
      assertTrue(e.getMessage(), SdkErrorCodes.INVALID_X_API_USER_HEADER.getApiCode().equals(e.getApiCode()));
    }
  }

  /**
   * Test for fetching an agreement's form data through the getFormData endpoint. Negative scenarios covered:
   * INVALID_AGREEMENT_ID: null and empty agreementId.
   *
   * @throws ApiException
   */
  @Test
  public void testInvalidAgreementId() throws ApiException {

    try {
      byte[] formData = agreementsApi.getFormData(ApiUtils.getValidHeaderParams(),
                                                  TestData.EMPTY_PARAM);
      assertNotNull(formData);
    }
    catch (ApiException e) {
      assertTrue(e.getMessage(), SdkErrorCodes.INVALID_AGREEMENT_ID.getApiCode().equals(e.getApiCode()));
    }

    try {
      byte[] formData = agreementsApi.getFormData(ApiUtils.getValidHeaderParams(),
                                                  TestData.NULL_PARAM);
      assertNotNull(formData);
    }
    catch (ApiException e) {
      assertTrue(e.getMessage(), SdkErrorCodes.INVALID_AGREEMENT_ID.getApiCode().equals(e.getApiCode()));
    }
  }

  /**
   * Test for fetching an agreement's form data through the getFormData endpoint.
   */
  @Test
  public void testFormData() throws ApiException {

    try {
      byte[] formData = agreementsApi.getFormData(ApiUtils.getValidHeaderParams(),
                                                  agreementId);
      assertNotNull(formData);
    }
    catch (ApiException e) {
      fail(ApiUtils.getMessage(e));
    }
  }

}
