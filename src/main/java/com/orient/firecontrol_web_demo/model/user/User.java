package com.orient.firecontrol_web_demo.model.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.orient.firecontrol_web_demo.model.organization.OrganizationDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author bewater
 * @version 1.0
 * @date 2019/10/21 10:56
 * @func
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
public class User implements Serializable {
    //序列化id的作用 是为了能够反序列成功  然后可以在进程中进行通信 进行对象传送  所以序列化还是重要的 保证能正常序列化 序列化id就有必要了
    //序列化 可以简单理解成 将对象通过一个输出流写到文件中
    //反序列化 可以简单理解成  将文件中通过输入流 转成一个对象
    private static final long serialVersionUID = -5026805672236044030L;
    @ApiModelProperty(hidden = true)
    private Integer id;

    @ApiModelProperty(name = "account",value = "账号",example = "zhougong",required =true)
    private String account;

    @ApiModelProperty(name = "password",value = "密码",example = "123456",required =true)
    private String password;

    @ApiModelProperty(name = "username",value = "昵称",example = "周工",required =true)
    private String username;

    @ApiModelProperty(hidden = true)
    private Date regTime;  //注册时间

    @ApiModelProperty(hidden = true)
    private Integer enable;     //是否可用  为了避免删除用户会造成对某些信息的影响  选择禁用和启用用户功能

    @ApiModelProperty(name = "organId",value = "单位id",example = "5",required =true)
    private Integer organId;  //所属单位id  用户与单位1对1关系

    @ApiModelProperty(hidden = true)
    private OrganizationDto organization = new OrganizationDto();

}
