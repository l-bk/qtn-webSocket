package com.qtn.conf;
import com.qtn.modules.utils.MyHandler;
import com.qtn.modules.utils.WebSocketInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


/**
 * 实现接口来配置Websocket请求的路径和拦截器
 * @author Administrator
 *
 */
@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

		//handler是webSocket的核心，配置入口
		registry.addHandler(new MyHandler(), "/webSocket/qtn/connect/{organizerId}").setAllowedOrigins("*").addInterceptors(new WebSocketInterceptor());

	}


}
