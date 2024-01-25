package com.suda.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Setting {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer ml;
    private Integer node;
    private Integer threshold;
    private Integer notice;
}
