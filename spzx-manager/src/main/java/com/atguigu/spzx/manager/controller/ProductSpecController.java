package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.ProductSpecService;
import com.atguigu.spzx.model.entity.product.ProductSpec;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ：zxl
 * @Description: 商品规格管理
 * @ClassName: ProductSpecController
 * @date ：2024/04/26 8:44
 */
@RestController
@RequestMapping("/admin/product/productSpec")
public class ProductSpecController {
    @Resource
    private ProductSpecService productSpecService;

    /**
     * 分页查询
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/{page}/{limit}")
    public Result<PageInfo<ProductSpec>> findByPage(@PathVariable("page") int page,
                                                    @PathVariable("limit") int limit){
        PageInfo<ProductSpec> productSpecPageInfo = productSpecService.findByPage(page,limit);
        return Result.build(productSpecPageInfo, ResultCodeEnum.SUCCESS);
    }

    /**
     * 修改
     * @param productSpec
     * @return
     */
    @PutMapping("/updateById")
    public Result updateById(@RequestBody ProductSpec productSpec){
        productSpecService.updateById(productSpec);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    /**
     * 新加
     * @param productSpec
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody ProductSpec productSpec){
        productSpecService.save(productSpec);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable("id")Long id){
        productSpecService.deleteById(id);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
    @GetMapping("findAll")
    public Result<List<ProductSpec>> findAll(){
        List<ProductSpec> productSpecList = productSpecService.findAll();
        return Result.build(productSpecList,ResultCodeEnum.SUCCESS);
    }

}
