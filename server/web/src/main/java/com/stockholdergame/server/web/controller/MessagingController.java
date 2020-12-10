package com.stockholdergame.server.web.controller;

import com.stockholdergame.server.services.messaging.MessageBuffer;
import com.stockholdergame.server.session.UserSessionUtil;
import com.stockholdergame.server.web.dto.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/queue")
public class MessagingController {

    public static final int NUMBER_OF_ITEMS = 10;

    @Autowired
    private MessageBuffer messageBuffer;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseWrapper<List<?>> getMessages() {
        Long gamerId = UserSessionUtil.getCurrentUser().getId();
        List<MessageBuffer.BufferItem<?>> items = messageBuffer.readMessages(gamerId, NUMBER_OF_ITEMS);
        return ResponseWrapper.ok(items.stream().map(MessageBuffer.BufferItem::getPayload).collect(Collectors.toList()));
    }
}
