package com.souza.charles.BookstoreAPI;

import com.souza.charles.BookstoreAPI.environments.LoadEnvironment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookstoreApiApplication {

	public static void main(String[] args) {
		LoadEnvironment.loadEnv();
		SpringApplication.run(BookstoreApiApplication.class, args);
	}

}
