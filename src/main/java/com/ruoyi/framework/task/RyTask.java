package com.ruoyi.framework.task;

import com.ruoyi.project.system.task.mapper.BjTaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ruoyi.common.utils.StringUtils;

/**
 * 定时任务调度测试
 * 
 * @author ruoyi
 */
@Component("ryTask")
public class RyTask
{
    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i)
    {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void ryParams(String params)
    {
        System.out.println("执行有参方法：" + params);
    }

    public void ryNoParams()
    {
        System.out.println("执行无参方法");
    }

    public void ryNoParamsBj()
    {
        //检查bj_task表中任务的执行状态,更新为已完成  (可先手动修改数据库)
        System.out.println("bj定时任务");
    }



    @Autowired
    private BjTaskMapper bjTaskMapper;

    public void deleteTasks() {
        //agv一共有01、02、03、04、05、06、07、08、09、10这十个,检查bj_task表中是否存在某个AGV的任务是080且已经执行完成(状态为2),且未被删除,如果存在则执行删除当前AGV的所有任务
        String[] agvs = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10"};
        for (String agv : agvs) {
            if (checkIfTaskExists(agv)) {
                deleteTasksByAgv(agv);
            }
        }
        System.out.println("bj定时任务");
    }

    // 检查是否存在已完成的 080 任务
    private boolean checkIfTaskExists(String agvNo) {
        return bjTaskMapper.countCompletedTask080(agvNo) > 0;
    }

    // 删除该 AGV 的所有任务
    private void deleteTasksByAgv(String agvNo) {
        try {
            bjTaskMapper.deleteTasksByAgvNo(agvNo);
            System.out.println("AGV: " + agvNo + " 的所有任务已删除");
        } catch (Exception e) {
            System.err.println("删除 AGV " + agvNo + " 的任务失败：" + e.getMessage());
        }
    }


}
