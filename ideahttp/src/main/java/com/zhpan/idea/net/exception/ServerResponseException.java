package com.zhpan.idea.net.exception;

import com.zhpan.idea.net.common.ErrorCode;

/**
 * 服务器返回的异常
 */
public class ServerResponseException extends RuntimeException {

    private int errorCode;

    public ServerResponseException(int errorCode, String cause) {
        super(ErrorCode.getErrorMessage(errorCode), new Throwable(cause));
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
