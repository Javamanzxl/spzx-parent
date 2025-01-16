package com.atguigu.spzx.manager.task;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.atguigu.spzx.manager.mapper.OrderInfoMapper;
import com.atguigu.spzx.manager.mapper.OrderStatisticsMapper;
import com.atguigu.spzx.model.entity.order.OrderInfo;
import com.atguigu.spzx.model.entity.order.OrderStatistics;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author ：zxl
 * @Description: 定时任务---统计订单总金额
 * @ClassName: OrderStatisticsTask
 * @date ：2024/04/27 16:02
 */
@Component
@Slf4j
public class OrderStatisticsTask {

    @Resource
    private OrderInfoMapper orderInfoMapper;
    @Resource
    private OrderStatisticsMapper orderStatisticsMapper;

    /**
     * 每天凌晨2点，查询前一天数据，把统计后的数据添加到统计结果表中
     */
    @Scheduled(cron = "0 0 2 * * ?")
    //@Scheduled(cron = "0/10 * * * * ?")
    public void orderTotalAmountStatistics(){
        log.info(new Date().toString());
        //获取前一天的日期
        String afterDay = DateUtil.offsetDay(new Date(), -1).toString("yyyy-MM-dd");
        //根据前一天的日期进行统计(分组)，统计前一天交易金额
        OrderStatistics orderStatistics = orderInfoMapper.selectStatisticsByDate(afterDay);
        //把统计之后的数据，添加到统计结果表中
        if(orderStatistics!=null){
            orderStatisticsMapper.insert(orderStatistics);
        }

    }
}
