package com.kgc.provider.service;


import org.apache.ibatis.annotations.Param;

public interface ChooseService {

    //更新积分
    int updateIntergral(@Param("phone") String phone, @Param("phone") int reIntegral);

}
