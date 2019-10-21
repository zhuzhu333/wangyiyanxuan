package com.kgc.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kgc.provider.dto.*;
import com.kgc.provider.mapper.MemberMapper;
import com.kgc.provider.mapper.OrderMapper;
import com.kgc.provider.mapper.UserMapper;
import com.kgc.provider.service.PersonnalService;
import com.kgc.provider.util.CalendarUtil;
import org.springframework.beans.factory.annotation.Autowired;



import java.util.Date;
import java.util.List;

/**
 * @author
 * @description
 * @return
 * @throws
 * @date 2019/10/20 12:26
 * @since
 */
@Service
public class PersonnalServiceImpl implements PersonnalService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Override
    public List<Order> serchPaiedOrder(String phone) {
        OrderExample orderExample=new OrderExample();
        orderExample.createCriteria().andPhoneEqualTo(phone).andStatusEqualTo(1);
        List<Order> list=orderMapper.selectByExample(orderExample);
        return list;
    }

    @Override
    public boolean updateIntegral(String phone,int integral) {
        UserExample userExample=new UserExample();
        userExample.createCriteria().andUserPhoneEqualTo(phone);
        User user=new User();
        user.setIntegral(integral);
        boolean flag= userMapper.updateByExampleSelective(user,userExample)==1?true:false;
        return flag;
    }

    @Override
    public boolean updateLevel(String phone, int level) {
        UserExample userExample=new UserExample();
        userExample.createCriteria().andUserPhoneEqualTo(phone);
        User user=new User();
        user.setUserLevel(level);
        boolean flag= userMapper.updateByExampleSelective(user,userExample)==1?true:false;
        return flag;
    }

    @Override
    public boolean updateUserInformation(User user) {
        UserExample userExample=new UserExample();
        userExample.createCriteria().andUserPhoneEqualTo(user.getUserPhone());
        boolean flag=userMapper.updateByExampleSelective(user,userExample)==1?true:false;
        return flag;
    }

    @Override
    public void createMember(String phone, int day) {
        Date date=new Date();
        Date endDate= CalendarUtil.getEndTimeOfDay(date.getTime(),30);
        Member member=new Member();
        member.setCreateTime(date);
        member.setEndTime(endDate);
        member.setPhone(phone);
        member.setMembertype("超级会员");
        memberMapper.insert(member);
    }

    @Override
    public Member searchMember(String phone) {
        MemberExample memberExample=new MemberExample();
        memberExample.createCriteria().andPhoneEqualTo(phone);
        List<Member> list=memberMapper.selectByExample(memberExample);
        if(!list.isEmpty()){
          return   list.get(0);
        }
        return null;
    }

}
