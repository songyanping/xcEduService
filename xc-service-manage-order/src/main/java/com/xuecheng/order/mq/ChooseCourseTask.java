package com.xuecheng.order.mq;

import com.rabbitmq.client.Channel;
import com.xuecheng.framework.domain.task.XcTask;
import com.xuecheng.order.config.RabbitMQConfig;
import com.xuecheng.order.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Component
public class ChooseCourseTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChooseCourseTask.class);
    @Autowired
    TaskService taskService;

    //每隔一分钟扫描任务表，向mq发送消息
    @Scheduled(cron = "0/1 * * * * *")
    public void sendChoosecourseTask(){
        LOGGER.info("=======启动定时任务获取订单========");
        //取出当前时间1分钟之前的时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(GregorianCalendar.MINUTE,-1);
        Date time = calendar.getTime();
        List<XcTask> tasks =taskService.findTaskList(time);

        //遍历任务列表
        for (XcTask xcTask:tasks) {
            //发送选课消息
            //任务id
            String taskId = xcTask.getId();
            //任务版本号
            Integer version = xcTask.getVersion();
            //调用乐观锁方法校验任务是否可以执行
            if (taskService.getTask(taskId,version)>0){
                LOGGER.info("发送选课任务至MQ，task id:{}",taskId);
                taskService.publish(xcTask,xcTask.getMqExchange(),xcTask.getMqRoutingkey());
            }

        }
    }

    /**
     * 接收选课响应结果
     *
     */
    @RabbitListener(queues = {RabbitMQConfig.XC_LEARNING_FINISHADDCHOOSECOURSE})
    public void receiveFinishChoosecourseTask(XcTask xcTask, Message message, Channel channel) throws IOException {
        LOGGER.info("接收选课完成响应结果，receiveChoosecourseTask...{}",xcTask.getId());
        String id = xcTask.getId();
        taskService.finishTask(id);
    }
}
