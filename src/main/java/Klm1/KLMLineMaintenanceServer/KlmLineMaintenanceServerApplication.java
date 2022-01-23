package Klm1.KLMLineMaintenanceServer;

import Klm1.KLMLineMaintenanceServer.models.User;
import Klm1.KLMLineMaintenanceServer.repositories.UserRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.transaction.Transactional;

@SpringBootApplication
public class KlmLineMaintenanceServerApplication implements CommandLineRunner {
	@Autowired
	UserRepositoryJpa userRepositoryJpa;

	public static void main(String[] args) {
		SpringApplication.run(KlmLineMaintenanceServerApplication.class, args);
		System.out.println("-------------------------Finish---------------------------");
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {

	}
}
