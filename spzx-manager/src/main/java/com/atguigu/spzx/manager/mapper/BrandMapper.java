package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.product.Brand;

import java.util.List;

public interface BrandMapper {
    List<Brand> findAll();

    void insert(Brand brand);

    void updateById(Brand brand);

    void deleteById(Long id);
}
