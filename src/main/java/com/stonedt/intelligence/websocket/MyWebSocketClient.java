package com.stonedt.intelligence.websocket;

import java.net.URI;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 *  websocket客户端监听类
 * @author 。
 */
public class MyWebSocketClient extends WebSocketClient {

    private static Logger logger = LoggerFactory.getLogger(MyWebSocketClient.class);
    
  //用来接收数据
    private String excptMessage;
    
    
    

    public MyWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        logger.info(">>>>>>>>>>>websocket open");
    }

    @Override
    public void onMessage(String s) {
    	
    	this.excptMessage = s;
    	JSONObject parseObject = JSONObject.parseObject(s);
    	if(parseObject.getString("eventType").equals("output")) {
    		WebSocketUtils.array.add(parseObject);
    	}
    	
    	
    	
    	
    	
    	
    	
    	System.out.println("s:"+s);
       
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        logger.info(">>>>>>>>>>>websocket close");
    }

    @Override
    public void onError(Exception e) {
        logger.error(">>>>>>>>>websocket error {}",e);
    }

    
  //获取接收到的信息
    public String getExcptMessage() {
        if(excptMessage != null){
            String message = new String(excptMessage);
            System.out.println("message:"+message);
            excptMessage = null;
            return message;
        }
        return null;
    }

}