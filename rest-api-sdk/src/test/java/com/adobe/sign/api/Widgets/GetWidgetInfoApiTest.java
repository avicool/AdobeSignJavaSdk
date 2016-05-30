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

package com.adobe.sign.api.Widgets;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.adobe.sign.api.WidgetsApi;
import com.adobe.sign.model.widgets.WidgetInfo;
import com.adobe.sign.utils.ApiUtils;
import com.adobe.sign.utils.TestData;
import com.adobe.sign.utils.WidgetUtils;
import com.adobe.sign.utils.ApiException;
import com.adobe.sign.utils.validator.SdkErrorCodes;
import org.junit.Before;
import org.junit.Test;

/**
 * Junit test cases for Get Widget Info API.
 */
public class GetWidgetInfoApiTest {

  private WidgetsApi widgetsApi = null;
  private String widgetId = null;

  @Before
  public void setup() throws ApiException {
    widgetId = WidgetUtils.getResourceId(TestData.WIDGET_NAME);
    widgetsApi = WidgetUtils.getWidgetsApi();
  }

  /**
   * Test for retrieving the details of the widget through the getWidgetInfo endpoint. Negative scenarios covered:
   * NO_ACCESS_TOKEN_HEADER: null access token.
   * INVALID_ACCESS_TOKEN: empty access token.
   *
   * @throws ApiException
   */
  @Test
  public void testNullAndEmptyAccessToken() throws ApiException {
    try {
      widgetsApi.getWidgetInfo(TestData.NULL_PARAM,
                               widgetId,
                               TestData.X_API_HEADER);
    }
    catch (ApiException e) {
      assertTrue(e.getMessage(),
                 SdkErrorCodes.NO_ACCESS_TOKEN_HEADER.getApiCode().equals(e.getApiCode()));
    }

    try {
      widgetsApi.getWidgetInfo(TestData.EMPTY_PARAM,
                               widgetId,
                               TestData.X_API_HEADER);
    }
    catch (ApiException e) {
      assertTrue(e.getMessage(),
                 SdkErrorCodes.INVALID_ACCESS_TOKEN.getApiCode().equals(e.getApiCode()));
    }
  }

  /**
   * Test for retrieving the details of the widget through the getWidgetInfo endpoint. Negative scenarios covered:
   * INVALID_X_API_USER_HEADER: empty xApiUser.
   *
   * @throws ApiException
   */
  @Test
  public void testInvalidXApiUser() throws ApiException {
    try {
      widgetsApi.getWidgetInfo(TestData.ACCESS_TOKEN,
                               widgetId,
                               TestData.EMPTY_PARAM);
    }
    catch (ApiException e) {
      assertTrue(e.getMessage(),
                 SdkErrorCodes.INVALID_X_API_USER_HEADER.getApiCode().equals(e.getApiCode()));
    }
  }

  /**
   * Test for retrieving the details of the widget through the getWidgetInfo endpoint. Negative scenarios covered:
   * INVALID_WIDGET_ID: empty and null widgetId.
   *
   * @throws ApiException
   */
  @Test
  public void testInvalidWidgetId() throws ApiException {
    try {
      widgetsApi.getWidgetInfo(TestData.ACCESS_TOKEN,
                               TestData.EMPTY_PARAM,
                               TestData.X_API_HEADER);
    }
    catch (ApiException e) {
      assertTrue(e.getMessage(),
                 SdkErrorCodes.INVALID_WIDGET_ID.getApiCode().equals(e.getApiCode()));
    }

    try {
      widgetsApi.getWidgetInfo(TestData.ACCESS_TOKEN,
                               TestData.NULL_PARAM,
                               TestData.X_API_HEADER);
    }
    catch (ApiException e) {
      assertTrue(e.getMessage(),
                 SdkErrorCodes.INVALID_WIDGET_ID.getApiCode().equals(e.getApiCode()));
    }
  }

  /**
   * Test for retrieving the details of the widget through the getWidgetInfo endpoint.
   * Case covered is successful execution of the api call.
   *
   * @throws ApiException
   */
  @Test
  public void testWidgetInfo() throws ApiException {
    try {
      WidgetInfo widgetInfo = widgetsApi.getWidgetInfo(TestData.ACCESS_TOKEN,
                                                       widgetId,
                                                       TestData.X_API_HEADER);
      assertNotNull(widgetInfo);
      assertNotNull(widgetInfo.getWidgetId());
    }
    catch (ApiException e) {
      fail(ApiUtils.getMessage(e));
    }
  }


}