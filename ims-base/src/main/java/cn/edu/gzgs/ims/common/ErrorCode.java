package cn.edu.gzgs.ims.common;

public enum ErrorCode {
    /* 成功 */
    SUCCESS(0, "成功"),

    //失败
    FAIL(-1, "失败"),

    USER_ACCOUNT_EXPIRED(2002, "账号已过期"),
    USER_CREDENTIALS_ERROR(2003, "密码错误"),
    USER_CREDENTIALS_EXPIRED(2004, "密码过期"),
    USER_ACCOUNT_DISABLE(2005, "账号不可用"),
    USER_ACCOUNT_LOCKED(2006, "账号被锁定"),
    USER_ACCOUNT_NOT_EXIST(2007, "账号不存在"),
    LOGIN_TIME_EXPIRED(2010, "未登录或登陆已失效，请先登录"),

    /** 没有权限 */
    NO_PERMISSION(3001, "没有权限");

    private Integer code;
    private String message;

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 根据code获取message
     */
    public static String getMessageByCode(Integer code) {
        for (ErrorCode ele : values()) {
            if (ele.getCode().equals(code)) {
                return ele.getMessage();
            }
        }
        return null;
    }
}
