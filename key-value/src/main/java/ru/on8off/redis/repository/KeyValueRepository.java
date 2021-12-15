package ru.on8off.redis.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import ru.on8off.redis.repository.dao.Element;

import java.util.concurrent.TimeUnit;

@Repository
public class KeyValueRepository {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate<String, Element> elementRedisTemplate;
    @Value("${application.keys.parameter}")
    private String PARAMETER_KEY;
    @Value("${application.keys.element}")
    private String ELEMENT_KEY;
    @Value("${application.expirationMs.parameter}")
    private Long PARAMETER_KEY_EXP;
    @Value("${application.expirationMs.element}")
    private Long ELEMENT_KEY_EXP;


    public String getParameter(String id){
        return stringRedisTemplate.opsForValue().get(PARAMETER_KEY + id);
    }

    public void setParameter(String id, String value){
        if(value != null) {
            stringRedisTemplate.opsForValue().set(PARAMETER_KEY + id, value, PARAMETER_KEY_EXP, TimeUnit.MILLISECONDS);
        } else {
            stringRedisTemplate.delete(PARAMETER_KEY + id);
        }

    }

    public Element getElement(Long id){
        return elementRedisTemplate.opsForValue().get(ELEMENT_KEY + id);
    }

    public void setElement(Long id, Element element){
        if(element != null) {
            elementRedisTemplate.opsForValue().set(ELEMENT_KEY + id, element, ELEMENT_KEY_EXP, TimeUnit.MILLISECONDS);
        } else {
            elementRedisTemplate.delete(ELEMENT_KEY + id);
        }
    }

}
