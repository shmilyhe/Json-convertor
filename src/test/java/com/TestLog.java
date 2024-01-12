package com;

import io.shmilyhe.convert.log.Log;
import io.shmilyhe.convert.log.api.Logger;

public class TestLog {
    public static void main(String[] args) {
     Logger log= Log.getLogger(TestLog.class);
     log.info("这是一条{}的日志", "测试");
     log.info("这是一条{}的日志");
    }
}
