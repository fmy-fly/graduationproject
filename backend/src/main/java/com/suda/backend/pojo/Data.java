package com.suda.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;
@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class Data {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date predicttime;
    private Integer packagenum;
    private String description;
    private String mainattack;
    private String alerttype;

}
