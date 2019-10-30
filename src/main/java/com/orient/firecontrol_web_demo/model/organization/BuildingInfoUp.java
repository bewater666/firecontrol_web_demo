package com.orient.firecontrol_web_demo.model.organization;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author bewater
 * @version 1.0
 * @date 2019/10/30 10:32
 * @func
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BuildingInfoUp {
    @ApiModelProperty(value = "id",name="id",required = true,example = "9")
    private Integer id;
    @ApiModelProperty(value = "建筑编号",name="buildCode",required = true,example = "1111111112")
    private String buildCode;
    @ApiModelProperty(value = "建筑物名称",name="buildName",required = true,example = "测试单位测试更新建筑1")
    private String buildName;
}
