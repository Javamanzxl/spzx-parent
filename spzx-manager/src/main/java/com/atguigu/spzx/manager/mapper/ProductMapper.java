package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.base.ProductUnit;
import com.atguigu.spzx.model.entity.product.Product;
import com.atguigu.spzx.model.entity.product.ProductDetails;
import com.atguigu.spzx.model.entity.product.ProductSku;

import java.util.List;

public interface ProductMapper {
    List<Product> findByPage(ProductDto productDto);

    List<ProductUnit> findAllUnit();

    void saveProduct(Product product);

    void saveDeatilsImageUrls(ProductDetails productDetails);

    void saveSku(ProductSku productSku);

    Product getById(Long id);

    List<ProductSku> getSkuById(Long id);

    ProductDetails getDetailsById(Long id);

    void updateById(Product product);

    void updateSkuById(ProductSku sku);

    void updateDetailsById(ProductDetails productDetails);

    void deleteById(Long id);

    void deleteSkuById(Long id);

    void deleteDetailsById(Long id);

}
