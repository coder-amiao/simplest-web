package cn.soboys.restapispringbootstarter.aop;

import cn.soboys.restapispringbootstarter.annotation.Limit;
import cn.soboys.restapispringbootstarter.config.RestApiProperties;
import cn.soboys.restapispringbootstarter.enums.LimitType;
import cn.soboys.restapispringbootstarter.exception.LimitAccessException;
import cn.soboys.restapispringbootstarter.utils.HttpUserAgent;
import cn.soboys.restapispringbootstarter.utils.Strings;
import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/7/2 11:28
 * @webSite https://github.com/coder-amiao
 */
@Slf4j
@Aspect
@Component
public class LimitAspect  extends BaseAspectSupport{
    @Resource
    private  RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RestApiProperties.RedisProperties redisProperties;

    public LimitAspect() {
    }

    @Pointcut("@annotation(cn.soboys.restapispringbootstarter.annotation.Limit)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Method method = resolveMethod(point);
        Limit limitAnnotation = method.getAnnotation(Limit.class);
        LimitType limitType = limitAnnotation.limitType();
        String name = limitAnnotation.name();
        String key;
        String ip = HttpUserAgent.getIpAddr();
        int limitPeriod = limitAnnotation.period();
        int limitCount = limitAnnotation.count();
        switch (limitType) {
            case IP:
                key = ip;
                break;
            case CUSTOMER:
                key = limitAnnotation.key();
                break;
            default:
                key = StringUtils.upperCase(method.getName());
        }
        if(redisProperties!=null&&StringUtils.isNotBlank(redisProperties.getKeyPrefix())){
            key=redisProperties.getKeyPrefix()+Strings.COLON+key;
        }
        ImmutableList<String> keys = ImmutableList.of(StringUtils.join(limitAnnotation.prefix() + Strings.UNDER_LINE, key, ip));
        String luaScript = buildLuaScript();
        RedisScript<Long> redisScript = new DefaultRedisScript<>(luaScript, Long.class);
        Long count = redisTemplate.execute(redisScript, keys, limitCount, limitPeriod);
        if (count != null && count.intValue() <= limitCount) {
            log.info("IP:{} 第 {} 次访问key为 {}，描述为 [{}] 的接口", ip, count, keys, name);
            return point.proceed();
        } else {
            log.error("key为 {}，描述为 [{}] 的接口访问超出频率限制", keys, name);
            throw new LimitAccessException("访问频率过快请稍后再试");
        }
    }

    /**
     * 限流脚本
     * 调用的时候不超过阈值，则直接返回并执行计算器自加。
     *
     * @return lua脚本
     */
    private String buildLuaScript() {
        return "local c" +
                "\nc = redis.call('get',KEYS[1])" +
                "\nif c and tonumber(c) > tonumber(ARGV[1]) then" +
                "\nreturn c;" +
                "\nend" +
                "\nc = redis.call('incr',KEYS[1])" +
                "\nif tonumber(c) == 1 then" +
                "\nredis.call('expire',KEYS[1],ARGV[2])" +
                "\nend" +
                "\nreturn c;";
    }
}
