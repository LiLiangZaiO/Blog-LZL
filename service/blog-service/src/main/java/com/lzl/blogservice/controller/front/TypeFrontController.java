package com.lzl.blogservice.controller.front;

import com.lzl.blogservice.entity.Vo.TypeFrontVo;
import com.lzl.blogservice.service.TypeService;
import com.lzl.common_utils.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/front/type")
public class TypeFrontController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/getTypeList")
    public R getTypeList(){

        List<TypeFrontVo> typeFrontVoList =typeService.getTypeList();

        return R.ok().data("typeList",typeFrontVoList);

    }

}
