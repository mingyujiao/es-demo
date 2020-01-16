package com.atguigu.es.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author Jmy
 * @date 2020/1/16 16:07
 * @email jiaomingyu5778@gmail.com
 */
@Document(indexName = "user", type = "info", shards = 3, replicas = 2)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    private Long id;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String name;
    @Field(type = FieldType.Integer)
    private int age;
    @Field(type = FieldType.Keyword, index = false)
    private String password;
}
