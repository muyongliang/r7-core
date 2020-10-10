package com.r7.core.trigger.model;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @Description quartz自带jobDetail 的详细信息
 * @author wutao
 * @date 2020/9/28
 */
@ApiModel(description="quartz自带jobDetail 的详细信息")
@Data
@TableName(value = "QRTZ_JOB_DETAILS")
@EqualsAndHashCode(callSuper = true)
public class QrtzJobDetails extends Model<QrtzJobDetails> {
    /**
     * 调度名称
     */
    @TableId(value = "SCHED_NAME")
    @ApiModelProperty(value="调度名称")
    private String schedName;

    /**
     * 集群中job的名字
     */
    @ApiModelProperty(value="集群中job的名字")
    private String jobName;

    /**
     * 集群中job的所属组的名字
     */
    @ApiModelProperty(value="集群中job的所属组的名字")
    private String jobGroup;

    /**
     * 详细描述信息
     */
    @ApiModelProperty(value="详细描述信息")
    private String description;

    /**
     * 集群中job的全限定名
     */
    @ApiModelProperty(value="集群中job的全限定名")
    private String jobClassName;

    /**
     * 是否持久化,把该属性设置为1，quartz会把job持久化到数据库中
     */
    @ApiModelProperty(value="是否持久化,把该属性设置为1，quartz会把job持久化到数据库中")
    private String isDurable;

    /**
     * 是否并发执行
     */
    @ApiModelProperty(value="是否并发执行")
    private String isNonconcurrent;

    /**
     * 是否更新数据
     */
    @ApiModelProperty(value="是否更新数据")
    private String isUpdateData;

    /**
     * 是否接受恢复执行，默认为false，设置了RequestsRecovery为true，则该job会被重新执行
     */
    @ApiModelProperty(value="是否接受恢复执行，默认为false，设置了RequestsRecovery为true，则该job会被重新执行")
    private String requestsRecovery;

    /**
     * 一个blob字段，存放持久化job对象
     */
    @ApiModelProperty(value="一个blob字段，存放持久化job对象")
    private byte[] jobData;
}