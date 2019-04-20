package com.springboot;

import com.springboot.activemq.producer.Producer;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * SpringBootApplication注解组合了@Configuration、@EnableAutoConfiguration、@ComponentScan
 * 可以使用这三个注解替代@SpringBootApplication注解
 */
@SpringBootApplication
@MapperScan("com.springboot.mapper")
@EnableTransactionManagement
@EnableJms
public class MainApplication extends SpringBootServletInitializer {

    @Resource
    private Producer producer;

    @Bean
    public boolean message() {
        producer.sendMessage("1-初始化模拟队列消息输送完成！");
        producer.sendMessage("2-初始化模拟队列消息输送完成！");
        producer.sendMessage("3-初始化模拟队列消息输送完成！","main-thread-queue");
        producer.delaySend(Producer.DEFAULT_QUEUE_OBJECT,"向客戶端推送延时信息",5000L);
        return true;
    }

    /**
     * 注入hibernate的SessionFactory
     *
     * @param hibernateEntityManagerFactory
     * @return SessionFactory
     */
    @Bean
    public SessionFactory sessionFactory(HibernateEntityManagerFactory hibernateEntityManagerFactory) {
        return hibernateEntityManagerFactory.getSessionFactory();
    }

    /**
     * 启动方法：
     * 1、如下main方法直接运行
     * 2、进入到项目的根目录后执行 mvn spring-boot:run
     * 3、进入到项目根目录 执行 mvn install 编译项目 然后 cd target 执行dir 再利用 java -jar 项目名.jar启动
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
        openExploer();
    }

    /**
     * 设置tomcat的长传文件最大限制为不限制
     * tomcatEmbedded这段代码是为了解决，上传文件大于10M出现连接重置的问题
     * 此异常内容GlobalException也捕获不到,可以将容量限制放在业务中处理,方便异常处理
     *
     * @return
     */
    @Bean
    public TomcatEmbeddedServletContainerFactory tomcatEmbedded() {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
        tomcat.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> {
            if ((connector.getProtocolHandler() instanceof AbstractHttp11Protocol<?>)) {
                ((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(-1);
            }
        });
        return tomcat;
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MainApplication.class);
    }

    public static void openExploer(){
        String cmd = "D:\\soft\\360se6\\Application\\360se.exe http://localhost:8816/login/loginIn";
        Runtime run = Runtime.getRuntime();
        try{
            run.exec(cmd);
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            System.out.println(date + " [INFO] - 启动浏览器打开项目成功");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
