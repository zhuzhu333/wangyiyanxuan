package com.kgc.provider.service;

import com.kgc.provider.dto.Member;
import com.kgc.provider.dto.Order;
import com.kgc.provider.dto.OrderExample;
import com.kgc.provider.dto.User;

import java.util.List;

/**
 * @author
 * @description
 * @return
 * @throws
 * @date 2019/10/20 12:26
 * @since
 */
public interface PersonnalService {
    List<Order> serchPaiedOrder(String phone);
    boolean updateIntegral(String phone,int integral);
    boolean updateLevel(String phone,int level);
    boolean updateUserInformation(User user);
    void createMember(String phone,int day);
    Member searchMember(String phone);
}
