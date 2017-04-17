package com.poker.colapanda.zhenrendantiao.common.network;

/**
 * 错误类型
 * Created by xiaomengli on 16/5/17.
 */
public class ResultError {
    public int ret;
    public String desc;
    // 请求结果为空
    public static final int RESULT_NULL = -1;
    // 请求结果数据格式错误
    public static final int RESULT_DATA_FORMAT_ERROR = -2;
    // 请求返回错误
    public static final int RESULT_ERROR = -3;

    public static final String MESSAGE_NULL = "网络异常，请检查网络后重试";
    public static final String MESSAGE_ERROR = "数据格式错误，请稍候重试";
}
