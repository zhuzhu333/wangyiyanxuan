package com.kgc.provider.service.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.kgc.provider.dto.GoodsGroup;
import com.kgc.provider.dto.GoodsGroupExample;
import com.kgc.provider.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;

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
    @Override
    public List<GoodsGroup> showGroup() {
        GoodsGroupExample goodsGroupExample=new GoodsGroupExample();
        List<GoodsGroup> list=goodsGroupMapper.selectByExample(goodsGroupExample);
        return list;
    }
}
