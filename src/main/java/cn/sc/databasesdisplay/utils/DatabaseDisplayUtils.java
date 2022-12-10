package cn.sc.databasesdisplay.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;

@Configuration
public class DatabaseDisplayUtils implements ApplicationContextAware {

   private ConfigurableApplicationContext applicationContext;

   public JdbcTemplate getJdbcTemplate(String jdbcId) {
      JdbcTemplate bean;
      try {
         bean = this.applicationContext.getBean(jdbcId, JdbcTemplate.class);
      } catch (BeansException e) {
         throw new RuntimeException("连接不存在");
      }
      return bean;
   }

   public void setJdbcTemplate(String jdbcId, JdbcTemplate jdbcTemplate) {
      try {
         this.applicationContext.getBeanFactory().registerSingleton(jdbcId, jdbcTemplate);
      } catch (BeansException e) {
         throw new RuntimeException("bean id exit...");
      }
   }

   @Override
   public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
      this.applicationContext = (ConfigurableApplicationContext) applicationContext;
   }
}
