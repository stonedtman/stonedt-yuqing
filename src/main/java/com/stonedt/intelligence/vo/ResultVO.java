package com.stonedt.intelligence.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 文轩
 * 通用返回对象
 * @param <T>
 */
@Data
@Schema(name = "ResultVO", description = "通用返回对象")
public class ResultVO <T>{

    /**
     * 状态码
     */
    @Schema(name = "code", description = "状态码")
    private Integer code;

    /**
     * 提示信息
     */
    @Schema(name = "msg", description = "提示信息")
    private String msg;

    /**
     * 具体内容
     */
    @Schema(name = "data", description = "具体内容")
    private T data;

    public static <T> ResultVO<T> success(T data) {
        ResultVO<T> resultVO = new ResultVO<>();
        resultVO.setCode(200);
        resultVO.setMsg("success");
        resultVO.setData(data);
        return resultVO;
    }

    public static <T> ResultVO<T> success() {
        ResultVO<T> resultVO = new ResultVO<>();
        resultVO.setCode(200);
        resultVO.setMsg("success");
        return resultVO;
    }

    public static <T> ResultVO<T> error(String msg) {
        ResultVO<T> resultVO = new ResultVO<>();
        resultVO.setCode(500);
        resultVO.setMsg(msg);
        return resultVO;
    }

    public static <T> ResultVO<T> error(Integer code, String msg) {
        ResultVO<T> resultVO = new ResultVO<>();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }
}
