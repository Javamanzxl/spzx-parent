package com.atguigu.spzx.product.service.serviceImpl;

import cn.hutool.core.collection.CollectionUtil;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.product.mapper.CategoryMapper;
import com.atguigu.spzx.product.service.CategoryService;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author ：zxl
 * @Description:
 * @ClassName: CategoryServiceImpl
 * @date ：2024/05/28 14:46
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> selectOneCategory() {

        return categoryMapper.selectOneCategory();
    }


    //key ： category::all
    @Cacheable(value = "category",key = "'all'")  //注解在方法上，表示该方法的返回结果是可以缓存的
    @Override
    public List<Category> findCategoryTree() {
        //查询所有分类 返回List集合
        List<Category> allCategoryList = categoryMapper.findAll();
        //遍历所有集合，通过条件parentId = 0得到所有一级分类
        List<Category> oneCategoryList = allCategoryList.stream().filter(category -> category.getParentId() == 0)
                .toList();
        //遍历所有一级分类集合，根据条件id = parentId 得到所有二级分类
        if(!CollectionUtil.isEmpty(oneCategoryList)){
            oneCategoryList.forEach(oneCategory -> {
                List<Category> twoCategoryList = allCategoryList.stream().filter(category -> Objects.equals(category.getParentId(),oneCategory.getId()))
                        .toList();
                oneCategory.setChildren(twoCategoryList);
                //遍历所有二级分类集合，根据条件id = parentId 得到所有三级分类
                if(!CollectionUtil.isEmpty(twoCategoryList)){
                    twoCategoryList.forEach(twoCategory->{
                        List<Category> threeCategoryList = allCategoryList.stream().filter(category -> Objects.equals(category.getParentId(),twoCategory.getId()))
                                .toList();
                        twoCategory.setChildren(threeCategoryList);
                    });
                }
            });
        }
        return oneCategoryList;
    }
}
