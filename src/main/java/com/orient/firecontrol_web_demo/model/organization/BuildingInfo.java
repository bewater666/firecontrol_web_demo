package com.orient.firecontrol_web_demo.model.organization;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author bewater
 * @version 1.0
 * @date 2019/10/15 15:56
 * @func
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BuildingInfo {
    @ApiModelProperty(hidden = true)
    private Integer id;
    @ApiModelProperty(value = "建筑编号",name="buildCode",required = true,example = "3201134444")
    private String buildCode;
    @ApiModelProperty(value = "建筑物名称",name="buildName",required = true,example = "南京办公室测试建筑4")
    private String buildName;
}
