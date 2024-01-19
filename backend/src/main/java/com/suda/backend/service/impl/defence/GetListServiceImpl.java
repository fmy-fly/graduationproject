package com.suda.backend.service.impl.defence;



import com.suda.backend.mapper.DefenceMapper;
import com.suda.backend.pojo.Defence;
import com.suda.backend.service.impl.utils.UserDetailsImpl;
import com.suda.backend.service.defence.GetListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetListServiceImpl implements GetListService {
    @Autowired
    private DefenceMapper defenceMapper;

    @Override
    public List<Defence> getList() {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();

        return defenceMapper.selectList(null);
    }
}
