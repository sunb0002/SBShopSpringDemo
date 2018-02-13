/**
 * 
 */
package com.madoka.sunb0002.config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.google.common.cache.CacheBuilder;
import com.madoka.sunb0002.config.Constants.LocalCache;

/**
 * 
 * @author Sun Bo
 *
 */
@Configuration
@EnableCaching
public class CacheConfig {

	@Primary
	@Bean
	public CacheManager guavaCacheMgr() {

		SimpleCacheManager cacheMgr = new SimpleCacheManager();
		List<GuavaCache> cacheList = new ArrayList<>();

		GuavaCache cache1 = new GuavaCache(LocalCache.CACHE1,
				CacheBuilder.newBuilder().maximumSize(50).initialCapacity(5).expireAfterAccess(10, TimeUnit.SECONDS)
						.expireAfterWrite(2, TimeUnit.MINUTES).build());
		cacheList.add(cache1);
		GuavaCache cache2 = new GuavaCache(LocalCache.CACHE2,
				CacheBuilder.newBuilder().maximumSize(100).initialCapacity(10).expireAfterAccess(20, TimeUnit.SECONDS)
						.expireAfterWrite(1, TimeUnit.MINUTES).build());
		cacheList.add(cache2);

		cacheMgr.setCaches(cacheList);
		return cacheMgr;
	}

}
