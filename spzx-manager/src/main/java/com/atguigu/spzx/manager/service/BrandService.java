package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.entity.base.ProductUnit;
import com.atguigu.spzx.model.entity.product.Brand;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BrandService {
    PageInfo<Brand> findByPage(int page, int limit);

    void save(Brand brand);

    void updateById(Brand brand);

    void deleteById(Long id);

    List<Brand> findAll();

}
