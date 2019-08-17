package com.project.es_search.User;

import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "lib")
public class User {

    private String id;

    private String name;

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
