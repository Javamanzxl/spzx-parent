package com.atguigu.spzx.product.service;

import com.atguigu.spzx.model.entity.product.Category;

import java.util.List;

/**
 * @author ：zxl
 * @Description: 类别Service
 * @ClassName: CategoryService
 * @date ：2024/05/28 14:45
 */
public interface CategoryService {
    List<Category> selectOneCategory();

    List<Category> findCategoryTree();
}
