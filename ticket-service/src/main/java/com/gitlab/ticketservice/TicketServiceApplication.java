package com.gitlab.ticketservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class TicketServiceApplication implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(TicketServiceApplication.class, args);
  }

  @Override
  public void run(String... args) {
    log.info("http://localhost:2222/swagger-ui.html");
  }
}
