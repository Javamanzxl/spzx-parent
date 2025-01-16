package com.atguigu.spzx.product.controller;

import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.product.service.BrandService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ：zxl
 * @Description:
 * @ClassName: BrandController
 * @date ：2024/06/01 19:18
 */
@RestController
@RequestMapping("api/product/brand")
public class BrandController {
    @Resource
    private BrandService brandService;

    /**
     * 查询所有品牌
     * @return
     */
    @GetMapping("findAll")
    public Result<List<Brand>> findAll(){
        List<Brand> brandList = brandService.findAll();
        return Result.build(brandList, ResultCodeEnum.SUCCESS);
    }
}
