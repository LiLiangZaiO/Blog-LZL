package com.lzl.esservice.service.impl;

import com.lzl.common_utils.domain.R;
import com.lzl.common_utils.utils.JsonUtils;
import com.lzl.esservice.entity.Blog;
import com.lzl.esservice.entity.BlogDoc;
import com.lzl.esservice.entity.RequestParams;
import com.lzl.esservice.entity.Suggestion;
import com.lzl.esservice.service.EsService;
import com.lzl.feign.clients.BlogClient;
import com.lzl.feign.entity.BlogFrontVo;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EsServiceImpl implements EsService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private BlogClient blogClient;

    @Override
    public R search(RequestParams params) {
        SearchResponse response = null;
        try {
            // 1.准备Request
            SearchRequest request = new SearchRequest("blog");
            // 2.准备请求参数
            // 2.1.query
            String key = params.getKey();
            BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
            if (StringUtils.isNotBlank(key)) {
                // 不为空，根据关键字查询
                boolQuery.must(QueryBuilders.matchQuery("all", key));
            } else {
                // 为空，查询所有
                boolQuery.must(QueryBuilders.matchAllQuery());
            }
            String typeName = params.getTypeName();
            if (StringUtils.isNotBlank(typeName)) {
                boolQuery.filter(QueryBuilders.termQuery("typeName", typeName));
            }

            request.source().query(boolQuery);

            // 2.2.分页
            int page = params.getPage();
            int size = params.getSize();
            request.source().from((page - 1) * size).size(size);

            // 3.发送请求
            response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
            // 4.解析响应
            return handleResponse(response);
        } catch (IOException e) {
            throw new RuntimeException("搜索数据失败", e);
        }
    }

    @Override
    public R uploadDocument() {
        try {
            List<BlogFrontVo> blogList = blogClient.getAllBlogFrontVo();
            // 1.准备Request
            BulkRequest request = new BulkRequest();

            for (BlogFrontVo blogFrontVo : blogList) {
                Blog blog = new Blog();
                BeanUtils.copyProperties(blogFrontVo, blog);
                BlogDoc blogDoc = new BlogDoc(blog);
                // 2.2.转json
                String json = JsonUtils.toString(blogDoc);
                // 2.3.添加请求
                request.add(new IndexRequest("blog").id(blogFrontVo.getId()).source(json, XContentType.JSON));
            }
            // 3.发送请求
            restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return R.ok();
    }

    /**
     * 自动补全
     *
     * @param prefix
     * @return
     */
    @Override
    public R getSuggestions(String prefix) {
        try {
            // 1.准备Request
            SearchRequest request = new SearchRequest("blog");
            // 2.准备DSL
            request.source().suggest(new SuggestBuilder().addSuggestion(
                    "suggestions",
                    SuggestBuilders.completionSuggestion("suggestion")
                            .prefix(prefix)
                            .skipDuplicates(true)
                            .size(10)
            ));
            // 3.发起请求
            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
            // 4.解析结果
            Suggest suggest = response.getSuggest();
            // 4.1.根据补全查询名称，获取补全结果
            CompletionSuggestion suggestions = suggest.getSuggestion("suggestions");
            // 4.2.获取options
            List<CompletionSuggestion.Entry.Option> options = suggestions.getOptions();
            // 4.3.遍历
            List<Suggestion> list = new ArrayList<>(options.size());
            for (CompletionSuggestion.Entry.Option option : options) {
                Suggestion suggestion = new Suggestion();
                String text = option.getText().toString();
                suggestion.setValue(text);
                list.add(suggestion);
            }

            return R.ok().data("list", list);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //MQ消息--删除数据
    @Override
    public void deleteById(String id) {
        try {
            DeleteRequest request = new DeleteRequest("blog", id);
            restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //MQ消息--更新数据
    @Override
    public void insertById(String id) {
        try {
            // 0.根据id查询酒店数据
            BlogFrontVo blogFrontVo = blogClient.getBlogEsById(id);
            // 转换为文档类型
            Blog blog = new Blog();
            BeanUtils.copyProperties(blogFrontVo, blog);
            BlogDoc blogDoc = new BlogDoc(blog);
            // 1.准备Request对象
            IndexRequest request = new IndexRequest("blog").id(blog.getId());
            // 2.准备Json文档
            request.source(JsonUtils.toString(blogDoc), XContentType.JSON);
            // 3.发送请求
            restHighLevelClient.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private R handleResponse(SearchResponse response) {

        SearchHits searchHits = response.getHits();
        // 4.1.总条数
        long total = searchHits.getTotalHits().value;
        // 4.2.获取文档数组
        SearchHit[] hits = searchHits.getHits();
        // 4.3.遍历
        List<BlogDoc> blogs = new ArrayList<>(hits.length);
        for (SearchHit hit : hits) {
            // 4.4.获取source
            String json = hit.getSourceAsString();
            // 4.5.反序列化，非高亮的
            BlogDoc blogDoc = JsonUtils.toBean(json, BlogDoc.class);
            // 4.6.处理高亮结果
            // 1)获取高亮map
            Map<String, HighlightField> map = hit.getHighlightFields();
            if (map != null && !map.isEmpty()) {
                // 2）根据字段名，获取高亮结果
                HighlightField highlightField = map.get("title");
                if (highlightField != null) {
                    // 3）获取高亮结果字符串数组中的第1个元素
                    String Title = highlightField.getFragments()[0].toString();
                    // 4）把高亮结果放到HotelDoc中
                    blogDoc.setTitle(Title);
                }
            }

            // 4.9.放入集合
            blogs.add(blogDoc);
        }
        return R.ok().data("total", total).data("blogs", blogs);

    }


}
