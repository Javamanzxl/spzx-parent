package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.product.ProductSpec;

import java.util.List;

public interface ProductSpecMapper {
    List<ProductSpec> findByPage();

    void updateById(ProductSpec productSpec);

    void save(ProductSpec productSpec);

    void deleteById(Long id);

    List<ProductSpec> findAll();
}
