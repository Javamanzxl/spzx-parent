package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.entity.product.ProductSpec;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProductSpecService {
    PageInfo<ProductSpec> findByPage(int page, int limit);

    void updateById(ProductSpec productSpec);

    void save(ProductSpec productSpec);

    void deleteById(Long id);

    List<ProductSpec> findAll();
}
