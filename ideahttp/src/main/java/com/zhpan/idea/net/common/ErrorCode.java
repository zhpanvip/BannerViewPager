
package com.zhpan.idea.net.common;

import androidx.annotation.StringRes;

import com.zhpan.idea.R;
import com.zhpan.idea.utils.Utils;

/**
 * Created by zhpan on 2018/3/27.
 */
public class ErrorCode {
    /**
     * request success
     */
    public static final int SUCCESS = 0;
    /**
     * Wrong verify code
     */
    public static final int VERIFY_CODE_ERROR = 110011;
    /**
     * Verify code is invalid
     */
    public static final int VERIFY_CODE_EXPIRED=110010;
    /**
     * User is not register
     */
    public static final int ACCOUNT_NOT_REGISTER =110009;
    /**
     *  Wrong password or username
     */
    public static final int PASSWORD_ERROR=110012;

    /**
     *  Wrong old password
     */
    public static final int OLD_PASSWORD_ERROR=110015;

    public static final int USER_REGISTERED =110006;

    public static final int PARAMS_ERROR =19999;
    /**
     * 异地登录
     */
    public static final int REMOTE_LOGIN=91011;

    /**
     * get error message with error code
     *
     * @param errorCode error code
     * @return error message
     */
    public static String getErrorMessage(int errorCode) {
        String message;
        switch (errorCode) {
            case VERIFY_CODE_ERROR:
                message = getString(R.string.verify_code_error);
                break;
            case VERIFY_CODE_EXPIRED:
                message=getString(R.string.verify_code_expired);
                break;
            case ACCOUNT_NOT_REGISTER:
                message=getString(R.string.not_register);
                break;
            case PASSWORD_ERROR:
                message=getString(R.string.wrong_pwd_username);
                break;
            case USER_REGISTERED:
                message=getString(R.string.user_registered);
                break;
            case OLD_PASSWORD_ERROR:
                message=getString(R.string.wrong_password);
                break;
            case PARAMS_ERROR:
                message=getString(R.string.parameters_exception);
                break;
            case REMOTE_LOGIN:
                message=getString(R.string.remote_login);
                break;
            default:
                message = getString(R.string.request_error) + errorCode;
                break;
        }
        return message;
    }

    private static String getString(@StringRes int resId) {
        return Utils.getContext().getString(resId);
    }
}
