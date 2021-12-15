package ru.on8off.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.on8off.redis.repository.KeyValueRepository;
import ru.on8off.redis.repository.dao.Element;

import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class KeyValueIT {
    @Autowired
    private KeyValueRepository keyValueRepository;

    @Test
    public void testParam(){
        var paramId = "testId-" + UUID.randomUUID();
        var param = "test";

        var result = keyValueRepository.getParameter(paramId);
        assertNull(result);

        keyValueRepository.setParameter(paramId, param);
        result = keyValueRepository.getParameter(paramId);
        assertEquals(param, result);

        keyValueRepository.setParameter(paramId, null);
        result = keyValueRepository.getParameter(paramId);
        assertNull(result);
    }

    @Test
    public void testElement(){
        Long id = new Random().nextLong();
        var element = new Element(id, "Test", 1);

        var result = keyValueRepository.getElement(id);
        assertNull(result);

        keyValueRepository.setElement(element.getId(), element);
        result = keyValueRepository.getElement(id);
        assertEquals(element, result);

        keyValueRepository.setElement(id, null);
        result = keyValueRepository.getElement(id);
        assertNull(result);
    }
}