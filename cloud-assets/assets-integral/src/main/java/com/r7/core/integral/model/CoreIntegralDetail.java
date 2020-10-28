package com.r7.core.integral.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.r7.core.integral.dto.CoreIntegralDetailDTO;
import com.r7.core.integral.vo.CoreIntegralDetailVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 
 * @Description 积分详情表
 * @author wt
 * 
 */
@Data
@ApiModel(value="积分详情实体")
@TableName(value = "core_integral_detail")
@EqualsAndHashCode(callSuper = true)
public class CoreIntegralDetail extends Model<CoreIntegralDetail> {


    /**
     * id
     */
    @TableId(value = "id")
    @ApiModelProperty(value="id")
    private Long id;

    /**
     * 平台ID
     */
    @ApiModelProperty(value="平台ID")
    private Long appId;

    /**
     * 用户id
     */
    @ApiModelProperty(value="用户id")
    private Long userId;

    /**
     * 积分来源类型 游戏1/完成系统任务2/权益转换积分3/现金购买积分4/用户注册成功赠送积分5/权益商品卖出6/
     */
    @ApiModelProperty(value="积分来源类型 游戏1/完成系统任务2/权益转换积分3/现金购买积分4/用户注册成功赠送积分5/权益商品卖出6/")
    private Integer sourceType;

    /**
     * 业务编号 多次调接口区分
     */
    @ApiModelProperty(value="业务编号 多次调接口区分")
    private String businessCode;

    /**
     * 当前积分值
     */
    @ApiModelProperty(value="当前积分值")
    private Integer laveNum;

    /**
     * 变动积分数 变动积分数就是代表增加时增加了多少积分，使用时使用了多少积分
     */
    @ApiModelProperty(value="变动积分数 变动积分数就是代表增加时增加了多少积分，使用时使用了多少积分")
    private Integer changeNum;

    /**
     * 操作类型 增加1/减少2
     */
    @ApiModelProperty(value="操作类型 增加1/减少2")
    private Integer operateType;

    /**
     * 日期 yyyyMMdd
     */
    @ApiModelProperty(value="日期 yyyyMMdd")
    private Integer detailedDate;

    /**
     * 积分描述
     */
    @ApiModelProperty(value="积分描述")
    private String description;

    /**
     * 创建人
     */
    @ApiModelProperty(value="创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private LocalDateTime createdAt;

    /**
     * 更新人
     */
    @ApiModelProperty(value="更新人")
    private Long updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value="更新时间")
    private LocalDateTime updateAt;

    public void toCoreIntegralDetailDTO(CoreIntegralDetailDTO coreIntegralDetailDTO){

        this.setUserId(coreIntegralDetailDTO.getUserId());
        this.setSourceType(coreIntegralDetailDTO.getSourceType());
        this .setBusinessCode(coreIntegralDetailDTO.getBusinessCode());
        this.setLaveNum(coreIntegralDetailDTO.getLaveNum());
        this.setChangeNum(coreIntegralDetailDTO.getChangeNum());
        this.setOperateType(coreIntegralDetailDTO.getOperateType());
        this.setDetailedDate(coreIntegralDetailDTO.getDetailedDate());
        this.setDescription(coreIntegralDetailDTO.getDescription());
    }

    public CoreIntegralDetailVO toCoreIntegralDetailVO(){
        CoreIntegralDetailVO coreIntegralDetailVO = new CoreIntegralDetailVO();
        coreIntegralDetailVO.setAppId(this.getAppId());
        coreIntegralDetailVO.setUserId(this.getUserId());
        coreIntegralDetailVO.setSourceType(this.getSourceType());
        coreIntegralDetailVO.setBusinessCode(this.getBusinessCode());
        coreIntegralDetailVO.setLaveNum(this.getLaveNum());
        coreIntegralDetailVO.setChangeNum(this.getChangeNum());
        coreIntegralDetailVO.setOperateType(this.getOperateType());
        coreIntegralDetailVO.setDetailedDate(this.getDetailedDate());
        coreIntegralDetailVO.setDescription(this.getDescription());


        return coreIntegralDetailVO;
    }

}