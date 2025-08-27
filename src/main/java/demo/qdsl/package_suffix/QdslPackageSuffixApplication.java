package demo.qdsl.package_suffix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
public class QdslPackageSuffixApplication {

	public static void main(String[] args) {
		SpringApplication.run(QdslPackageSuffixApplication.class, args);
	}

}
