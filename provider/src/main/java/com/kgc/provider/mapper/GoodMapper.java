package com.kgc.provider.mapper;

import com.kgc.provider.model.Good;
import com.kgc.provider.model.GoodExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GoodMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods
     *
     * @mbg.generated
     */
    long countByExample(GoodExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods
     *
     * @mbg.generated
     */
    int deleteByExample(GoodExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods
     *
     * @mbg.generated
     */
    int insert(Good record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods
     *
     * @mbg.generated
     */
    int insertSelective(Good record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods
     *
     * @mbg.generated
     */
    List<Good> selectByExample(GoodExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods
     *
     * @mbg.generated
     */
    Good selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Good record, @Param("example") GoodExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") Good record, @Param("example") GoodExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Good record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Good record);
}