package com.demo.tk.mybatis;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 注意：该接口不能被扫描到，否则会出错
 * @param <T>
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper {
}
