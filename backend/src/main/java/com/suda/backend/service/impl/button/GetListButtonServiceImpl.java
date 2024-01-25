package com.suda.backend.service.impl.button;



import com.suda.backend.mapper.ButtonMapper;
import com.suda.backend.mapper.DefenceMapper;
import com.suda.backend.pojo.Button;
import com.suda.backend.pojo.Defence;
import com.suda.backend.service.button.GetListButtonService;
import com.suda.backend.service.impl.utils.UserDetailsImpl;
import com.suda.backend.service.defence.GetListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetListButtonServiceImpl implements GetListButtonService {
    @Autowired
    private ButtonMapper buttonMapper;

    @Override
    public List<Button> getList() {

        return buttonMapper.selectList(null);
    }
}
