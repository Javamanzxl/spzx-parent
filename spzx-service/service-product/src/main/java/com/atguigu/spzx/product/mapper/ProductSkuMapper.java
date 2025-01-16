package com.atguigu.spzx.product.mapper;

import com.atguigu.spzx.model.dto.product.ProductSkuDto;
import com.atguigu.spzx.model.entity.product.ProductSku;

import java.util.List;

public interface ProductSkuMapper {
    List<ProductSku> selectBySale();

    List<ProductSku> findByPage(ProductSkuDto productSkuDto);

    ProductSku findBySkuId(Long skuId);

    List<ProductSku> findByProductId(Long productId);

    void updateSale(Long skuId, Integer num);
}
