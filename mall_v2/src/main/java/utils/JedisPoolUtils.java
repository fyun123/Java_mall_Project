package utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JedisPoolUtils {
    private static JedisPool jedisPool;
    static {
        //读取配置文件
        InputStream inputStream = JedisPoolUtils.class.getClassLoader().getResourceAsStream("jedis.properties");

        //创建properties对象
        Properties properties = new Properties();
        //关联文件
        try {
            assert inputStream != null;
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取数据，设置到JedisPoolConfig中
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(Integer.parseInt((String)properties.get("maxTotal")));
        jedisPoolConfig.setMaxIdle(Integer.parseInt((String)properties.get("maxIdle")));
        //初始化JedisPool
        jedisPool = new JedisPool(jedisPoolConfig, (String) properties.get("host"),Integer.parseInt((String)properties.get("port")));
    }
    //获取连接
    public static Jedis getJedis(){
        return jedisPool.getResource();
    }
}
