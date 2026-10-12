package co.edu.unimagdalena.turismo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TurismoApp {
  public static void main(String[] args) {
    SpringApplication.run(TurismoApp.class, args);
  }
}
