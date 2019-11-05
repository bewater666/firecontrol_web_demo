package com.orient.firecontrol_web_demo.model.organization;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author bewater
 * @version 1.0
 * @date 2019/11/5 15:29
 * @func 某建筑物下的楼层bean
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FloorInfo {
    private Integer id;
    private String buildCode; //建筑物编号
    private Integer floorCode; //楼层编号 例:5
    private String floorName;   //楼层名称 例:5楼
}
