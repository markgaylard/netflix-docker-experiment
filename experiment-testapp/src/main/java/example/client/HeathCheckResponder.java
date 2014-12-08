package example.client;

import com.netflix.appinfo.HealthCheckHandler;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.InstanceInfo.InstanceStatus;
import com.netflix.discovery.DiscoveryManager;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class HeathCheckResponder implements HealthCheckHandler, InitializingBean
{
    @Override
    public InstanceStatus getStatus(InstanceStatus currentStatus)
    {
        System.err.println("XXXXXXX");
        return InstanceInfo.InstanceStatus.UP;
    }

    @Override
    public void afterPropertiesSet() throws Exception
    {
        DiscoveryManager.getInstance().getDiscoveryClient().registerHealthCheck(this);
    }

    
}
