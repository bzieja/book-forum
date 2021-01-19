package pl.bzpb.bookforum;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "BookForum API", version = "1.0", description = "An API that allows users to rank books and discuss them"))
public class BookForumApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookForumApplication.class, args);
    }

}
