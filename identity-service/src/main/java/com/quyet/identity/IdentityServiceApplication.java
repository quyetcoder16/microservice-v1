package com.quyet.identity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.net.InetAddress;

@SpringBootApplication
public class IdentityServiceApplication {

  @Value("${server.port:8888}")
  private int serverPort;

  @Value("${server.servlet.context-path}")
  private String contextPath;

  public static void main(String[] args) {
    SpringApplication.run(IdentityServiceApplication.class, args);
  }

  @EventListener(ApplicationReadyEvent.class)
  public void onApplicationReady() {
    try {
      String ip = InetAddress.getLocalHost().getHostAddress();
      System.out.println("\n================================================");
      System.out.println(" 🚀 Identity Service is running!");
      System.out.println(" 🌐 Local:    http://localhost:" + serverPort + contextPath);
      System.out.println(" 🌍 External: http://" + ip + ":" + serverPort + contextPath);
      System.out.println("================================================\n");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
