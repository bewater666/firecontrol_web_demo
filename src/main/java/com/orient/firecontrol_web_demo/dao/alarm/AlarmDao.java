package com.orient.firecontrol_web_demo.dao.alarm;

import com.orient.firecontrol_web_demo.model.alarm.AlarmInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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


    /**
     * 查询所有的告警  用于告警管理
     * @return
     */
    List<AlarmInfo> findAll();

    /**
     * 查看已处理(处理成功)的列表
     * @return
     */
    List<AlarmInfo> findHasHandler();


    /**
     * 查看 处理失败的告警信息
     * @return
     */
    List<AlarmInfo> findHandlerBad();


    /**
     * 查看未处理的告警信息列表
     * @return
     */
    List<AlarmInfo> findUnHandler();

    /**
     * 根据设备id  查询他的告警信息列表
     * @param deviceCode
     * @return
     */
    List<AlarmInfo> findByDeviceCode(String deviceCode);

    /**
     * 计数某告警级别的数量
     * @param alarmGrade
     * @return
     */
    int countNum(String alarmGrade);

    /**
     * 根据单位id  查询该单位下的报警信息
     * @param organId
     * @return
     */
    List<AlarmInfo> findByOrganId(Integer organId);
}
