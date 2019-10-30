package com.orient.firecontrol_web_demo.model.device;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author bewater
 * @version 1.0
 * @date 2019/10/16 14:17
 * @func 主控设备bean  用于接受测量的数据值
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
public class Device01 {
    private Integer id;
    private String voltageA; //A相电压
    private String voltageB; //B相电压
    private String voltageC;  //C相电压
    private String remainElec;  //剩余电流
    private String boxTemp;  //配电箱环境温度
}
