package com.atguigu.spzx.manager.test;

import com.alibaba.excel.EasyExcel;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.product.CategoryExcelVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：zxl
 * @Description: EasyExcel快速入门
 * @ClassName: EasyExcelTest
 * @date ：2024/04/24 19:46
 */
public class EasyExcelTest {
    public static void main(String[] args) {
        //read();
        write();
    }


    //读
    public static void read() {
        //1.定义读取excel文件的位置
        String fileName = "D:/01.xlsx";
        //2.调用方法
        EasyExcelListener<CategoryExcelVo> easyExcelListener = new EasyExcelListener<>();
        EasyExcel.read(fileName, CategoryExcelVo.class,easyExcelListener)
                .sheet().doRead();
        List<CategoryExcelVo> data = easyExcelListener.getData();
        System.out.println(data);
    }

    //写
    public static void write() {
        ArrayList<CategoryExcelVo> list = new ArrayList<>();
        list.add(new CategoryExcelVo(1L , "数码办公" , "",0L, 1, 1)) ;
        list.add(new CategoryExcelVo(11L , "华为手机" , "",1L, 1, 2)) ;
        EasyExcel.write("D:/02.xlsx", CategoryExcelVo.class)
                .sheet("分类数据").doWrite(list);
    }
}
