package com.gigigo.interactorexecutor.responses;

public class BusinessObject<ModelObject> {

  private ModelObject data;
  private BusinessError businessError;

  public BusinessObject(ModelObject data, BusinessError businessError) {
    this.data = data;
    this.businessError = businessError;
  }

  public ModelObject getData() {
    return data;
  }

  public void setData(ModelObject data) {
    this.data = data;
  }

  public BusinessError getBusinessError() {
    return businessError;
  }

  public void setBusinessError(BusinessError businessError) {
    this.businessError = businessError;
  }

  public boolean isSuccess() {
    return businessError.getBusinessContentType().equals(BusinessContentType.NO_ERROR_CONTENT);
  }
}
