package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.ProductMapper;
import com.atguigu.spzx.manager.service.ProductService;
import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.base.ProductUnit;
import com.atguigu.spzx.model.entity.product.Product;
import com.atguigu.spzx.model.entity.product.ProductDetails;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author ：zxl
 * @Description:商品管理业务逻辑处理
 * @ClassName: ProductServiceImpl
 * @date ：2024/04/26 15:25
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductMapper productMapper;

    @Override
    public PageInfo<Product> findByPage(int page, int limit, ProductDto productDto) {
        PageHelper.startPage(page,limit);
        List<Product> productList =  productMapper.findByPage(productDto);
        return new PageInfo<>(productList);
    }

    @Override
    public List<ProductUnit> findAllUnit() {
        List<ProductUnit> unitList = productMapper.findAllUnit();
        return unitList;
    }

    @Override
    public void save(Product product) {
        product.setStatus(0);
        product.setAuditStatus(0);
        productMapper.saveProduct(product);
        //保存图片详细地址
        String detailsImageUrls = product.getDetailsImageUrls();
        ProductDetails productDetails = new ProductDetails();
        productDetails.setImageUrls(detailsImageUrls);
        productDetails.setProductId(product.getId());

        productMapper.saveDeatilsImageUrls(productDetails);
        //保存sku
        List<ProductSku> productSkuList = product.getProductSkuList();
        productSkuList.forEach(productSku -> {
            String uuid = UUID.randomUUID().toString().replace("-", "");
            productSku.setSkuCode(product.getId()+"_"+uuid);
            productSku.setProductId(product.getId());
            productSku.setSkuName(product.getName()+productSku.getSkuSpec());
            productSku.setSaleNum(0);
            productSku.setStatus(0);
            productMapper.saveSku(productSku);
        });
    }

    @Override
    public Product getById(Long id) {
        //根据id查询商品基本信息 product
        Product product = productMapper.getById(id);
        //根据id查询商品sku商品列表 product_sku
        List<ProductSku> productSkuList = productMapper.getSkuById(id);
        //根据id查询商品详情数据 product_details
        ProductDetails productDetails = productMapper.getDetailsById(id);
        //封装数据
        product.setProductSkuList(productSkuList);
        product.setDetailsImageUrls(productDetails.getImageUrls());
        return product;
    }

    @Override
    public void updateById(Product product) {
        //更新product数据
        productMapper.updateById(product);
        //更新product_sku数据
        List<ProductSku> productSkus = product.getProductSkuList();
        productSkus.forEach(sku -> {
            productMapper.updateSkuById(sku);
        });
        //更新product_details数据
        String detailsImageUrls = product.getDetailsImageUrls();
        ProductDetails productDetails = productMapper.getDetailsById(product.getId());
        productDetails.setImageUrls(detailsImageUrls);
        productMapper.updateDetailsById(productDetails);
    }

    @Override
    public void deleteById(Long id) {
        productMapper.deleteById(id);
        productMapper.deleteSkuById(id);
        productMapper.deleteDetailsById(id);
    }

    @Override
    public void updateAuditStatus(Long id, int auditStatus) {
        Product product = new Product();
        product.setId(id);
        if(auditStatus == 1){
            product.setAuditStatus(1);
            product.setAuditMessage("审核通过");
        }else{
            product.setAuditStatus(-1);
            product.setAuditMessage("审核不通过");
        }
        productMapper.updateById(product);
    }

    @Override
    public void updateStatus(Long id, int status) {
        Product product = new Product();
        product.setId(id);
        if(status == 1){
            product.setStatus(1);
        }else{
            product.setStatus(-1);
        }
        productMapper.updateById(product);
    }
}
