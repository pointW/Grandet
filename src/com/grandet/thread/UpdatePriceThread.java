package com.grandet.thread;

import com.grandet.service.PriceService;

/**
 * Created by outen on 16/7/6.
 */
public class UpdatePriceThread extends Thread{
    private PriceService priceService;
    public UpdatePriceThread(PriceService priceService){
        this.priceService = priceService;
    }

    public void run() {
        while (!this.isInterrupted()) {// 线程未中断执行循环
            System.out.println("____FUCK TIME:" + System.currentTimeMillis());
            try {
                if (priceService != null) {
                    System.out.println("begin updating price");
                    priceService.updateAllPrice();
                    System.out.println("update over");
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }

            try {
                Thread.sleep(60*60*1000); //每隔一小时执行一次
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
