package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.CategoryBrandMapper;
import com.atguigu.spzx.manager.service.CategoryBrandService;
import com.atguigu.spzx.manager.service.CategoryService;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.entity.product.CategoryBrand;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：zxl
 * @Description:
 * @ClassName: CategoryBrandServiceImpl
 * @date ：2024/04/25 19:11
 */
@Service
public class CategoryBrandServiceImpl implements CategoryBrandService {
    @Resource
    private CategoryBrandMapper categoryBrandMapper;

    @Override
    public PageInfo<CategoryBrand> findByPage(int page, int limit,CategoryBrand categoryBrand) {
        PageHelper.startPage(page,limit);
        List<CategoryBrand> categoryBrandList = categoryBrandMapper.findByPage(categoryBrand);
        return new PageInfo<CategoryBrand>(categoryBrandList);
    }

    @Override
    public void save(CategoryBrand categoryBrand) {
        categoryBrandMapper.save(categoryBrand);
    }

    @Override
    public void updateById(CategoryBrand categoryBrand) {
        categoryBrandMapper.updateById(categoryBrand);
    }

    @Override
    public void deleteById(Long id) {
        categoryBrandMapper.deleteById(id);
    }

    @Override
    public List<Brand> findBrandByCategoryId(Long categoryId) {
        List<Brand> brandList = categoryBrandMapper.findBrandByCategoryId(categoryId);
        return brandList;
    }
}
