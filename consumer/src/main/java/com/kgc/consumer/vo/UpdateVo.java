package com.kgc.consumer.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author
 * @description
 * @return
 * @throws
 * @date 2019/10/20 17:58
 * @since
 */
public class UpdateVo implements Serializable {
    @ApiModelProperty(value = "用户姓名", required = true)
    private String userName;
    @ApiModelProperty(value = "用户手机号", required = true)
    private String userPhone;
    @ApiModelProperty(value = "用户密码", required = true)
    private String userPassword;
    @ApiModelProperty(value = "用户性别", required = true)
    private Integer userSex;
    @ApiModelProperty(value = "用户邮箱", required = true)
    private String userEmail;
    @ApiModelProperty(value = "用户地址", required = true)
    private String address;
    @ApiModelProperty(value = "生日", required = true)
    private String birthday;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Integer getUserSex() {
        return userSex;
    }

    public void setUserSex(Integer userSex) {
        this.userSex = userSex;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
