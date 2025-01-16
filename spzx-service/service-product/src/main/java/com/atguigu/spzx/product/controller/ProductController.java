package com.atguigu.spzx.product.controller;

import com.atguigu.spzx.model.dto.product.ProductSkuDto;
import com.atguigu.spzx.model.dto.product.SkuSaleDto;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.h5.ProductItemVo;
import com.atguigu.spzx.product.service.ProductService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ：zxl
 * @Description:
 * @ClassName: ProductContoller
 * @date ：2024/06/01 19:18
 */
@RestController
@RequestMapping("api/product")
public class ProductController {
    @Resource
    private ProductService productService;

    /**
     * 根据条件分页查询
     * @param page
     * @param limit
     * @param productSkuDto
     * @return
     */
    @GetMapping(value = "/{page}/{limit}")
    public Result<PageInfo<ProductSku>> findByPage(@PathVariable Integer page, @PathVariable Integer limit, ProductSkuDto productSkuDto){
        PageInfo<ProductSku> productSkuPageInfo = productService.findByPage(page,limit,productSkuDto);
        return Result.build(productSkuPageInfo, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("item/{skuId}")
    public Result<ProductItemVo> item(@PathVariable Long skuId){
        ProductItemVo productItemVo = productService.item(skuId);
        return Result.build(productItemVo,ResultCodeEnum.SUCCESS);
    }

    /**
     * openFeign接口，查询ProductSku信息
     * @param skuId
     * @return
     */
    @GetMapping("/getBySkuId/{skuId}")
    public ProductSku getBySkuId(@PathVariable Long skuId){
        return productService.getBySkuId(skuId);
    }

    @PostMapping("updateSkuSaleNum")
    public Boolean updateSkuSaleNum(@RequestBody List<SkuSaleDto> skuSaleDtoList) {
        return productService.updateSkuSaleNum(skuSaleDtoList);
    }
}
