package com.orient.firecontrol_web_demo.model.device;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author bewater
 * @version 1.0
 * @date 2019/10/16 14:21
 * @func 单相子机bean  用于接受测量的数据值
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
public class Device02 {
    private Integer id;
    private String branchElec;  //支路电流
    private String branchTemp;  //支路接头温度
}
