package com.alibaba.otter.canal.admin.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * spring util配置类
 *
 * @author rewerma @ 2018-10-20
 * @version 1.0.0
 */
@Component
public class ApplicationHolder implements ApplicationContextAware {

    private static ApplicationContext context;

    /**
     * 注入ApplicationContext
     */
    @Override
    public void setApplicationContext(final ApplicationContext context) throws BeansException {
        // 在加载Spring时自动获得context
        ApplicationHolder.context = context;
    }

    public static <T> T getBean(final String beanName, Class<T> clz) {
        return ApplicationHolder.context.getBean(beanName, clz);
    }

    public static <T> T getBean(final Class<T> clz) {
        return context.getBean(clz);
    }
}
