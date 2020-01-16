package com.atguigu.es.demo.repository;

import com.atguigu.es.demo.pojo.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author Jmy
 * @date 2020/1/16 16:27
 * @email jiaomingyu5778@gmail.com
 */
public interface UserRepository extends ElasticsearchRepository<User, Long> {

    List<User> findByAgeBetween(Integer age1, Integer arg2);
    List<User> findByName(String name);
}
