package com.xuecheng.order.service;

import com.xuecheng.framework.domain.task.XcTask;
import com.xuecheng.framework.domain.task.XcTaskHis;
import com.xuecheng.order.dao.XcTaskHisRepository;
import com.xuecheng.order.dao.XcTaskRepository;
import org.hibernate.annotations.common.reflection.XClass;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    XcTaskRepository xcTaskRepository;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    XcTaskHisRepository xcTaskHisRepository;


    //取出前n条任务，取出指定时间之前处理得任务
    public List<XcTask> findTaskList(Date updateTime){
        //设置分页参数/取出前n条记录
        List<XcTask> all = xcTaskRepository.findByUpdateTimeBefore(updateTime);
        return all;
    }


    /**
     * 向mq发送消息
     * @Param xcTask对象
     * @Param ex 交换机id
     * @param routingKey
     *
     */
    @Transactional
    public void publish(XcTask xcTask,String ex,String routingKey){
        //查询任务
        Optional<XcTask> optionalXcTask= xcTaskRepository.findById(xcTask.getId());
        if (optionalXcTask.isPresent()){
            rabbitTemplate.convertAndSend(ex,routingKey,xcTask);
            //更新任务时间为当前时间
            xcTask.setUpdateTime(new Date());
            xcTaskRepository.save(xcTask);
        }
    }

    //乐观锁
    @Transactional
    public int getTask(String taskId,int version){
        int i = xcTaskRepository.updateTaskVersion(taskId,version);
        return i;
    }

    //删除任务
    @Transactional
    public void finishTask(String taskId){
        Optional<XcTask> optional =xcTaskRepository.findById(taskId);
        if (optional.isPresent()){
            XcTask xcTask = optional.get();
            xcTask.setDeleteTime(new Date());
            XcTaskHis xcTaskHis = new XcTaskHis();
            BeanUtils.copyProperties(xcTask,xcTaskHis);
            xcTaskHisRepository.save(xcTaskHis);
            xcTaskRepository.delete(xcTask);
        }
    }
}
