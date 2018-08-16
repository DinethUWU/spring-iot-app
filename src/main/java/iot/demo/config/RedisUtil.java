package iot.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@ComponentScan("iot.demo")
public class RedisCacheConfig {

    private static JedisPool jedisPool;
    /**
     * Initialize Jedis Pool with Default Timeout
     */
    public void initializeJedisPool() {
        if (jedisPool == null) {
            jedisPool = new JedisPool(new JedisPoolConfig(),
                   "localhost",
                    6379,
                    100);

        }
    }

    /**
     * Method to return Jedis Pool. If the pool is null, it calls
     * initialize method and then returns the pool
     * @return {@link JedisPool}
     */
    public JedisPool getJedisPool() {
        if (jedisPool == null) {
            initializeJedisPool();
        }
        return jedisPool;
    }

    /**
     * Method to close and detroy Jedis Pool
     */
    public void destroyJedisPool() {
        if (jedisPool != null && !jedisPool.isClosed()) {
            jedisPool.close();
            jedisPool.destroy();
        }
    }



}
