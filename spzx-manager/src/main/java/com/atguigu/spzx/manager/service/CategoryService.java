package com.atguigu.spzx.manager.service;

import cn.hutool.http.server.HttpServerResponse;
import com.atguigu.spzx.model.entity.product.Category;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {
    List<Category> findCategoryList(Long parentId);

    void exportData(HttpServletResponse response);

    void importData(MultipartFile file);
}
