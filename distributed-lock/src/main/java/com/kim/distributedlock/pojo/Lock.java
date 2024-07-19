package com.kim.distributedlock.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("tb_lock")
public class Lock {
    private Long id;
    private String lockName;
}
