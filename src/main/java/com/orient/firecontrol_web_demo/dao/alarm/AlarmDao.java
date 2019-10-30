package com.orient.firecontrol_web_demo.dao.alarm;

import com.orient.firecontrol_web_demo.model.alarm.AlarmInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author bewater
 * @version 1.0
 * @date 2019/10/16 15:56
 * @func
 */
@Mapper
@Repository
public interface AlarmDao {
    void insert(AlarmInfo alarmInfo);

    AlarmInfo findOne(String deviceCode);

    void updateStatus(@Param("isHandler") String isHandler, @Param("deviceCode") String deviceCode);
}
