package ru.on8off.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.on8off.redis.repository.ElementRepository;
import ru.on8off.redis.repository.entity.Element;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ElementRepositoryIT {
    @Autowired
    ElementRepository elementRepository;

    @Test
    void crud() {
        var element1 = new Element(1L, "Test1", 1 );
        var element2 = new Element(2L, "Test2", 2 );
        elementRepository.save(element1);
        elementRepository.save(element2);
        var list = toList(elementRepository.findAll());
        assertEquals(2, list.size());
        assertTrue(list.contains(element1));
        assertTrue(list.contains(element2));

        assertEquals(element1, elementRepository.findById(1L).get());
        assertEquals(element2, elementRepository.findById(2L).get());

        elementRepository.deleteById(1L);
        elementRepository.deleteById(2L);
        assertEquals(0, toList(elementRepository.findAll()).size());
    }

    private List<Element> toList(Iterable<Element> iterable){
        return StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
    }

}