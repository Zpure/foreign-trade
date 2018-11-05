package com.zcpure.foreign.trade.mybatis;

import org.apache.ibatis.logging.Log;

public class DisabledMybatisDefaultLog implements Log {
    public DisabledMybatisDefaultLog(String s) {

    }

    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    @Override
    public boolean isTraceEnabled() {
        return false;
    }

    @Override
    public void error(String s, Throwable throwable) {

    }

    @Override
    public void error(String s) {

    }

    @Override
    public void debug(String s) {
    }

    @Override
    public void trace(String s) {

    }

    @Override
    public void warn(String s) {

    }
}
