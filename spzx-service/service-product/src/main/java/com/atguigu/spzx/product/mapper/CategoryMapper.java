package com.atguigu.spzx.product.mapper;

import com.atguigu.spzx.model.entity.product.Category;
import jakarta.annotation.Resource;

import java.util.List;

public interface CategoryMapper {

    List<Category> selectOneCategory();


    List<Category> findAll();
}
