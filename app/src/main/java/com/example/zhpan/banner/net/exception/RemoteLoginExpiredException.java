
package com.example.zhpan.banner.net.exception;

import com.example.zhpan.banner.net.common.ErrorCode;

/**
 * Created by zhpan on 2018/3/27.
 */
public class RemoteLoginExpiredException extends RuntimeException {
  private int errorCode;

  public RemoteLoginExpiredException(int errorCode, String cause) {
    super(ErrorCode.getErrorMessage(errorCode), new Throwable(cause));
    this.errorCode = errorCode;
  }

  public int getErrorCode() {
    return errorCode;
  }
}