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
        switch (state) {
            case SUCCESS:
                this.code = 200;
                this.state = SUCCESS;
                this.message = "操作成功";
                break;
            case FAIL:
                this.code = 500;
                this.state = FAIL;
                this.message = "操作失败";
                break;
            case NO_LOGIN:
                this.code = 500;
                this.state = NO_LOGIN;
                this.message = "请登录";
                break;
            case WITHOUT_PERM:
                this.code = 500;
                this.state = WITHOUT_PERM;
                this.message = "您无权操作";
                break;
            case ERROR:
                this.code = 500;
                this.state = ERROR;
                this.message = "服务器忙，请稍后再试";
                break;
            case NO_DATA:
                this.code = 500;
                this.state = NO_DATA;
                this.message = "没有数据";
                break;
            case TEACHER_INFO_ERROR:
                this.code = 500;
                this.state = TEACHER_INFO_ERROR;
                this.message = "教师信息错误";
                break;
            case INSERT_FAILED:
                this.code = 500;
                this.state = INSERT_FAILED;
                this.message = "插入数据失败";
                break;
            case TYPE_EXIST:
                this.code = 500;
                this.state = TYPE_EXIST;
                this.message = "类型已存在";
                break;
            case TYPE_NOT_EXIST:
                this.code = 500;
                this.state = TYPE_NOT_EXIST;
                this.message = "类型不存在";
                break;
            case ILLEGAL_PARAMETER:
                this.code = 500;
                this.state = ILLEGAL_PARAMETER;
                this.message = "参数非法";
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
