package example.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import example.service1.Service1Client;

@Controller
public class TestController
{
    private final Service1Client client;

    @Autowired
    public TestController(Service1Client client)
    {
        this.client = client;
    }
    
    @RequestMapping(value = "/hello/{name}", method = RequestMethod.GET)
    public ModelAndView hello(@PathVariable("name") String name)
    {
        ModelAndView model = new ModelAndView();
        model.setViewName("hello");
        model.addObject("message", client.hello(name));

        return model;
    }
    
    @RequestMapping(value = "/domain/{id}", method = RequestMethod.GET)
    public ModelAndView domain(@PathVariable("id") String id)
    {
        ModelAndView model = new ModelAndView();
        model.setViewName("domain");
        model.addObject("domainObject", client.getDomainObject(id));
        
        return model;
    }
    
    @RequestMapping(value = "/env", method = RequestMethod.GET)
    public ModelAndView env()
    {
        ModelAndView model = new ModelAndView();
        model.setViewName("env");
        model.addObject("env", System.getenv());
        model.addObject("prop", System.getProperties());
        
        return model;
    }
}
