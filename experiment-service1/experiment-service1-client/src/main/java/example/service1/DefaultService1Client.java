package example.service1;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.inject.Named;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.appinfo.CloudInstanceConfig;
import com.netflix.config.ConfigurationManager;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryManager;
import com.netflix.hystrix.util.HystrixTimer;
import com.netflix.ribbon.ClientOptions;
import com.netflix.ribbon.Ribbon;
import com.netflix.ribbon.RibbonRequest;
import com.netflix.ribbon.http.HttpRequestTemplate;
import com.netflix.ribbon.http.HttpResourceGroup;

@Named
public class DefaultService1Client implements Service1Client
{
    private static final ObjectMapper mapper = new ObjectMapper();
    private final HttpRequestTemplate<ByteBuf> helloToTemplate;
    private final HttpRequestTemplate getObjectTemplate;

    public DefaultService1Client()
    {
        try
        {
            ConfigurationManager.loadCascadedPropertiesFromResources("helloClient");
        }
        catch (IOException e)
        {
        }


        ClientOptions clientOptions = ClientOptions.create()
            .withMaxAutoRetriesNextServer(1)
            .withDiscoveryServiceIdentifier("service1.mydomain.net");

        HttpResourceGroup httpResourceGroup = Ribbon.createHttpResourceGroup("helloClient", clientOptions);
        DiscoveryManager.getInstance().initComponent(new CloudInstanceConfig(), new DefaultEurekaClientConfig());

        helloToTemplate = httpResourceGroup.newTemplateBuilder("helloTo", ByteBuf.class)
            .withMethod("GET")
            .withUriTemplate("/hello/to/{name}")
            //            .withFallbackProvider(new RecommendationServiceFallbackHandler())
//            .withResponseValidator(new RecommendationServiceResponseValidator())
            .build();

        getObjectTemplate = httpResourceGroup.newTemplateBuilder("getObject", ByteBuf.class)
            .withMethod("GET")
            .withUriTemplate("/hello/object/{identifier}")
            //            .withFallbackProvider(new RecommendationServiceFallbackHandler())
//            .withResponseValidator(new RecommendationServiceResponseValidator())
            .build();
    }

    @Override
    public String hello(String name)
    {
        RibbonRequest<ByteBuf> ribbonRequest = helloToTemplate.requestBuilder().withRequestProperty("name", name).build();
        return ribbonRequest.execute().toString(Charset.defaultCharset());
    }

    @Override
    public TopLevelDomainObject getDomainObject(String identifier)
    {
        RibbonRequest<ByteBuf> ribbonRequest = getObjectTemplate.requestBuilder().withRequestProperty("identifier", identifier).build();
        String jsonString = ribbonRequest.execute().toString(Charset.defaultCharset());
        try
        {
            return mapper.readValue(jsonString, TopLevelDomainObject.class);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args)
    {
        try
        {
            DefaultService1Client client = new DefaultService1Client();
            System.err.println(client.hello("frog"));
            TopLevelDomainObject domainObject = client.getDomainObject("thingy");
            System.err.println(domainObject);
        }
        finally
        {
            HystrixTimer.reset();
        }
    }
}
