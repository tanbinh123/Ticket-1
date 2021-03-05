package com.woniuxy.auth.exception;

import com.woniuxy.common.enums.StateEnum;
import com.woniuxy.common.utils.ResponseResult;
import org.springframework.dao.DataAccessException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class UserExceptionHandler {

    /**
     * 数据库操作失败
     *
     * @return 响应
     */
    @ExceptionHandler(DataAccessException.class)
    public ResponseResult<?> DataAccessExceptionHandler(DataAccessException e) {
        e.printStackTrace();
        return new ResponseResult<>(StateEnum.ERROR);
    }

    /**
     * 积分不足
     *
     * @return 响应
     */
    @ExceptionHandler(IntegrationLackException.class)
    public ResponseResult<?> IntegrationLackExceptionHandler(IntegrationLackException e) {
        e.printStackTrace();
        return new ResponseResult<>(StateEnum.INTEGRATION_LACK);
    }

    /**
     * 账户已存在
     *
     * @return 响应
     */
    @ExceptionHandler(AccountExistedException.class)
    public ResponseResult<?> AccountExistedExceptionHandler(AccountExistedException e) {
        e.printStackTrace();
        return new ResponseResult<>(StateEnum.ACCOUNT_EXISTED);
    }

    /**
     * 手机号已存在
     *
     * @return 响应
     */
    @ExceptionHandler(TelExistedException.class)
    public ResponseResult<?> TelExistedExceptionHandler(TelExistedException e) {
        e.printStackTrace();
        return new ResponseResult<>(StateEnum.TEL_EXISTED);
    }

    /**
     * 状态有误
     *
     * @return 响应
     */
    @ExceptionHandler(StatusErrorException.class)
    public ResponseResult<?> StatusErrorExceptionHandler(StatusErrorException e) {
        e.printStackTrace();
        return new ResponseResult<>(StateEnum.STATUS_ERROR);
    }

    /**
     * 参数非法
     *
     * @return 响应
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult<?> IllegalParameterExceptionHandler(MethodArgumentNotValidException e) {
        e.printStackTrace();

        // 获取校验结果
        BindingResult result = e.getBindingResult();
        // 获取所有字段错误
        List<FieldError> errors = result.getFieldErrors();
        // 存放字段错误
        Map<String, String> msg = new HashMap<>();
        errors.forEach(error -> msg.put(error.getField(), error.getDefaultMessage()));

        return new ResponseResult<>(StateEnum.ILLEGAL_PARAMETER).setData(msg);
    }

    @ExceptionHandler(BindException.class)
    public ResponseResult<?> IllegalParameterExceptionHandler(BindException e) {
        e.printStackTrace();

        // 获取校验结果
        BindingResult result = e.getBindingResult();
        // 获取所有字段错误
        List<FieldError> errors = result.getFieldErrors();
        // 存放字段错误
        Map<String, String> msg = new HashMap<>();
        errors.forEach(error -> msg.put(error.getField(), error.getDefaultMessage()));

        return new ResponseResult<>(StateEnum.ILLEGAL_PARAMETER).setData(msg);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseResult<?> IllegalParameterExceptionHandler(ConstraintViolationException e) {
        e.printStackTrace();

        Map<String, Object> msg = new HashMap<>();
        e.getConstraintViolations().forEach(error -> {
            String message = error.getMessage();
            String path = error.getPropertyPath().toString();
            msg.put(path.substring(path.lastIndexOf(".") + 1), message);
        });

        return new ResponseResult<>(StateEnum.ILLEGAL_PARAMETER).setData(msg);
    }
}
