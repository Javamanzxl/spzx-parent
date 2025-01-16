package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.base.ProductUnit;
import com.atguigu.spzx.model.entity.product.Product;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProductService {
    PageInfo<Product> findByPage(int page, int limit, ProductDto productDto);

    List<ProductUnit> findAllUnit();

    void save(Product product);

    Product getById(Long id);

    void updateById(Product product);

    void deleteById(Long id);

    void updateAuditStatus(Long id, int auditStatus);

    void updateStatus(Long id, int status);
}
