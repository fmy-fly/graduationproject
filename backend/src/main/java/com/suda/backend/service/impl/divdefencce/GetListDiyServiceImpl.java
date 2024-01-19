package com.suda.backend.service.impl.divdefencce;

import com.suda.backend.mapper.DiyDefenceMapper;
import com.suda.backend.pojo.Diydefence;
import com.suda.backend.service.divdefence.GetListDiyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetListDiyServiceImpl implements GetListDiyService {
    @Autowired
    private DiyDefenceMapper diyDefenceMapper;

    @Override
    public List<Diydefence> getList() {



        return diyDefenceMapper.selectList(null);
    }
}
