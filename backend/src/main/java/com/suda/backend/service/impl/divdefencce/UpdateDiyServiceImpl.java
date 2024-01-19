package com.suda.backend.service.impl.divdefencce;

import com.suda.backend.mapper.DiyDefenceMapper;
import com.suda.backend.pojo.Diydefence;
import com.suda.backend.pojo.User;
import com.suda.backend.service.impl.utils.UserDetailsImpl;
import com.suda.backend.service.divdefence.UpdateDiyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateDiyServiceImpl implements UpdateDiyService {
    @Autowired
    private DiyDefenceMapper diyDefenceMapper;

    @Override
    public Map<String, String> update(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        int id = Integer.parseInt(data.get("id"));

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
            map.put("error_message", "Bot描述的长度不能大于300");
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

        Diydefence diydefence = diyDefenceMapper.selectById(id);

        if (diydefence == null) {
            map.put("error_message", "策略不存在或已被删除");
            return map;
        }



        Diydefence diydefence1 = new Diydefence(
                diydefence.getId(),
                title,
                description,
                content,
                diydefence.getCreatetime(),
                new Date()
        );

        diyDefenceMapper.updateById(diydefence1);

        map.put("error_message", "success");

        return map;
    }
}
