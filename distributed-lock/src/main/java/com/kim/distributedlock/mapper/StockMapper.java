package com.kim.distributedlock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kim.distributedlock.pojo.Stock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface StockMapper extends BaseMapper<Stock> {

    @Update("UPDATE tb_stock SET count = count - #{count} WHERE product_code = #{productCode} AND count >= #{count};")
    int updateStock(@Param("productCode") String productCode, @Param("count") Integer count);


    @Select("SELECT * FROM tb_stock WHERE product_code = #{productCode} for update;")
    List<Stock> queryStock(@Param("productCode") String productCode);
}
