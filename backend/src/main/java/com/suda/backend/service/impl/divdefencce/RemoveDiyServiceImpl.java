package com.suda.backend.service.impl.divdefencce;

import com.suda.backend.mapper.DiyDefenceMapper;
import com.suda.backend.pojo.Diydefence;
import com.suda.backend.service.divdefence.RemoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RemoveDiyServiceImpl implements RemoveService {
    @Autowired
    private DiyDefenceMapper diyDefenceMapper;

    @Override
    public Map<String, String> remove(Map<String, String> data) {
        Map<String, String> map = new HashMap<>();
        if (data.get("id") == null) {
            map.put("error_message", "策略不存在或已被删除");
            return map;
        }

        Integer id = Integer.parseInt(data.get("id"));
        Diydefence diyDefence = diyDefenceMapper.selectById(id);


        if (diyDefence == null) {
            map.put("error_message", "策略不存在或已被删除");
            return map;
        }



        diyDefenceMapper.deleteById(id);

        map.put("error_message", "success");
        return map;
    }
}
