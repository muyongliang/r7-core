package com.r7.core.integral.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.r7.core.integral.dto.CoreIntegralDTO;
import com.r7.core.integral.vo.CoreIntegralVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author wt
 * @Description 当前用户积分表
 */
@Data
@ApiModel(value = "当前用户积分实体")
@TableName(value = "core_integral")
@EqualsAndHashCode(callSuper = true)
public class CoreIntegral extends Model<CoreIntegral> {
    /**
     * id
     */
    @TableId(value = "id")
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 当前用户id
     */
    @ApiModelProperty(value = "当前用户id")
    private Long userId;

    /**
     * 当前用户的总积分
     */
    @ApiModelProperty(value = "当前用户的总积分")
    private Integer total;

    /**
     * 签名 用户ID，用户总积分，每次增减时，从数据库中获取签名进行对比，如果匹配，则进行增减操作，并重新设置对应的签名，否者返回对应错误
     */
    @ApiModelProperty(value = "签名 用户ID，用户总积分，每次增减时，从数据库中获取签名进行对比，如果匹配，则进行增减操作，并重新设置对应的签名，否者返回对应错误")
    private String sign;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private Long createdBy;

    /**
     * 创建时间
     */

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdAt;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private Long updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateAt;

    public void toCoreIntegralDTO(CoreIntegralDTO coreIntegralDTO) {
        this.setUserId(coreIntegralDTO.getUserId());
        this.setTotal(coreIntegralDTO.getTotal());
    }

    public CoreIntegralVO toCoreIntegralVO() {
        CoreIntegralVO coreIntegralVO = new CoreIntegralVO();
        coreIntegralVO.setUserId(this.getUserId());
        coreIntegralVO.setTotal(this.getTotal());
        coreIntegralVO.setSign(this.getSign());
        coreIntegralVO.setId(this.getId());
        return coreIntegralVO;
    }
}