package com.yimamerchant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author ruoyi
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class RuoYiApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(RuoYiApplication.class, args);
        System.out.println("\n" +
                "    __   __  _                         \n" +
                "    \\ \\ / / (_)  _ __ ___     __ _    \n" +
                "     \\ V /  | | | '_ ` _ \\   / _` |   \n" +
                "      | |   | | | | | | | | | (_| |   \n" +
                "      |_|   |_| |_| |_| |_|  \\__,_|   \n" +
                "                                      \n" +
                "  ==================================  \n" +
                "          亿 马 商 家 端 已 启 动         \n" +
                "  ==================================  \n");
    }
}
