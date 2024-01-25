package com.suda.backend.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Node {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer type;
    private Integer size;
    private String label;
    private String shape;
    private String image;
    private Integer state;

}
