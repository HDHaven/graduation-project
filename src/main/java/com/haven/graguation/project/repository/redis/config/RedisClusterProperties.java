package com.haven.graguation.project.repository.redis.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Haven
 * @date 2018/03/12
 */
@Component
@ConfigurationProperties(prefix="spring.redis.cluster")
public class RedisClusterProperties {

	private List<String> nodes;
	private Integer maxRedirects;

	public List<String> getNodes() {
		return nodes;
	}

	public void setNodes(List<String> nodes) {
		this.nodes = nodes;
	}

	public Integer getMaxRedirects() {
		return maxRedirects;
	}

	public void setMaxRedirects(Integer maxRedirects) {
		this.maxRedirects = maxRedirects;
	}
	
}
