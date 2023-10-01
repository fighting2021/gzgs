package cn.edu.gzgs.ims.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.crypto.SealedObject;
import java.io.Serializable;

@Data
@ApiModel("统一返回结果")
public class WrapperResult<T> implements Serializable {
    private static final long serialVersionUID = 5778573516446596671L;

    @ApiModelProperty("返回码")
    private Integer code = 0;

    @ApiModelProperty("返回消息")
    private String message;

    @ApiModelProperty("返回数据")
    private T data;

    public WrapperResult() {
    }

    public WrapperResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功
     *
     * @param data 返回数据
     * @param <T>
     * @return
     */
    public static <T> WrapperResult<T> success(T data) {
        return new WrapperResult(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功
     *
     * @param data    返回数据
     * @param message 返回消息
     * @param <T>
     * @return
     */
    public static <T> WrapperResult<T> success(T data, String message) {
        message = message != null && message.length() > 0 ? message : ErrorCode.SUCCESS.getMessage();
        return new WrapperResult(ErrorCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败
     * @param data 返回数据
     * @param <T>
     * @return
     */
    public static <T> WrapperResult<T> fail(T data) {
        return new WrapperResult(ErrorCode.FAIL.getCode(), ErrorCode.FAIL.getMessage(), data);
    }

    /**
     *
     * @param data 返回数据
     * @param message 返回消息
     * @param <T>
     * @return
     */
    public static <T> WrapperResult<T> fail(T data, String message) {
        message = message != null && message.length() > 0 ? message : ErrorCode.FAIL.getMessage();
        return new WrapperResult(ErrorCode.FAIL.getCode(), message, data);
    }

}
