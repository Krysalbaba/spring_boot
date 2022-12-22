package com.java.nie.config.redisson;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 
 * @ClassName: RedissonAutoConfiguration    
 * @Description: RedissonAutoConfiguration
 * @date 2022年10月9日 上午10:48:33    
 *     
 * @author  Q.JI
 * @version  
 * @since   JDK 1.8
 */

@Configuration
@ConditionalOnClass(Config.class)
@EnableConfigurationProperties
@Slf4j
public class RedissonAutoConfiguration {

	@Autowired
	private RedissonProperties redissonProperties;

	@Bean
	public RedissonClient redisson() {
		if (null != redissonProperties.getCluster().getNodes()) {
			return redissonCluster();
		} else if (!StringUtils.isEmpty(redissonProperties.getHost())) {
			return redissonSingle();
		} else {
			return redissonSentinel();
		}
	}

	/**
	 * 
	 * @Title: redissonCluster
	 * @Description: 集群模式
	 * @return RedissonClient
	 */
	private RedissonClient redissonCluster() {
		log.info("----- Redis Cluster setup... -----");
		Config config = new Config();		
		List<String> clusterNodes = redissonProperties.getCluster().getNodes().stream().map(node -> "redis://" + node).collect(Collectors.toList());
		ClusterServersConfig serverConfig = config.useClusterServers()
				.addNodeAddress(clusterNodes.toArray(new String[clusterNodes.size()]))
				.setTimeout(redissonProperties.getTimeout())
				.setMasterConnectionPoolSize(redissonProperties.getMasterConnectionPoolSize())
				.setPingConnectionInterval(redissonProperties.getPingConnectionInterval())
				.setSlaveConnectionPoolSize(redissonProperties.getSlaveConnectionPoolSize());

		if (StringUtils.isNotBlank(redissonProperties.getPassword())) {
			serverConfig.setPassword(redissonProperties.getPassword());
		}
		return Redisson.create(config);
	}

	/**
	 * 
	 * @Title: redissonSingle
	 * @Description: 单机模式
	 * @return RedissonClient
	 */
	private RedissonClient redissonSingle() {
		log.info("----- Redis Single setup... -----");
		Config config = new Config();
		SingleServerConfig serverConfig = config.useSingleServer()
				.setAddress("redis://" + redissonProperties.getHost() + ":" + redissonProperties.getPort())
				.setTimeout(redissonProperties.getTimeout())
				.setDatabase(redissonProperties.getDatabase())
				.setPingConnectionInterval(redissonProperties.getPingConnectionInterval())
				.setConnectionPoolSize(redissonProperties.getConnectionPoolSize())
				.setConnectionMinimumIdleSize(redissonProperties.getConnectionMinimumIdleSize());

		if (StringUtils.isNotBlank(redissonProperties.getPassword())) {
			serverConfig.setPassword(redissonProperties.getPassword());
		}

		return Redisson.create(config);
	}

	/**
	 * 
	 * @Title: redissonSentinel
	 * @Description: 哨兵模式
	 * @return RedissonClient
	 */
	private RedissonClient redissonSentinel() {
		log.info("----- Redis Sentinel setup... -----");
		Config config = new Config();
		List<String> clusterNodes = redissonProperties.getSentinel().getNodes().stream().map(node -> "redis://" + node).collect(Collectors.toList());
		SentinelServersConfig serverConfig = config.useSentinelServers()
				.setMasterName(redissonProperties.getSentinel().getMaster())
				.addSentinelAddress(clusterNodes.toArray(new String[clusterNodes.size()]));
		if (StringUtils.isNotBlank(redissonProperties.getPassword())) {
			serverConfig.setPassword(redissonProperties.getPassword());
		}
		if (null != redissonProperties.getDatabase()) {
			serverConfig.setDatabase(redissonProperties.getDatabase());
		}
		return Redisson.create(config);
	}
}
