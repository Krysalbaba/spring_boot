package com.java.nie.config.redisson;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 
 * @ClassName: RedissonProperties    
 * @Description: redisson通用配置
 * @date 2022年10月9日 上午08:48:48    
 *     
 * @author  Q.JI
 * @version  
 * @since   JDK 1.8
 */

@Component
@ConfigurationProperties(prefix = "spring.redis")
@Data
public class RedissonProperties {

	private String host;
	
	private String port;
	
	private String password;
	
	private Integer database;

	private ClusterProperties cluster = new ClusterProperties();
	
	private SentinelProperties sentinel = new SentinelProperties();
	
	private int timeout = 10000;

	private int connectionPoolSize = 64;

	private int connectionMinimumIdleSize = 10;

	private int slaveConnectionPoolSize = 250;

	private int masterConnectionPoolSize = 250;

	private int pingConnectionInterval = 1000;

	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	public class ClusterProperties {
		private List<String> nodes;		
	}
	
	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@Setter
	public class SentinelProperties {
		private String master;
		private List<String> nodes;
	}
}
