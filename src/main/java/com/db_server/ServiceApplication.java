package com.db_server;

import com.db_server.Script.Script;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;

@SpringBootApplication
public class ServiceApplication {




	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
		//检测登录
		new Script().inspect_login();
	}
}
