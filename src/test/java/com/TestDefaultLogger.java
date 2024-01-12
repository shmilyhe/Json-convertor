package com;

import io.shmilyhe.convert.log.impl.DefaultLogger;

public class TestDefaultLogger {
    public static void main(String[] args) {
        DefaultLogger log = new DefaultLogger(TestDefaultLogger.class);
        log.info("hi {}", "logger!");
        log.warn("hi {}", "logger!");
        log.error("hi {}", "logger!");
        log.debug("hi {}", "logger!");
        log.error("处理失败！", new RuntimeException("NaN!"));
    }
}
