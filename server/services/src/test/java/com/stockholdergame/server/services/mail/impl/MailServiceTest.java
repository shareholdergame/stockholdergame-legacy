package com.stockholdergame.server.services.mail.impl;

import com.stockholdergame.server.dao.MessageDao;

import static org.easymock.EasyMock.createMock;

/**
 * @author Alexander Savin
 */
//@Test
public class MailServiceTest {

    private MailServiceImpl mailService;
    private MessageDao messageDao;

    public void setUp() {
        mailService = new MailServiceImpl();
        messageDao = createMock(MessageDao.class);
        //mailService.setMessageDao(messageDao);
    }
}
