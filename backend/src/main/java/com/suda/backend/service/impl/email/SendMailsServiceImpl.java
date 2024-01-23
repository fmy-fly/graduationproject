package com.suda.backend.service.impl.email;

import com.suda.backend.pojo.Recipient;
import com.suda.backend.service.email.SendMailService;
import com.suda.backend.service.email.SendMailsService;
import com.suda.backend.service.recipient.GetListRecipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SendMailsServiceImpl implements SendMailsService {
    @Autowired
    private SendMailService sendMailService;
    @Autowired
    private GetListRecipientService getListRecipientService;

    public void sendMails() {
       List<Recipient> address = getListRecipientService.getList();
       for (Recipient recipient:address){
           System.out.println(recipient.getEmail());
           sendMailService.sendMail(recipient.getEmail());
       }

    }

}
