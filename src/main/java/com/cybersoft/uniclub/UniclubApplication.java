package com.cybersoft.uniclub;

import com.cybersoft.uniclub.service.FilesStorageService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UniclubApplication implements CommandLineRunner {
	@Autowired
	FilesStorageService storageService;

	public static void main(String[] args) {
		SpringApplication.run(UniclubApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		storageService.init();
	}
}
