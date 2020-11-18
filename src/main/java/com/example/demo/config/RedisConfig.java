package com.example.demo.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.HashSet;
import java.util.Set;

/**
 * com.example.demo.config
 *
 * @author ypl
 * @create 2020-08-12 09:44
 */
@Configuration
//放这貌似不好使
//@EnableAutoConfiguration(exclude = {RedisAutoConfiguration.class, RedisReactiveAutoConfiguration.class})

//最好配置文件写
//spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
public class RedisConfig {

    @Value("${spring.redis.lone.port}")
    public Integer lonePort;
    @Value("${spring.redis.lone.host}")
    public String loneHost;
    @Value("${spring.redis.lone.password}")
    public String lonePassword;

    @Value("${spring.redis.cluster.nodes}")
    public String clusterNodes;
    @Value("${spring.redis.cluster.password}")
    public String clusterPassword;


    /**
     * 单一数据源
     */

//    @Bean
//    @SuppressWarnings("all")
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
//        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
//        template.setConnectionFactory(factory);
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//        // key采用String的序列化方式
//        template.setKeySerializer(stringRedisSerializer);
//        // hash的key也采用String的序列化方式
//        template.setHashKeySerializer(stringRedisSerializer);
//        // value序列化方式采用jackson
//        //也可以自定义序列化方式
//        //实现implements RedisSerializer<T> 重写serialize  deserialize 方法
//
//        template.setValueSerializer(jackson2JsonRedisSerializer);
//        // hash的value序列化方式采用jackson
//        template.setHashValueSerializer(jackson2JsonRedisSerializer);
//        template.afterPropertiesSet();
//        return template;
//    }




    /**
     * 多数据源
     * 设置
     * 参考：  https://blog.csdn.net/larger5/article/details/105915130
     */


    /**
     * 单机版配置
     * @return
     */
    @Bean("loneRedisFactory")
    public LettuceConnectionFactory loneRedisFactory() {
        //lone config
        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();//单机的配置config
        //从配置文件读取
        //主机
        standaloneConfiguration.setHostName(loneHost);
        standaloneConfiguration.setDatabase(0);
        //端口
        standaloneConfiguration.setPort(lonePort);
        standaloneConfiguration.setPassword(RedisPassword.of(lonePassword));
        return new LettuceConnectionFactory(standaloneConfiguration);
    }

    /**
     *
     * 集群版的配置
     * @return
     */
    @Bean("clusterRedisFactory")
    public LettuceConnectionFactory clusterRedisFactory() {
        //cluster config
        RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration();
        String[] split = clusterNodes.split(",");
        //nodes节点
        Set<RedisNode> redisNodes =new HashSet<>();
        for (int i = 0; i < split.length; i++) {
            redisNodes.add(new RedisNode(split[i].split(":")[0],Integer.valueOf(split[i].split(":")[1])));
        }
        //设置集群节点
        clusterConfiguration.setClusterNodes(redisNodes);
        //设置密码
        clusterConfiguration.setPassword(RedisPassword.of(clusterPassword));
        return new LettuceConnectionFactory(clusterConfiguration);
    }

    /**
     * clusterRedisTemplate
     * @return
     */
    @Bean("clusterRedisTemplate")
    public RedisTemplate<String, Object> clusterRedisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        //设置连接factory
        template.setConnectionFactory(clusterRedisFactory());
        return template;
    }


    /**
     * loneRedisTemplate
     * @return
     */
    @Bean("loneRedisTemplate")
    public RedisTemplate<String, Object> loneRedisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        //设置连接factory
        template.setConnectionFactory(loneRedisFactory());
        return template;
    }

    //需要设置默认的？？
    @Bean("redisTemplate")
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        //设置连接factory
        template.setConnectionFactory(loneRedisFactory());
        return template;
    }




}
