package com.suda.backend.service.impl.button;





import com.suda.backend.mapper.ButtonMapper;
import com.suda.backend.pojo.Button;
import com.suda.backend.pojo.User;
import com.suda.backend.service.impl.utils.UserDetailsImpl;

import com.suda.backend.service.button.UpdateButtonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateButtonServiceImpl implements UpdateButtonService {
    @Autowired
    private ButtonMapper buttonMapper;

    @Override
    public Map<String, String> update(Map<String, String> data) {

        HashMap<String,String> map = new HashMap<>();
        int id = Integer.parseInt(data.get("id"));


        int state = Integer.parseInt(data.get("state"));





        Button button= buttonMapper.selectById(id);

        if (button== null) {
            map.put("error_message", "策略不存在或已被删除");
            return map;
        }



        Button button1 = new Button(
                button.getId(),

                state
        );

        buttonMapper.updateById(button1);

        map.put("error_message", "success");

        return map;
    }
}
