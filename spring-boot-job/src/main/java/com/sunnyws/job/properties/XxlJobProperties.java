package com.sunnyws.job.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/*
* @Description //TODO
* @Author qinlang
* @Date 9:22 2020/9/14
**/

@Data
@ConfigurationProperties(prefix = "xxl.job", ignoreUnknownFields = true)
public class XxlJobProperties {

	@NestedConfigurationProperty
	private XxlAdminProperties admin = new XxlAdminProperties();

	@NestedConfigurationProperty
	private XxlExecutorProperties executor = new XxlExecutorProperties();

}