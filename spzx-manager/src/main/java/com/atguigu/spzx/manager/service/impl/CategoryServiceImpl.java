package com.atguigu.spzx.manager.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.server.HttpServerResponse;
import com.alibaba.excel.EasyExcel;
import com.atguigu.spzx.common.exception.MyException;
import com.atguigu.spzx.manager.listener.EasyExcelListener;
import com.atguigu.spzx.manager.mapper.CategoryMapper;
import com.atguigu.spzx.manager.service.CategoryService;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.product.CategoryExcelVo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：zxl
 * @Description: 商品类别
 * @ClassName: CategoryServiceImpl
 * @date ：2024/04/24 18:48
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> findCategoryList(Long parentId) {
        List<Category> categoryList = categoryMapper.findCategoryList(parentId);
        //遍历返回list集合，判断每个分类是否有下一层分类，如果有设置hasChild属性为true
        if (!CollectionUtil.isEmpty(categoryList)) {
            categoryList.forEach(category -> {
                category.setHasChildren(false);
                int count = categoryMapper.countByParentId(category.getId());
                if (count > 0) {
                    category.setHasChildren(true);
                }
            });
        }
        return categoryList;
    }

    @Override
    public void exportData(HttpServletResponse response) {

        try {
            //1.设置响应头信息和其他信息
                // 设置响应结果类型
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
                // URLEncoder.encode可以防止中文乱码
            String fileName = null;
            fileName = URLEncoder.encode("分类数据", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            //2.调用mapper方法查询所有分类
            List<Category> categoryList = categoryMapper.selectCategoryAll();
            ArrayList<CategoryExcelVo> categoryExcelVoArrayList = new ArrayList<>(categoryList.size());
                // 将从数据库中查询到的Category对象转换成CategoryExcelVo对象
            categoryList.forEach(category -> {
                CategoryExcelVo categoryExcelVo = new CategoryExcelVo();
                BeanUtils.copyProperties(category, categoryExcelVo);
                categoryExcelVoArrayList.add(categoryExcelVo);
            });
            //3.调用EasyExcel的write方法完成写操作
            EasyExcel.write(response.getOutputStream(), CategoryExcelVo.class).sheet("分类数据")
                    .doWrite(categoryExcelVoArrayList);
        } catch (Exception e) {
            throw new MyException(ResultCodeEnum.DATA_ERROR);
        }

    }

    @Override
    public void importData(MultipartFile file) {
        try {
            EasyExcelListener<CategoryExcelVo> easyExcelListener = new EasyExcelListener<>(categoryMapper);
            EasyExcel.read(file.getInputStream(), CategoryExcelVo.class,easyExcelListener).sheet().doRead();
        } catch (Exception e) {
            throw new MyException(ResultCodeEnum.DATA_ERROR);
        }
    }
}
