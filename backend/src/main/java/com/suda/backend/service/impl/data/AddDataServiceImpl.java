package com.suda.backend.service.impl.data;

import com.suda.backend.service.data.AddDataService;
import org.springframework.stereotype.Service;



import com.suda.backend.mapper.DataMapper;
import com.suda.backend.pojo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AddDataServiceImpl implements AddDataService {

    @Autowired
    private DataMapper dataMapper;

    @Override
    public Map<String, String> add(int [] data) {

//normal\dos\probing\R2l\U2R
        Date predictime = new Date();
        int packagenum = data[5];
        String description = "正常数据包:" +data[0]+",    Dos:" + data[1] + ",    Probing:" +data[2] +
                ",    R2l:" + data[3] + ",    U2R:" + data[4];
        String mainattack;
        int temp = 0;
        for (int i = 1; i <= 4;i ++){
            temp = Math.max(temp,data[i]);
        }
        int j = 0 ;
        for (int i = 1;i  <= 4; i++){
            if (temp == data[i]){
                j = i;
                break;
            }
        }
        if (j == 1){
            mainattack = "Dos";
        }
        else if (j == 2){
            mainattack = "probing";
        } else if (j == 3) {

            mainattack = "R2l";
        }
        else {
            mainattack = "U2R";
        }
        String alerttype;
        Map<String, String> map = new HashMap<>();
        double percent = (double)data[0]/data[5] ;
        if (percent < 0.2){
            alerttype = "严重";
        }
        else if (percent < 0.5){
            alerttype = "普通";
        }
        else if (percent < 0.75){
            alerttype = "轻微";
        }
        else alerttype = "正常";


        Date now = new Date();
        Data a = new Data(null,predictime,packagenum,description,mainattack,alerttype);
        dataMapper.insert(a);
        map.put("error_message", "success");

        return map;
    }
}
