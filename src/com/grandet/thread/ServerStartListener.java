package com.grandet.thread;

import com.grandet.service.PriceService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by outen on 16/7/6.
 */
public class ServerStartListener implements ServletContextListener {
    private UpdatePriceThread thread;
    private WebApplicationContext springContext;
    private PriceService priceService;


    public void contextDestroyed(ServletContextEvent e) {
        if (thread != null && thread.isInterrupted()) {
            thread.interrupt();
        }
    }

    public void contextInitialized(ServletContextEvent e) {
        springContext = WebApplicationContextUtils.getWebApplicationContext(e.getServletContext());
        if(springContext != null){
            priceService = (PriceService) springContext.getBean("PriceService");
        }else{
            System.out.println("获取应用程序上下文失败!");
            return;
        }

        if (thread == null) {
            thread = new UpdatePriceThread(priceService);
            thread.start(); // servlet 上下文初始化时启动 socket
        }
    }
}

