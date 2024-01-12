package io.shmilyhe.convert.log.api;

public interface Logger {
    public void debug(String format, Object... arguments);
    public void info(String format, Object... arguments);
    public void warn(String msg, Throwable t);
    public void warn(String format, Object... arguments);
    public void error(String format, Object... arguments);
    public void error(String msg, Throwable t);
}
