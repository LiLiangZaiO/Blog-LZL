package com.lzl.esservice.mq;

import com.lzl.esservice.constants.MqConstants;
import com.lzl.esservice.service.EsService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BlogListener {

    @Autowired
    private EsService esService;

    /**
     * 监听酒店新增或修改的业务
     * @param id
     */
    @RabbitListener(queues = MqConstants.BLOG_INSERT_QUEUE)
    public void  listenBlogInsertOrUpdate(String id){
        esService.insertById(id);
    }

    /**RabbitListener
     * 监听酒店删除的业务
     * @param id
     */
    @RabbitListener(queues = MqConstants.BLOG_DELETE_QUEUE)
    public void  listenBlogDelete(String id){
        esService.deleteById(id);
    }


}
