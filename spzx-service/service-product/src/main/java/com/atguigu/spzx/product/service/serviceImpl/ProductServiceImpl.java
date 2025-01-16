package com.atguigu.spzx.product.service.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.model.dto.product.ProductSkuDto;
import com.atguigu.spzx.model.dto.product.SkuSaleDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.atguigu.spzx.model.entity.product.ProductDetails;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.vo.h5.ProductItemVo;
import com.atguigu.spzx.product.mapper.ProductDetailsMapper;
import com.atguigu.spzx.product.mapper.ProductMapper;
import com.atguigu.spzx.product.mapper.ProductSkuMapper;
import com.atguigu.spzx.product.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author ：zxl
 * @Description: 商品Service
 * @ClassName: ProductServiceImpl
 * @date ：2024/05/28 14:53
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductSkuMapper productSkuMapper;
    @Resource
    private ProductMapper productMapper;
    @Resource
    private ProductDetailsMapper productDetailsMapper;

    @Override
    public List<ProductSku> selectBySale() {
        return productSkuMapper.selectBySale();

    }

    @Override
    public PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto) {
        PageHelper.startPage(page,limit);
        List<ProductSku> productSkuList = productSkuMapper.findByPage(productSkuDto);
        return new PageInfo<>(productSkuList);
    }

    @Override
    public ProductItemVo item(Long skuId) {
        ProductItemVo productItemVo = new ProductItemVo();
        //根据skuId获取sku信息
        ProductSku productSku = productSkuMapper.findBySkuId(skuId);
        if(!Objects.isNull(productSku)){
            //根据获取的sku信息的id获取商品信息
            Product product = productMapper.findById(productSku.getProductId());
            //根据获取的sku信息的id获取商品详细信息
            ProductDetails productDetails = productDetailsMapper.findBySkuId(productSku.getId());
            //封装map集合，商品规格对应商品skuId信息
            List<ProductSku> productSkuList = productSkuMapper.findByProductId(productSku.getProductId());
            HashMap<String, Object> map = new HashMap<>();
            productSkuList.forEach(item -> {
                map.put(item.getSkuSpec(),item.getId());
            });
            //设置productItemVo
            productItemVo.setProduct(product);
            productItemVo.setProductSku(productSku);
            productItemVo.setDetailsImageUrlList(Arrays.asList(productDetails.getImageUrls().split(",")));
            productItemVo.setSliderUrlList(Arrays.asList(product.getSliderUrls().split(",")));
            productItemVo.setSpecValueList(JSON.parseArray(product.getSpecValue()));
            productItemVo.setSkuSpecValueMap(map);
        }

        return productItemVo;
    }

    @Override
    public ProductSku getBySkuId(Long skuId) {
        return productSkuMapper.findBySkuId(skuId);
    }

    @Override
    public Boolean updateSkuSaleNum(List<SkuSaleDto> skuSaleDtoList) {
        if(!CollectionUtils.isEmpty(skuSaleDtoList)) {
            for(SkuSaleDto skuSaleDto : skuSaleDtoList) {
                productSkuMapper.updateSale(skuSaleDto.getSkuId(), skuSaleDto.getNum());
                return true;
            }
        }
        return false;
    }
}
