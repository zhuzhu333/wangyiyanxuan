package com.kgc.provider.service;

import com.kgc.provider.dto.Order;
import org.apache.ibatis.annotations.Param;

/**
 * Created By: YunCeng
 * Created on: 2019/10/22:18:51
 */

public interface AAOrderService {

    Order selectByGoodId(String goodId);

    Order selectBygoodIdAndPhone(String goodId,String phone);

    int updateStatus(String code);


}
