package com.kgc.provider.mapper;

import com.kgc.provider.dto.Order;
import com.kgc.provider.dto.OrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper {
    long countByExample(OrderExample example);

    int deleteByExample(OrderExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Order record);

    int insertSelective(Order record);

    List<Order> selectByExample(OrderExample example);

    Order selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByExample(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    int highPraise(String gid);

    int totalOrder(String gid);

    List<Order> getGoodList(Order order);

    int count(Order order);

    int isDel(int id);

    int updateGoodIntegral(@Param("id")int id,@Param("score")int score);

    int cutStock(String phone);
}