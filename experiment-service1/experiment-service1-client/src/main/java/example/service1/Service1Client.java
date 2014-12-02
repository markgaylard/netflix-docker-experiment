package example.service1;

public interface Service1Client
{
    String hello(String name);
    
    TopLevelDomainObject getDomainObject(String identifier);
}