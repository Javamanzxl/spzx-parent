package com.atguigu.spzx.manager.test;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：zxl
 * @Description: EasyExcel监听器
 * @ClassName: EasyExcelListener
 * @date ：2024/04/24 19:49
 */
public class EasyExcelListener<T> extends AnalysisEventListener<T> {

    private List<T> data = new ArrayList<>();
    /**
     * 读取excel内容
     * 从第二行开始读取，把每行读取内容封装装到T对象中去
     * @param t
     * @param analysisContext
     */
    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        data.add(t);
    }

    public List<T> getData(){
        return data;
    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
