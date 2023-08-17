package io.shmilyhe.convert.api;

import io.shmilyhe.convert.tools.ExpEnv;

/**
 * 转换器
 * 
 */
public interface IConvertor {
    Object convert(Object root,ExpEnv env);
}
