package com.atguigu.es.demo;

import com.atguigu.es.demo.pojo.User;
import com.atguigu.es.demo.repository.UserRepository;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class EsDemoApplicationTests {

    @Autowired
    private ElasticsearchRestTemplate template;

    @Autowired
    private UserRepository repository;

    @Test
    void contextLoads() {
        this.template.createIndex(User.class);
        this.template.putMapping(User.class);
    }

    @Test
    void saveUser(){
        List<User> userList = Arrays.asList(
                User.builder().id(1L).name("张大锤").age(20).password("123456789").build(),
                User.builder().id(2L).name("张一锤").age(22).password("123456789").build(),
                User.builder().id(3L).name("张二锤").age(23).password("123456789").build(),
                User.builder().id(4L).name("张三锤").age(24).password("123456789").build(),
                User.builder().id(5L).name("张四锤").age(20).password("123456789").build(),
                User.builder().id(5L).name("张锤锤").age(25).password("123456789").build()
        );
        this.repository.saveAll(userList);
    }

    @Test
    void findUser(){
        this.repository.findAll().forEach(System.out::println);
    }

    @Test
    void findByAge(){
        this.repository.findByAgeBetween(22,25).forEach(System.out::println);
        this.repository.findByName("二锤").forEach(System.out::println);
    }

    @Test
    void testNativeQuery(){
        // 自定义查询
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 构建查询
        queryBuilder.withQuery(QueryBuilders.matchQuery("name", "张大锤"));
        // 分页查询
        queryBuilder.withPageable(PageRequest.of(0, 2));
        // 排序
        queryBuilder.withSort(SortBuilders.fieldSort("age").order(SortOrder.DESC));
        // 构建高亮
        queryBuilder.withHighlightBuilder(new HighlightBuilder().field("name").preTags("<em>").postTags("</em>"));

        Page<User> userPage = this.repository.search(queryBuilder.build());
        System.out.println("命中数：" + userPage.getTotalElements());
        System.out.println("总页数:" + userPage.getTotalPages());
        userPage.getContent().forEach(System.out::println);
    }
}
