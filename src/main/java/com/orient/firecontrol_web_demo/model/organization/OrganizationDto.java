package com.orient.firecontrol_web_demo.model.organization;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author bewater
 * @version 1.0
 * @date 2019/10/25 10:05
 * @func
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrganizationDto {
    @ApiModelProperty(name = "id",value = "id",required = true,example = "5")
    private Integer id;

    @ApiModelProperty(name = "organizationName",value = "单位名称",required = true,example = "测试单位1")
    private String organizationName;    //单位名称
}
