package com.stockholdergame.server.services.messaging;

import com.stockholdergame.server.model.game.EventType;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MessageBuffer {

    public static final int CAPACITY = 100;

    private Map<Long, LinkedList<BufferItem<?>>> bufferMap = new HashMap<>();

    public void addMessage(Long gamerId, Object payload) {
        LinkedList<BufferItem<?>> gamerQueue = bufferMap.getOrDefault(gamerId, new LinkedList<>());
        bufferMap.putIfAbsent(gamerId, gamerQueue);
        if (gamerQueue.size() == CAPACITY) {
            gamerQueue.removeFirst();
        }
        gamerQueue.addLast(BufferItem.of(payload));
    }

    public List<BufferItem<?>> readMessages(Long gamerId, int numberOfItems) {
        List<BufferItem<?>> items = new ArrayList<>();
        if (bufferMap.containsKey(gamerId) && numberOfItems > 0) {
            LinkedList<BufferItem<?>> gamerQueue = bufferMap.get(gamerId);
            int count = Math.min(numberOfItems, gamerQueue.size());
            for (int i = 0; i < count; i++) {
                items.add(gamerQueue.removeFirst());
            }
        }
        return items;
    }

    public static class BufferItem<T> {
        private Long createdTime;

        private T payload;

        public static <T> BufferItem<T> of(T payload) {
            BufferItem<T> bufferItem = new BufferItem<>();
            bufferItem.createdTime = System.currentTimeMillis();
            bufferItem.payload = payload;
            return bufferItem;
        }

        public Long getCreatedTime() {
            return createdTime;
        }

        public T getPayload() {
            return payload;
        }
    }
}
