package com.zcpure.foreign.trade.log;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.classic.spi.Configurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.spi.ContextAwareBase;
import org.slf4j.helpers.Util;
import org.springframework.boot.logging.LoggingSystem;

import java.net.URL;


public class LogDefaultConfigurator extends ContextAwareBase implements Configurator {


    @Override
    public void configure(LoggerContext lc) {
        this.addInfo("Setting up retail default configuration.");

        lc.reset();

        JoranConfigurator configurator = new JoranConfigurator();
        try {
            URL url = Configurator.class.getClassLoader().getResource("logback_default.xml");
            // 加载默认配置
            configurator.setContext(lc);
            configurator.doConfigure(url);

            // 注意：springboot中会重新加载log配置， 下行代码可以让springboot不重新加载
            lc.putObject(LoggingSystem.class.getName(), 1);
        } catch (JoranException e) {
            Util.report("Failed to instantiate [" + LoggerContext.class.getName() + "]", e);
        }
    }

}
