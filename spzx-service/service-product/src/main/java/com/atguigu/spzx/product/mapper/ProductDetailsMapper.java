package com.atguigu.spzx.product.mapper;

import com.atguigu.spzx.model.entity.product.ProductDetails;

public interface ProductDetailsMapper {
    ProductDetails findBySkuId(Long id);
}
