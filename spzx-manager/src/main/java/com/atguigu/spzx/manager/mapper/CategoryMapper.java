package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.product.CategoryExcelVo;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

public interface CategoryMapper {
    List<Category> findCategoryList(Long parentId);

    int countByParentId(Long parentId);

    List<Category> selectCategoryAll();

    void batchInsert(List<CategoryExcelVo> categoryExcelVoList);
}
