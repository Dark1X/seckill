package com.loodeer.dao;

import com.loodeer.dataobject.ItemStockDO;
import com.loodeer.dataobject.ItemStockDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ItemStockDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Sun Dec 16 20:05:14 CST 2018
     */
    long countByExample(ItemStockDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Sun Dec 16 20:05:14 CST 2018
     */
    int deleteByExample(ItemStockDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Sun Dec 16 20:05:14 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Sun Dec 16 20:05:14 CST 2018
     */
    int insert(ItemStockDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Sun Dec 16 20:05:14 CST 2018
     */
    int insertSelective(ItemStockDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Sun Dec 16 20:05:14 CST 2018
     */
    List<ItemStockDO> selectByExample(ItemStockDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Sun Dec 16 20:05:14 CST 2018
     */
    ItemStockDO selectByPrimaryKey(Integer id);

    ItemStockDO selectByItemId(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Sun Dec 16 20:05:14 CST 2018
     */
    int updateByExampleSelective(@Param("record") ItemStockDO record, @Param("example") ItemStockDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Sun Dec 16 20:05:14 CST 2018
     */
    int updateByExample(@Param("record") ItemStockDO record, @Param("example") ItemStockDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Sun Dec 16 20:05:14 CST 2018
     */
    int updateByPrimaryKeySelective(ItemStockDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item_stock
     *
     * @mbg.generated Sun Dec 16 20:05:14 CST 2018
     */
    int updateByPrimaryKey(ItemStockDO record);
}
