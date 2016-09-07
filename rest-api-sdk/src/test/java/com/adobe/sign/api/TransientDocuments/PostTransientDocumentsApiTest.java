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

package com.adobe.sign.api.TransientDocuments;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.adobe.sign.api.TransientDocumentsApi;
import com.adobe.sign.model.transientDocuments.TransientDocumentResponse;
import com.adobe.sign.utils.ApiException;
import com.adobe.sign.utils.ApiUtils;
import com.adobe.sign.utils.Context;
import com.adobe.sign.utils.Retry;
import com.adobe.sign.utils.TestData;
import com.adobe.sign.utils.TransientDocumentsUtils;
import com.adobe.sign.utils.validator.SdkErrorCodes;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

/**
 * Junit test cases for Post Transient Documents API.
 */
public class PostTransientDocumentsApiTest {
  
  private static TransientDocumentsApi transientDocumentsApi = null;



  @Rule
  public Retry retry = new Retry();
  
  
  @BeforeClass
  public static void setup() {
    ApiUtils.configureProperty();
    transientDocumentsApi = TransientDocumentsUtils.getTransientDocumentsApi();
  }
  
  /**
   * Test for creating a transientDocument through the createTransientDocuments endpoint. Negative scenarios covered:
   * NO_ACCESS_TOKEN_HEADER: null access token.
   * INVALID_ACCESS_TOKEN: empty access token.
   *
   * @throws ApiException
   */
  @Test
  public void testNullAndEmptyAccessToken() throws ApiException {
    try {
      transientDocumentsApi.createTransientDocument(ApiUtils.getNullAccessTokenHeaderParams(),
                                                    TestData.SAMPLE_FILE,
                                                    TestData.TRANSIENT_DOCUMENT_NAME,
                                                    TestData.VALID_MIME);
    }
    catch (ApiException e) {
      assertTrue(e.getMessage(),
                 SdkErrorCodes.NO_ACCESS_TOKEN_HEADER.getApiCode().equals(e.getApiCode()));
    }

    try {
      transientDocumentsApi.createTransientDocument(ApiUtils.getEmptyAccessTokenHeaderParams(),
                                                    TestData.SAMPLE_FILE,
                                                    TestData.TRANSIENT_DOCUMENT_NAME,
                                                    TestData.VALID_MIME);
    }
    catch (ApiException e) {
      assertTrue(e.getMessage(),
                 SdkErrorCodes.INVALID_ACCESS_TOKEN.getApiCode().equals(e.getApiCode()));
    }
  }
  
  /**
   * Test for creating a transientDocument through the createTransientDocuments endpoint. Negative scenarios covered:
   * INVALID_X_API_USER_HEADER: empty xApiUser.
   *
   * @throws ApiException
   */
  @Test
  public void testInvalidXApiUser() throws ApiException {
    try {
      transientDocumentsApi.createTransientDocument(ApiUtils.getEmptyXApiUserHeaderParams(),
                                                    TestData.SAMPLE_FILE,
                                                    TestData.TRANSIENT_DOCUMENT_NAME,
                                                    TestData.VALID_MIME);
    }
    catch (ApiException e) {
      assertTrue(e.getMessage(),
                 SdkErrorCodes.INVALID_X_API_USER_HEADER.getApiCode().equals(e.getApiCode()));
    }
  }
  
  /**
   * Test for creating a transientDocument through the createTransientDocuments endpoint. Negative scenarios covered:
   * NO_FILE_CONTENT: null file.
   *
   * @throws ApiException
   */
  @Test
  public void testInvalidFile() throws ApiException {
    try {
      transientDocumentsApi.createTransientDocument(ApiUtils.getValidHeaderParams(),
                                                    null,
                                                    TestData.TRANSIENT_DOCUMENT_NAME,
                                                    TestData.VALID_MIME);
    }
    catch (ApiException e) {
      assertTrue(e.getMessage(),
                 SdkErrorCodes.NO_FILE_CONTENT.getApiCode().equals(e.getApiCode()));
    }
  }
  
  /**
   * Test for creating a transientDocument through the createTransientDocuments endpoint. Negative scenarios covered:
   * NO_FILE_NAME: empty file name.
   *
   * @throws ApiException
   */
  @Test
  public void testInvalidFileName() throws ApiException {
    try {
      transientDocumentsApi.createTransientDocument(ApiUtils.getValidHeaderParams(),
                                                    TestData.SAMPLE_FILE,
                                                    TestData.EMPTY_PARAM,
                                                    TestData.VALID_MIME);
    }
    catch (ApiException e) {
      assertTrue(e.getMessage(),
                 SdkErrorCodes.NO_FILE_NAME.getApiCode().equals(e.getApiCode()));
    }
  }
  
  /**
   * Test for creating a transientDocument through the createTransientDocuments endpoint. Negative scenarios covered:
   * NO_MEDIA_TYPE: empty file extension and empty mime.
   * UNSUPPORTED_MEDIA_TYPE: non-empty and non matching file extension and mime.
   *
   * @throws ApiException
   */
  @Test
  public void testInvalidFileExtensionAndMime() throws ApiException {
    try {
      transientDocumentsApi.createTransientDocument(ApiUtils.getValidHeaderParams(),
                                                    TestData.NO_EXTENSION_FILE,
                                                    TestData.TRANSIENT_DOCUMENT_NAME,
                                                    TestData.EMPTY_MIME);
    }
    catch (ApiException e) {
      assertTrue(e.getMessage(),
                 SdkErrorCodes.NO_MEDIA_TYPE.getApiCode().equals(e.getApiCode()));
    }
  }
  
  /**
   * Test for creating a transientDocument through the createTransientDocuments endpoint.
   *
   * @throws ApiException
   */
  @Test
  public void testCreateTransientDocument() throws ApiException {
    try {
      TransientDocumentResponse response = transientDocumentsApi.createTransientDocument(ApiUtils.getValidHeaderParams(),
                                                                                         TestData.SAMPLE_FILE.getAbsoluteFile(),
                                                                                         TestData.TRANSIENT_DOCUMENT_NAME,
                                                                                         TestData.VALID_MIME);
      assertNotNull(response);
      assertNotNull(response.getTransientDocumentId());
    }
    catch (ApiException e) {
      fail(ApiUtils.getMessage(e));
    }
  }

}
