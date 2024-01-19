package com.suda.backend.service.impl.divdefencce;

import com.suda.backend.mapper.DiyDefenceMapper;
import com.suda.backend.pojo.Diydefence;
import com.suda.backend.service.divdefence.AddDiyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AddDiyServiceImpl implements AddDiyService {

    @Autowired
    private DiyDefenceMapper diyDefenceMapper;

    @Override
    public Map<String, String> add(Map<String, String> data) {
        String title = data.get("title");
        String description = data.get("description");
        String content = data.get("content");
        Map<String, String> map = new HashMap<>();

        if (title == null || title.length() == 0) {
            map.put("error_message", "标题不能为空");
            return map;
        }

        if (title.length() > 100) {
            map.put("error_message", "标题长度不能大于100");
            return map;
        }

        if (description == null || description.length() == 0) {
            description = "这个用户很懒，什么也没留下~";
        }

        if (description.length() > 300) {
            map.put("error_message", "策略描述的长度不能大于300");
            return map;
        }

        if (content == null || content.length() == 0) {
            map.put("error_message", "代码不能为空");
            return map;
        }

        if (content.length() > 10000) {
            map.put("error_message", "代码长度不能超过10000");
            return map;
        }

        Date now = new Date();
        Diydefence diyDefence = new Diydefence(null,title,description,content,now,now);
        diyDefenceMapper.insert(diyDefence);
        map.put("error_message", "success");

        return map;
    }
}
