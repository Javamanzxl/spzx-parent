package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.CategoryBrandService;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.entity.product.CategoryBrand;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ：zxl
 * @Description: 商品分类管理
 * @ClassName: CategoryBrandController
 * @date ：2024/04/25 19:10
 */
@RestController
@RequestMapping("admin/product/categoryBrand")
public class CategoryBrandController {
    @Resource
    private CategoryBrandService categoryBrandService;

    /**
     * 分类商品分页查询and条件分页查询
     * @param page
     * @param limit
     * @param categoryBrand
     * @return
     */
    @GetMapping("/{page}/{limit}")
    public Result<PageInfo<CategoryBrand>> findByPage(@PathVariable("page") int page,
                                                      @PathVariable("limit") int limit,
                                                      CategoryBrand categoryBrand){
        PageInfo<CategoryBrand> categoryBrandPageInfo = categoryBrandService.findByPage(page,limit,categoryBrand);
        return Result.build(categoryBrandPageInfo, ResultCodeEnum.SUCCESS);
    }

    /**
     * 添加新的分类品牌
     * @param categoryBrand
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody CategoryBrand categoryBrand){
        categoryBrandService.save(categoryBrand);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    /**
     * 更新
     * @param categoryBrand
     * @return
     */
    @PutMapping("updateById")
    public Result updateById(@RequestBody CategoryBrand categoryBrand){
        categoryBrandService.updateById(categoryBrand);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable("id") Long id){
        categoryBrandService.deleteById(id);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    /**
     * 根据分类id查询商品信息
     * @param categoryId
     * @return
     */
    @GetMapping("/findBrandByCategoryId/{categoryId}")
    public Result<List<Brand>> findBrandByCategoryId(@PathVariable("categoryId") Long categoryId){
        List<Brand> brandList = categoryBrandService.findBrandByCategoryId(categoryId);
        return Result.build(brandList,ResultCodeEnum.SUCCESS);
    }
}
