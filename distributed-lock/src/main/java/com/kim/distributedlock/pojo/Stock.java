package com.kim.distributedlock.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("tb_stock")
@Data
public class Stock {
    private Long id;
    private String productCode;
    private String warehouse;
    private Integer count;
    private Integer version;
//    private Integer stock = 5000;
}
