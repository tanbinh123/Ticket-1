package com.woniuxy.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.woniuxy.user.validation.Login;
import com.woniuxy.user.validation.Register;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 *
 * </p>
 *
 * @author ll_5216
 * @since 2021-02-23
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode()
@TableName("ticket_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Null(message = "用户ID应为空", groups = Register.class)
    private Integer id;

    @NotBlank(message = "账号不能为空", groups = {Register.class, Login.class})
    @Size(min = 6, max = 20, message = "账号长度应在{min}-{max}位之间", groups = Register.class)
    private String account;

    @NotBlank(message = "密码不能为空", groups = {Register.class, Login.class})
    @Size(min = 6, max = 20, message = "密码长度应在{min}-{max}位之间", groups = Register.class)
    private String password;

    private String avatar;

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$",
            message = "无效手机号", groups = Register.class)
    private String tel;

    @Null(message = "注册时间应为空", groups = Register.class)
    private LocalDate regTime;

    @Null(message = "积分应为空", groups = Register.class)
    private Integer integration;


}
