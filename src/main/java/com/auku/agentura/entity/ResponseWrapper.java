package com.auku.agentura.entity;

public class ResponseWrapper {
    private String errorMessage;
    private String detailedErrorMessage;
    private boolean isSuccess = true;

    public ResponseWrapper() {

    }

    public ResponseWrapper(String errorMessage, String detailedErrorMessage, boolean isSuccess) {
        this.errorMessage = errorMessage;
        this.detailedErrorMessage = detailedErrorMessage;
        this.isSuccess = isSuccess;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getDetailedErrorMessage() {
        return detailedErrorMessage;
    }

    public void setDetailedErrorMessage(String detailedErrorMessage) {
        this.detailedErrorMessage = detailedErrorMessage;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
