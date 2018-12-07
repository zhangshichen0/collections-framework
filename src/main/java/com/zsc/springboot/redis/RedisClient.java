package com.zsc.springboot.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author zhouqisheng
 *
 *         2017年5月27日
 *
 */
public class RedisClient extends JedisCluster {


	public RedisClient(Set<HostAndPort> nodes, GenericObjectPoolConfig poolConfig) {
		super(nodes, poolConfig);
	}

	/**
	 * lock
	 * @param key
	 * @param expireSecond
	 * @return
	 */
	public String lock(String key, long expireSecond) {
		final String value = String.valueOf(System.currentTimeMillis());
		final String nxxx = "NX";
		final String expx = "EX";
		boolean ret;
		if (expireSecond <= 0L) {
			ret = setnx(key, value) > 0L;
		} else {
			ret = "ok".equalsIgnoreCase(set(key, value, nxxx, expx, expireSecond));
		}
		if (ret) {
			return value;
		} else {
			return null;
		}
	}

	/**
	 * lock
	 * @param key
	 * @param expireMilisecond
	 * @return
	 */
	public String lockPx(String key, long expireMilisecond) {
		final String value = String.valueOf(System.currentTimeMillis());
		final String nxxx = "NX";
		final String expx = "PX";
		boolean ret;
		if (expireMilisecond <= 0L) {
		    ret = setnx(key, value) > 0L;
		} else {
		    ret = "ok".equalsIgnoreCase(set(key, value, nxxx, expx, expireMilisecond));
		}
		if (ret) {
			return value;
		} else {
			return null;
		}
	}
	
    /**
     * 获取锁，自选等待直到获得锁
     * @param key
     * @param expireSecond
     * @return
     */
    public String lockWait(String key, long expireSecond) {
        
        String nxxx = "NX";
        String expx = "EX";
        boolean ret = false;
        String value = null;
        while (!ret) {
            value = String.valueOf(System.currentTimeMillis());
            if (expireSecond <= 0L) {
                ret = setnx(key, value) > 0L;
            } else {
                ret = "ok".equalsIgnoreCase(set(key, value, nxxx, expx, expireSecond));
            }
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                return null;
            }
        }
       
       return value;
    }
    
    /**
     * 获取锁，获取不到则等待
     * @param key
     * @param expireSecond
     * @param waitMilisencond 获取锁最大等待时间
     * @return
     */
    public String lockWait(String key, long expireSecond, long waitMilisencond) {
        
        String nxxx = "NX";
        String expx = "EX";
        boolean ret = false;
        String value = null;
        long start = System.currentTimeMillis();
        while (!ret && System.currentTimeMillis() - start < waitMilisencond) {
            value = String.valueOf(System.currentTimeMillis());
            if (expireSecond <= 0L) {
                ret = setnx(key, value) > 0L;
            } else {
                ret = "ok".equalsIgnoreCase(set(key, value, nxxx, expx, expireSecond));
            }
            
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                return null;
            }
        }
       
       return value;
    }

	/**
	 * unlock 
	 * @param key
	 * @param keySign
	 * @return
	 */
	public boolean unlock(final String key, final String keySign) {
		if (keySign != null && keySign.length() > 0) {
			String val = get(key);
			if (!keySign.equals(val)) {
				return true;
			}
		}
		if (del(key) <= 0) {
		    return !exists(key);
		}
		return true;
	}

}
