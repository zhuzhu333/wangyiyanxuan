package com.kgc.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kgc.provider.dto.*;
import com.kgc.provider.mapper.GoodMapper;
import com.kgc.provider.mapper.GoodsGroupMapper;
import com.kgc.provider.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @description
 * @return
 * @throws
 * @date 2019/10/17 19:08
 * @since
 */
@Service
public class IndexServiceImpl implements IndexService {
    @Autowired
    private GoodsGroupMapper goodsGroupMapper;
    @Autowired
    private GoodMapper goodMapper;
    @Override
    public List<GoodsGroup> showGroup() {
        GoodsGroupExample goodsGroupExample=new GoodsGroupExample();
        List<GoodsGroup> list=goodsGroupMapper.selectByExample(goodsGroupExample);
        return list;
    }

    @Override
    public GoodsGroup selectByName(String groupName) {
        GoodsGroupExample goodsGroupExample=new GoodsGroupExample();
        goodsGroupExample.createCriteria().andGroupNameEqualTo(groupName);
        List<GoodsGroup> list=goodsGroupMapper.selectByExample(goodsGroupExample);
        return list.get(0);
    }

    @Override
    public List<Good> selectBySort(int goodSort,int sPage,int pSize) {
        List<Good> list=goodMapper.selectBySort(goodSort,sPage,pSize);
        return list;
    }

    @Override
    public Good selectByGoodName(String goodName) {
        GoodExample goodExample=new GoodExample();
        goodExample.createCriteria().andGoodNameEqualTo(goodName);
        List<Good> list=goodMapper.selectByExample(goodExample);
        return list.get(0);
    }

    @Override
    public Good selectHotGood() {
        Good good=goodMapper.selectHotGood();
        if(null==good){
            good=goodMapper.selectNewDate();
            return good;
        }
        return good;
    }

    @Override
    public List<Good> getAllGoods(String goodName, int sPage, int pSize) {
        return goodMapper.getAllGoods(goodName,sPage,pSize);
    }

    @Override
    public Good randomGood() {
        int i=(int)(Math.random() % 3) + 1;
        List exampleList=new ArrayList();
        exampleList.add("good_stock");
        exampleList.add("good_sales");
        exampleList.add("update_time");
        String example=(String) exampleList.get(i);
        Good good=goodMapper.selectRandom(example);
        return good;
    }


}
