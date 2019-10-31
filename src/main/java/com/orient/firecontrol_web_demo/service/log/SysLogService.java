package com.orient.firecontrol_web_demo.service.log;

import com.orient.firecontrol_web_demo.dao.log.SysLogDao;
import com.orient.firecontrol_web_demo.model.common.ResultBean;
import com.orient.firecontrol_web_demo.model.log.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author bewater
 * @version 1.0
 * @date 2019/10/31 15:42
 * @func
 */
@Service
public class SysLogService {
    @Autowired
    private SysLogDao sysLogDao;


    public ResultBean list(){
        List<SysLog> all = sysLogDao.findAll();
        if (all.size()==0){
            return new ResultBean(200, "查询成功,当前没有系统日志", null);
        }
        return new ResultBean(200, "查询日志列表成功", all);
    }

}
