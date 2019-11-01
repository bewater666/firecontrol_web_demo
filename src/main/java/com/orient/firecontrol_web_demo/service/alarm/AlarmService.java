package com.orient.firecontrol_web_demo.service.alarm;

import com.orient.firecontrol_web_demo.dao.alarm.AlarmDao;
import com.orient.firecontrol_web_demo.model.alarm.AlarmInfo;
import com.orient.firecontrol_web_demo.model.common.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author bewater
 * @version 1.0
 * @date 2019/10/31 17:27
 * @func
 */
@Service
public class AlarmService {
    @Autowired
    private AlarmDao alarmDao;

    /**
     * 查看告警信息列表
     * @return
     */
    public ResultBean list(){
        List<AlarmInfo> all = alarmDao.findAll();
        if (all.size()==0){
            return new ResultBean(200, "查询成功,当前无告警信息", null);
        }
        return new ResultBean(200, "查询告警信息成功", all);
    }
}
