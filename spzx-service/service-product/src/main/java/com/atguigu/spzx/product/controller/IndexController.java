package com.atguigu.spzx.product.controller;

import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.h5.IndexVo;
import com.atguigu.spzx.product.service.CategoryService;
import com.atguigu.spzx.product.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ：zxl
 * @Description: 首页接口
 * @ClassName: IndexController
 * @date ：2024/05/28 14:44
 */
@Tag(name = "首页接口管理")
@RestController
@RequestMapping("api/product/index")
public class IndexController {
    @Resource
    private ProductService productService;
    @Resource
    private CategoryService categoryService;

    /**
     * 获取首页数据
     * @return
     */
    @GetMapping
    public Result<IndexVo> findIndexData(){
        //获取所有一级分类
        List<Category> categoryList = categoryService.selectOneCategory();

        //根据销量排序，获取前10条数据
        List<ProductSku> productSkuList = productService.selectBySale();

        IndexVo indexVo = new IndexVo();
        indexVo.setCategoryList(categoryList);
        indexVo.setProductSkuList(productSkuList);
        return Result.build(indexVo, ResultCodeEnum.SUCCESS);
    }


}
