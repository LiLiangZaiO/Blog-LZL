package com.lzl.esservice.controller;

import com.lzl.common_utils.domain.R;
import com.lzl.esservice.entity.RequestParams;
import com.lzl.esservice.service.EsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/es")
public class EsController {

    @Autowired
    private EsService esService;

    /**
     * 自动补全
     * @param prefix
     * @return
     */
    @GetMapping("suggestion")
    public R getSuggestions(@RequestParam("key") String prefix) {
        return esService.getSuggestions(prefix);
    }

    @PostMapping("list")
    public R search(@RequestBody RequestParams params){
         return esService.search(params);
    }


    @GetMapping("/uploadDocument")
    public R uploadDocument(){
        return esService.uploadDocument();
    }



}
