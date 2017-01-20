package com.gigigo.interactorexecutor.responses;

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

  public static BusinessError createKOInstance(String message) {
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
