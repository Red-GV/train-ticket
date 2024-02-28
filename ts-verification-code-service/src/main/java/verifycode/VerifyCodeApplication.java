package verifycode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author fdse
 */
@SpringBootApplication
@EnableCaching
public class VerifyCodeApplication {
    public static void main(String[] args) {
        SpringApplication.run(VerifyCodeApplication.class, args);
    }
}
