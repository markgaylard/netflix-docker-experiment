package example.service1;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.nio.charset.Charset;

import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.config.ConfigurationManager;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryManager;
import com.netflix.hystrix.util.HystrixTimer;
import com.netflix.ribbon.ClientOptions;
import com.netflix.ribbon.Ribbon;
import com.netflix.ribbon.RibbonRequest;
import com.netflix.ribbon.http.HttpRequestTemplate;
import com.netflix.ribbon.http.HttpResourceGroup;

public class DefaultService1Client implements Service1Client
{
    private final HttpRequestTemplate<ByteBuf> helloToTemplate;

    public DefaultService1Client()
    {
        try
        {
            ConfigurationManager.loadCascadedPropertiesFromResources("helloClient");
        }
        catch (IOException e)
        {
        }
        
        DiscoveryManager.getInstance().initComponent(new MyDataCenterInstanceConfig(), new DefaultEurekaClientConfig());

        ClientOptions clientOptions = ClientOptions.create()
            .withMaxAutoRetriesNextServer(1)
            .withDiscoveryServiceIdentifier("service1.mydomain.net");
        
        HttpResourceGroup httpResourceGroup = Ribbon.createHttpResourceGroup("helloClient", clientOptions);
        
        helloToTemplate = httpResourceGroup.newTemplateBuilder("helloTo", ByteBuf.class)
            .withMethod("GET")
            .withUriTemplate("/hello/to/{name}")
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
    
    public static void main(String[] args)
    {
        String hello = new DefaultService1Client().hello("frog");
        System.err.println(hello);
        HystrixTimer.reset();
    }
}
