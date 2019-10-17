package com.kgc.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.kgc.provider.dto.GoodsGroup;
import com.kgc.provider.service.IndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @description
 * @return
 * @throws
 * @date 2019/10/17 17:32
 * @since
 */
@RestController
@Api(tags= "首页展示")
@RequestMapping(value = "/index")
public class indexController {
   @Reference
   private IndexService indexService;
    @ApiOperation(value = "showGroup")
    @GetMapping(value = "/showGroup")
    public String showGroup(){
        List<GoodsGroup> list=indexService.showGroup();
        List showGroup=new ArrayList();
        list.forEach(obj->{
            showGroup.add(obj.getGroupName());
        });
     return showGroup.toString();
    }
}
