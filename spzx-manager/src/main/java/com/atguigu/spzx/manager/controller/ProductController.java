package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.ProductService;
import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.base.ProductUnit;
import com.atguigu.spzx.model.entity.product.Product;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ：zxl
 * @Description: 商品管理
 * @ClassName: ProductController
 * @date ：2024/04/26 15:26
 */
@RestController
@RequestMapping("admin/product/product")
public class ProductController {
    @Resource
    private ProductService productService;

    /**
     * 分页查询 and 条件查询
     * @param page
     * @param limit
     * @param productDto
     * @return
     */
    @GetMapping("{page}/{limit}")
    public Result<PageInfo<Product>> findByPage(@PathVariable("page") int page,
                                                @PathVariable("limit") int limit,
                                                ProductDto productDto){
        PageInfo<Product> productPageInfo = productService.findByPage(page,limit,productDto);
        return Result.build(productPageInfo, ResultCodeEnum.SUCCESS);
    }
    /**
     *查询所有商品计量单位的单元数据
     * @return
     */
    @GetMapping("/findAllUnit")
    public Result<List<ProductUnit>> findAllUnit(){
        List<ProductUnit> productUnitList = productService.findAllUnit();
        return Result.build(productUnitList,ResultCodeEnum.SUCCESS);
    }

    /**
     * 新增商品
     * @param product
     * @return
     */
    @PostMapping("save")
    public Result save(@RequestBody Product product){
        productService.save(product);
        return Result.success();
    }

    /**
     * 根据商品id查询信息
     * @param id
     * @return
     */
    @GetMapping("getById/{id}")
    public Result<Product> getById(@PathVariable("id") Long id){
        Product product = productService.getById(id);
        return Result.build(product,ResultCodeEnum.SUCCESS);
    }

    /**
     * 根据商品id修改数据
     * @param product
     * @return
     */
    @PutMapping("updateById")
    public Result updateById(@RequestBody Product product){
        productService.updateById(product);
        return Result.success();
    }

    /**
     * 根据id删除商品数据
     * @param id
     * @return
     */
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable("id") Long id){
        productService.deleteById(id);
        return Result.success();
    }

    /**
     * 商品审核管理
     * @param id
     * @param auditStatus
     * @return
     */
    @GetMapping("/updateAuditStatus/{id}/{auditStatus}")
    public Result updateAuditStatus(@PathVariable("id") Long id,
                                    @PathVariable("auditStatus") int auditStatus){
        productService.updateAuditStatus(id,auditStatus);
        return Result.success();
    }
    @GetMapping("/updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable("id") Long id,
                               @PathVariable("status") int status){
        productService.updateStatus(id,status);
        return Result.success();
    }
}
