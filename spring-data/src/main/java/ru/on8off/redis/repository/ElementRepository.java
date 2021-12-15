package ru.on8off.redis.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import ru.on8off.redis.repository.entity.Element;

public interface ElementRepository extends CrudRepository<Element, Long>, QueryByExampleExecutor<Element> {}
