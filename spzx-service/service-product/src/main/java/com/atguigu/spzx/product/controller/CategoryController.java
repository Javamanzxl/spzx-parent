package com.atguigu.spzx.product.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.product.mapper.CategoryMapper;
import com.atguigu.spzx.product.service.CategoryService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.StringUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author ：zxl
 * @Description:
 * @ClassName: CategoryController
 * @date ：2024/05/29 20:26
 */
@RestController
@RequestMapping("api/product/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 查询所有分类，属性结构封装
     *
     * @return
     */
    @GetMapping("findCategoryTree")
    public Result<List<Category>> findCategoryTree() {
//        String categoryJson = redisTemplate.opsForValue().get("category:one");
//        if(StringUtils.hasText(categoryJson)){
//            List<Category> categoryList = JSON.parseArray(categoryJson, Category.class);
//            return Result.build(categoryList, ResultCodeEnum.SUCCESS);
//        }
        List<Category> categoryList = categoryService.findCategoryTree();
//        redisTemplate.opsForValue().set("category:one",JSON.toJSONString(categoryList),7,TimeUnit.DAYS);
        return Result.build(categoryList, ResultCodeEnum.SUCCESS);
    }
}
