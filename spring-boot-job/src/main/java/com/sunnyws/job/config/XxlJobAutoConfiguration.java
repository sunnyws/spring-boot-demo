package com.sunnyws.job.config;

import com.sunnyws.job.properties.XxlExecutorProperties;
import com.sunnyws.job.properties.XxlJobProperties;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/*
* @Description //TODO
* @Author qinlang
* @Date 9:28 2020/9/14
**/

@Slf4j
@EnableConfigurationProperties(XxlJobProperties.class)
public class XxlJobAutoConfiguration {

	@Bean
	public XxlJobSpringExecutor xxlJobSpringExecutor(XxlJobProperties xxlJobProperties) {
		log.info(">>>>>>>>>>> xxl-job config init.");
		XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
		xxlJobSpringExecutor.setAdminAddresses(xxlJobProperties.getAdmin().getAddresses());
		XxlExecutorProperties executorProperties = xxlJobProperties.getExecutor();
		xxlJobSpringExecutor.setAppname(executorProperties.getAppname());
		xxlJobSpringExecutor.setIp(executorProperties.getIp());
		xxlJobSpringExecutor.setPort(executorProperties.getPort());
		xxlJobSpringExecutor.setAccessToken(executorProperties.getAccessToken());
		xxlJobSpringExecutor.setLogPath(executorProperties.getLogPath());
		xxlJobSpringExecutor.setLogRetentionDays(executorProperties.getLogRetentionDays());
		xxlJobSpringExecutor.setAddress(executorProperties.getAddress());
		return xxlJobSpringExecutor;
	}

}
