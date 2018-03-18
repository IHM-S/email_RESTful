package APIController;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//Please upload your solution on GitHub and include a README.md file with info on how to build and deploy the backend
//        Please deply your solution somewhere(URL) for us to play with it
@SpringBootApplication(scanBasePackages = {"APIController", "APIModel", "Service"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
