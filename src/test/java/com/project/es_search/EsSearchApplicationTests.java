package com.project.es_search;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.project.es_search.User.User;
import net.minidev.json.JSONUtil;
import org.apache.commons.beanutils.BeanUtilsBean2;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsSearchApplicationTests {

    @Autowired
    private ElasticsearchTemplate template;


    /**
     * 测试分页查询
     */
    @Test
    public void contextLoads() {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQuery("name","jack"))
                .withPageable(new PageRequest(0,1)).build();
        List<User> res = template.queryForList(searchQuery, User.class);
        System.out.println(res);
    }


    /**
     * 创建索引
     */
    @Test
    public void addIndex() {
        template.createIndex("store");
    }


    /**
     * 删除文档
     */
    @Test
    public void del() throws ExecutionException, InterruptedException {
        DeleteRequest delete = new DeleteRequest();
        delete.id("1");
        ActionFuture<DeleteResponse> deleteRes = template.getClient().prepareDelete("lib", "user", "2").execute();
        System.out.println(deleteRes.get().status());
    }


    /**
     * 更新文档
     */
    @Test
    public void update() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        User user = new User();
        user.setId("0000000000");
        user.setName("中华浩洋888888888");
        UpdateRequest updateRe = new UpdateRequest();
        Map map = BeanUtilsBean2.getInstance().describe(user);
        updateRe.doc(map);
        UpdateQuery update = new  UpdateQueryBuilder().withUpdateRequest(updateRe).withIndexName("lib").withType("user").withId("4").build();
        UpdateResponse res = template.update(update);
        System.out.println(res);
    }


    /**
     * 插入文档
     */
    @Test
    public void insert() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        User user = new User();
        user.setId("0000000000");
        user.setName("中华浩洋66666666");
        UpdateRequest updateRe = new UpdateRequest();
        Map map = BeanUtilsBean2.getInstance().describe(user);
        updateRe.doc(map);
        ActionFuture<IndexResponse> res = template.getClient().prepareIndex("lib", "user", "10").setSource(map).execute();
        System.out.println(res);
    }








}
