package com.java.nie.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * yarn任务日志
 * </p>
 *
 * @author wyf
 * @since 2022-06-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("dp_yarntask_log")
public class YarntaskLog extends Model<YarntaskLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long taskId;

    private Integer taskType;

    @TableField(exist = false)
    private Integer taskStates;

    @TableField(exist = false)
    private LocalDateTime timestamp;

    private LocalDateTime createTime;

    /**
     * 任务状态（-1：开始；0：执行中；1：执行结束-成功；2：执行结束-失败）
     */
    private Integer states;

    private LocalDateTime logTime;

    private String logger;

    private String method;

    private String level;

    private String message;

    private String exception;

    private String nodeip;

    /**
     * 数量
     */
    private int counting;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
