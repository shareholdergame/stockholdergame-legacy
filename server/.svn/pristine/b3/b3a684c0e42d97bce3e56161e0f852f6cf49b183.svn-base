package com.stockholdergame.server.services.event.listener;

import com.stockholdergame.server.model.event.BusinessEvent;
import com.stockholdergame.server.model.event.BusinessEventType;
import com.stockholdergame.server.services.event.EventHandler;
import org.springframework.context.ApplicationListener;

import java.util.Collections;
import java.util.Map;
import javax.annotation.Resource;

/**
 * @author Aliaksandr Savin
 */
public class BusinessEventDispatcher implements ApplicationListener<BusinessEvent> {

    @Resource
    private Map<BusinessEventType, EventHandler> handlerMap;

    public Map<BusinessEventType, EventHandler> getHandlerMap() {
        return Collections.unmodifiableMap(handlerMap);
    }

    public void setHandlerMap(Map<BusinessEventType, EventHandler> handlerMap) {
        this.handlerMap = handlerMap;
    }

    @Override
    public void onApplicationEvent(BusinessEvent event) {
        dispatchEvent(event);
    }

    @SuppressWarnings("unchecked")
    private void dispatchEvent(BusinessEvent event) {
        handlerMap.get(event.getType()).handle(event.getEventInitiatorUserId(), event.getPayload());
    }
}
