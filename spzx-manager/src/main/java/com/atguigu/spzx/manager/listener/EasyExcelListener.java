package com.atguigu.spzx.manager.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.atguigu.spzx.manager.mapper.CategoryMapper;
import com.atguigu.spzx.model.vo.product.CategoryExcelVo;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * @author ：zxl
 * @Description: EasyExcel的监听器, 官方参考文档:  https://easyexcel.opensource.alibaba.com/docs/current/quickstart/read
 * @ClassName: EasyExcelListener
 * @date ：2024/04/24 20:56
 */
public class EasyExcelListener<T> implements ReadListener<T> {

    //构造传递mapper,操作数据库
    private CategoryMapper categoryMapper;

    public EasyExcelListener(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    /**
     * 缓存的数据
     */
    private List<T> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    /**
     * 从第二行开始读取，把每行读取内容封装到t对象里面
     *
     * @param t
     * @param analysisContext
     */
    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        //把每行数据对象t放入cachedDataList集合
        cachedDataList.add(t);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData(cachedDataList);
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        //保存数据,如果cachedDataList没有达到BATCH_COUNT，确保最后遗留的数据也存储到数据库
        saveData(cachedDataList);
    }

    /**
     * 保存方法
     * @param cachedDataList
     */
    private void saveData(List<T> cachedDataList){
        categoryMapper.batchInsert((List<CategoryExcelVo>) cachedDataList);
    }
}
