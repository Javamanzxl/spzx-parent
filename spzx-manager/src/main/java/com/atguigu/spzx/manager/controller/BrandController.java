package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.BrandService;
import com.atguigu.spzx.model.entity.base.ProductUnit;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ：zxl
 * @Description: 品牌管理
 * @ClassName: BrandController
 * @date ：2024/04/25 10:34
 */
@RestController
@RequestMapping("admin/product/brand")
@Slf4j
public class BrandController {
    @Resource
    private BrandService brandService;

    /**
     * 分页查询所有品牌信息
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/{page}/{limit}")
    public Result<PageInfo<Brand>> findByPage(@PathVariable("page") int page,@PathVariable("limit") int limit){
        PageInfo<Brand> brandPageInfo = brandService.findByPage(page,limit);
        return Result.build(brandPageInfo, ResultCodeEnum.SUCCESS);
    }

    /**
     * 添加新的品牌信息
     * @param brand
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody Brand brand){
        brandService.save(brand);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    /**
     * 修改商品信息
     * @param brand
     * @return
     */
    @PutMapping("updateById")
    public Result updateById(@RequestBody Brand brand){
        brandService.updateById(brand);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    /**
     * 删除品牌
     * @param id
     * @return
     */
    @DeleteMapping("deleteById/{id}")
    public Result deleteById(@PathVariable("id") Long id){
        brandService.deleteById(id);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    /**
     * 查询所有品牌，返回list集合
     * @return
     */
    @GetMapping("/findAll")
    public Result<List<Brand>> findAll(){
        List<Brand> brandList = brandService.findAll();
        return Result.build(brandList,ResultCodeEnum.SUCCESS);
    }


}
