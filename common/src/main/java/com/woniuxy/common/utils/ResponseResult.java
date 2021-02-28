package com.woniuxy.common.utils;


import com.woniuxy.common.enums.StateEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import static com.woniuxy.common.enums.StateEnum.*;


@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult<T> {
    private int code;           //状态码
    private StateEnum state;    //状态
    private String message;     //提示信息
    private T data;             //返回的数据

    public ResponseResult(StateEnum state) {
        this.state = state;
        switch (state) {
            case SUCCESS:
                this.code = 200;
                this.message = "操作成功";
                break;
            case FAIL:
                this.code = 500;
                this.message = "操作失败";
                break;
            case NO_LOGIN:
                this.code = 500;
                this.message = "请登录";
                break;
            case WITHOUT_PERM:
                this.code = 500;
                this.message = "您无权操作";
                break;
            case ERROR:
                this.code = 500;
                this.message = "服务器忙，请稍后再试";
                break;
            case NO_DATA:
                this.code = 500;
                this.message = "没有数据";
                break;
            case INSERT_FAILED:
                this.code = 500;
                this.message = "插入数据失败";
                break;
            case TYPE_EXIST:
                this.code = 500;
                this.message = "类型已存在";
                break;
            case TYPE_NOT_EXIST:
                this.code = 500;
                this.message = "类型不存在";
                break;
            case ILLEGAL_PARAMETER:
                this.code = 500;
                this.message = "参数非法";
                break;
            case CINEMA_INFO_EXIST:
                this.code = 500;
                this.message = "影院信息已存在";
                break;
            case HALL_EXIST:
                this.code = 500;
                this.message = "放映厅已存在";
                break;
            case SEAT_UNAVAILABLE:
                this.code = 500;
                this.message = "座位不可用";
                break;
            case SEAT_AVAILABLE:
                this.code = 500;
                this.message = "座位已可用";
                break;
            case STATUS_ERROR:
                this.code = 500;
                this.message = "状态有误";
                break;
            case SCHEDULING_OVER:
                this.code = 500;
                this.message = "排片已结束";
                break;
            case INTEGRATION_LACK:
                this.code = 500;
                this.message = "积分不足";
                break;
        }
    }

    public ResponseResult(T data) {
        this.code = 200;
        this.state = SUCCESS;
        this.data = data;
        this.message = "操作成功";
    }

}
