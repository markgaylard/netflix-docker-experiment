package example.service1;

import java.util.Collections;
import java.util.Map;
import java.util.Random;

import com.netflix.config.DynamicFloatProperty;
import com.netflix.config.DynamicPropertyFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloWorldController
{
    private static final Logger logger = LoggerFactory.getLogger(HelloWorldController.class);
    private final Random random = new Random();
    private final DynamicFloatProperty failPercentProperty;

    public HelloWorldController()
    {
        failPercentProperty = DynamicPropertyFactory.getInstance().getFloatProperty("service1.failPercent", 0.001f);
    }

    @RequestMapping(value="to/{name}", method=RequestMethod.GET)
    public ResponseEntity<Map<String, String>> helloTo(@PathVariable("name") String name)
    {
        try
        {
            if (shouldFail())
            {
                return new ResponseEntity<Map<String, String>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            Map<String, String> response = Collections.singletonMap("Message", "Hello " + name + " from service 1");
            return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
        }
        catch (Exception e)
        {
            logger.error("Error creating json response.", e);
            return new ResponseEntity<Map<String, String>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value="to/person", method=RequestMethod.POST)
    public ResponseEntity<Map<String, String>> helloToPerson(String name)
    {
        try
        {
            if (shouldFail())
            {
                return new ResponseEntity<Map<String, String>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            Map<String, String> response = Collections.singletonMap("Message", "Hello " + name + " from service 1");
            return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
        }
        catch (Exception e)
        {
            logger.error("Error creating json response.", e);
            return new ResponseEntity<Map<String, String>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<Map<String, String>> hello()
    {
        try
        {
            if (shouldFail())
            {
                return new ResponseEntity<Map<String, String>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            Map<String, String> response = Collections.singletonMap("Message", "Hello from service 1");
            return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
        }
        catch (Exception e)
        {
            logger.error("Error creating json response.", e);
            return new ResponseEntity<Map<String, String>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value="object/{id}", method=RequestMethod.GET)
    public ResponseEntity<TopLevelDomainObject> topLevelObject(@PathVariable("id") String id)
    {
        try
        {
            if (shouldFail())
            {
                return new ResponseEntity<TopLevelDomainObject>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<TopLevelDomainObject>(new TopLevelDomainObject(id, 123), HttpStatus.OK);
        }
        catch (Exception e)
        {
            logger.error("Error creating json response.", e);
            return new ResponseEntity<TopLevelDomainObject>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean shouldFail()
    {
        float f = failPercentProperty.get();
        boolean shouldFail = random.nextFloat() <= f;
//        System.err.println(String.format("%s %% %s", f, shouldFail));
        return shouldFail;
    }

}