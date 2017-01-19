/*
 * Created by Gigigo Android Team
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gigigo.interactorexecutor.domain.responses;

public class BusinessError<ExtraErrorInfo> {

  public static final int EXCEPTION_BUSINESS_ERROR_CODE = -222;
  public static final int NO_ERROR_BUSINESS_ERROR_CODE = 0;
  public static final String NO_ERROR_BUSINESS_ERROR_MESSAGE = "OK";

  private BusinessContentType businessContentType;
  private int code;
  private String message;
  private ExtraErrorInfo extraErrorInfo;

  public BusinessError(int code, String message, BusinessContentType businessContentType) {
    this.code = code;
    this.message = message;
    this.businessContentType = businessContentType;
  }

  public BusinessContentType getBusinessContentType() {
    return businessContentType;
  }

  public void setBusinessContentType(BusinessContentType businessContentType) {
    this.businessContentType = businessContentType;
  }

  public static BusinessError createOKInstance() {
    BusinessError businessError =
        new BusinessError(NO_ERROR_BUSINESS_ERROR_CODE, NO_ERROR_BUSINESS_ERROR_MESSAGE,
            BusinessContentType.NO_ERROR_CONTENT);
    return businessError;
  }

  public static BusinessError createKoInstance(String message) {
    BusinessError businessError = new BusinessError(EXCEPTION_BUSINESS_ERROR_CODE, message,
        BusinessContentType.EXCEPTION_CONTENT);
    return businessError;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public ExtraErrorInfo getExtraErrorInfo() {
    return extraErrorInfo;
  }

  public void setExtraErrorInfo(ExtraErrorInfo extraErrorInfo) {
    this.extraErrorInfo = extraErrorInfo;
  }
}
