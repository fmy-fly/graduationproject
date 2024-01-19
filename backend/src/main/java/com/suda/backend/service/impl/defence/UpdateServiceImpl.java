package com.suda.backend.service.impl.defence;

import com.suda.backend.mapper.DefenceMapper;
import com.suda.backend.pojo.Defence;
import com.suda.backend.pojo.User;
import com.suda.backend.service.impl.utils.UserDetailsImpl;
import com.suda.backend.service.defence.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateServiceImpl implements UpdateService {
    @Autowired
    private DefenceMapper DefenceMapper;

    @Override
    public Map<String, String> update(Map<String, String> data) {

        System.out.println(12312312);
        int id = Integer.parseInt(data.get("id"));

        System.out.println(id);


        String title = data.get("title");
        String description = data.get("description");
        int start = Integer.valueOf(data.get("start"));

        Map<String, String> map = new HashMap<>();

        if (title == null || title.length() == 0) {
            map.put("error_message", "标题不能为空");
            return map;
        }

        if (description == null || description.length() == 0) {
            description = "这个用户很懒，什么也没留下~";
        }




        Defence defence = DefenceMapper.selectById(id);

        if (defence == null) {
            map.put("error_message", "策略不存在或已被删除");
            return map;
        }



        Defence defence1 = new Defence(
                defence.getId(),
                title,
                description,
                start

        );

        DefenceMapper.updateById(defence1);

        map.put("error_message", "success");

        return map;
    }
}
