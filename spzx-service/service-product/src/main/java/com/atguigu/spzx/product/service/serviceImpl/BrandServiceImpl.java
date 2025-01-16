package com.atguigu.spzx.product.service.serviceImpl;

import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.product.mapper.BrandMapper;
import com.atguigu.spzx.product.service.BrandService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：zxl
 * @Description:
 * @ClassName: BrandServiceImpl
 * @date ：2024/06/01 19:21
 */
@Service
public class BrandServiceImpl implements BrandService {
    @Resource
    private BrandMapper brandMapper;

    @Override
    public List<Brand> findAll() {

        return brandMapper.findAll();
    }
}
