package com.suda.backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Edge {

    private Integer id;
    private Integer fromn;
    private Integer ton;
    private String label;
    private String color;
    private String highlight;
    private String hover;
    private Integer length;
}
