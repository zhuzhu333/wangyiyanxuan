package com.kgc.provider.mapper;

import com.kgc.provider.dto.Shoppingcart;
import com.kgc.provider.dto.WxUser;
import com.kgc.provider.dto.WxUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WxUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxusers
     *
     * @mbg.generated
     */
    long countByExample(WxUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxusers
     *
     * @mbg.generated
     */
    int deleteByExample(WxUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxusers
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxusers
     *
     * @mbg.generated
     */
    int insert(WxUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxusers
     *
     * @mbg.generated
     */
    int insertSelective(WxUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxusers
     *
     * @mbg.generated
     */
    List<WxUser> selectByExample(WxUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxusers
     *
     * @mbg.generated
     */
    WxUser selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxusers
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") WxUser record, @Param("example") WxUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxusers
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") WxUser record, @Param("example") WxUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxusers
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(WxUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxusers
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(WxUser record);

}