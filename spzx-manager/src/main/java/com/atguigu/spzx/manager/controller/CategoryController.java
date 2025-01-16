package com.atguigu.spzx.manager.controller;

import cn.hutool.http.server.HttpServerResponse;
import com.atguigu.spzx.manager.service.CategoryService;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author ：zxl
 * @Description: 商品类别
 * @ClassName: CategoryController
 * @date ：2024/04/24 18:47
 */
@RestController
@RequestMapping("/admin/product/category")
@Slf4j
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    /**
     * 分类列表查询，每次查询一层数据
     * @param parentId
     * @return
     */
    @GetMapping("/findCategoryList/{parentId}")
    public Result<List<Category>> findCategoryList(@PathVariable("parentId")Long parentId){
        List<Category> categoryList = categoryService.findCategoryList(parentId);
        return Result.build(categoryList, ResultCodeEnum.SUCCESS);
    }

    /**
     * 导出列表数据，放到Excel表格中
     * @param response
     * @return
     */
    @GetMapping("/exportData")
    public Result exportData(HttpServletResponse response){
        categoryService.exportData(response);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    /**
     * 从Excel中导入数据
     * @param file
     * @return
     */
    @PostMapping("/importData")
    public Result importData(MultipartFile file){
        categoryService.importData(file);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }


}
