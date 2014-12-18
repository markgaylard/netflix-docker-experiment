package example.service1;

import java.io.IOException;

import com.netflix.config.ConfigurationManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration
public class HelloWorldApplication
{
    public static void main(String[] args)
    {
        try
        {
            System.setProperty("server.port", "8081");
            ConfigurationManager.loadCascadedPropertiesFromResources("service1");
        }
        catch (IOException e)
        {
        }
        SpringApplication.run(HelloWorldApplication.class, args);
    }
}
