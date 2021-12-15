package ru.on8off.redis.repository.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Element {
    private Long id;
    private String name;
    private Integer value;
}
