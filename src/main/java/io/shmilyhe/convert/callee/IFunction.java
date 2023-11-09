package io.shmilyhe.convert.callee;

import java.util.List;

import io.shmilyhe.convert.tools.ExpEnv;

public interface IFunction {
    Object call(List args,ExpEnv env);
}
