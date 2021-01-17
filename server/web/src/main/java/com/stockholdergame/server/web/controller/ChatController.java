package com.stockholdergame.server.web.controller;

import com.google.common.collect.Lists;
import com.stockholdergame.server.dto.account.SendMessageDto;
import com.stockholdergame.server.facade.SocialFacade;
import com.stockholdergame.server.web.dto.Chat;
import com.stockholdergame.server.web.dto.Duration;
import com.stockholdergame.server.web.dto.NewChatMessage;
import com.stockholdergame.server.web.dto.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private SocialFacade socialFacade;

    @RequestMapping(value = "/send", method = RequestMethod.PUT)
    public ResponseWrapper<?> sendChatMessage(@RequestBody NewChatMessage newChatMessage) {
        SendMessageDto sendMessageDto = new SendMessageDto();
        sendMessageDto.setRecipientNames(newChatMessage.recipients);
        sendMessageDto.setMessage(newChatMessage.text);
        socialFacade.sendMessage(sendMessageDto);
        return ResponseWrapper.ok();
    }

    @RequestMapping(value = "/markasread", method = RequestMethod.POST)
    public ResponseWrapper<?> markAsRead(@RequestParam("messageIds") String[] messageIds) {
        return ResponseWrapper.ok();
    }

    @RequestMapping(value = "/history/{duration}", method = RequestMethod.GET)
    public ResponseWrapper<?> chatHistory(@PathVariable Duration duration) {
        return ResponseWrapper.ok();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseWrapper<Collection<Chat>> getChats(@RequestParam(value = "unreadonly", defaultValue = "false") boolean unreadonly,
                                                      @RequestParam(value = "offset", defaultValue = "0") int offset,
                                                      @RequestParam(value = "ipp", defaultValue = "10") int itemsPerPage) {
        return ResponseWrapper.ok(Lists.newArrayList());
    }
}
