package io.shmilyhe.convert.callee;

import java.util.List;

import io.shmilyhe.convert.tools.ExpEnv;

/**
 * 扩展函数
 */
public interface IFunction {
    /**
     * 函数调用入口
     * @param args 参数LIST
     * @param env 环境
     * @return 返回值
     */
    Object call(List args,ExpEnv env);
}
