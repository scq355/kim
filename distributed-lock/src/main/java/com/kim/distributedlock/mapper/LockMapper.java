package com.kim.distributedlock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kim.distributedlock.pojo.Lock;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LockMapper extends BaseMapper<Lock> {
}
