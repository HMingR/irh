package top.imuster.message.provider.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import top.imuster.common.base.config.GlobalConstant;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * @ClassName: ChatController
 * @Description: 私聊控制器
 * @author: hmr
 * @date: 2020/7/15 9:28
 */
@ServerEndpoint("/chat/{uId}")
@Component
public class ChatWebSocket {

    @Autowired
    RedisTemplate redisTemplate;


    @OnOpen
    public void onOpen(@PathParam("userId") String userId, Session session){
        redisTemplate.opsForHash().put(GlobalConstant.IRH_CHAT_CLIENTS_MAP, userId, session);
    }

    @OnMessage
    public void onMessage(String msgDto, Session session){

    }



}
