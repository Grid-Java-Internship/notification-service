package com.internship.notification_service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.internship.notification_service.rabbitmq.communication.Message;
import com.internship.notification_service.service.SseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class SseServiceImpl implements SseService {

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final Map<String, ScheduledFuture<?>> heartbeatTasks = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper;
    private final ExecutorService sseExecutor = Executors.newCachedThreadPool();
    private final ScheduledExecutorService heartbeatExecutor = Executors.newSingleThreadScheduledExecutor();

    @Override
    public SseEmitter subscribe(String userId) {
        SseEmitter emitter = new SseEmitter(600_00L);
        emitters.put(userId, emitter);

        Runnable cleanupOnCompletion=
        () -> {
            log.info("Cleaning up data for user {} : onCompletionMethod", userId);

            emitters.remove(userId);
            ScheduledFuture<?> task = heartbeatTasks.remove(userId);
            if (task != null) {
                task.cancel(false);
            }
            if(emitters.isEmpty()){
                log.info("EMITTERS ARE EMPTY!!");
            }
        };

        Runnable cleanupOnTimeout=
                () -> log.info("Cleaning up data for user {} : onTimeoutMethod", userId);

        emitter.onCompletion(cleanupOnCompletion);
        emitter.onTimeout(cleanupOnTimeout);
        emitter.onError(e -> log.info("Cleaning up data for user {} : onErrorMethod, {}", userId,e.getMessage()));

        sseExecutor.execute(() -> {
            try {
                emitter.send(SseEmitter.event()
                        .id(UUID.randomUUID().toString())
                        .name("connection-established")
                        .data("SSE connected for user " + userId));
                startHeartbeat(userId, emitter);
            } catch (IOException e) {
                log.info("ERROR IN EXECUTE METHOD!!!");
            }
        });
        for (Map.Entry<String, SseEmitter> entry : emitters.entrySet()) {
            String id = entry.getKey();
            SseEmitter em = entry.getValue();
            log.info("User ID: {}, Emitter: {}", id, em);
        }
        return emitter;
    }



    private void startHeartbeat(String userId, SseEmitter emitter) {
        ScheduledFuture<?> task = heartbeatExecutor.scheduleAtFixedRate(() -> {
            try {
                emitter.send(SseEmitter.event().comment("keep-alive"));
                log.info("Sending ping to client {}!", userId);
            } catch (IOException e) {
                log.info("Client {} disconnected", userId);
            } catch (Exception ex) {
                log.info("Error in startHeartbeat method.", ex);
            }
        }, 10, 10, TimeUnit.SECONDS);

        heartbeatTasks.putIfAbsent(userId, task);
    }

    @Override
    public void sendNotificationToUser(String userId, Message message) {
        SseEmitter emitter = emitters.get(userId);
        log.info("Push notification is being processed!!");
        if (emitter == null) {
            log.info("EMITTER IS NULL!!!");
            return;
        }

        sseExecutor.execute(() -> {
            try {
                emitter.send(SseEmitter.event()
                        .id(UUID.randomUUID().toString())
                        .name("reservation-accepted-event")
                        .data(objectMapper.writeValueAsString(message)));
                log.info("Push notification was sent");
            } catch (Exception e) {
                log.error("Error sending message to userId:{} ", userId, e);
            }
        });
    }


}