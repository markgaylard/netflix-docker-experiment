package example.service1;

import com.netflix.adminresources.resources.KaryonWebAdminModule;
import com.netflix.governator.annotations.Modules;
import com.netflix.governator.guice.LifecycleInjectorBuilderSuite;
import com.netflix.karyon.Karyon;
import com.netflix.karyon.KaryonBootstrap;
import com.netflix.karyon.ShutdownModule;
import com.netflix.karyon.archaius.ArchaiusBootstrap;
import com.netflix.karyon.eureka.KaryonEurekaModule;
import com.netflix.karyon.jersey.blocking.KaryonJerseyModule;
import com.netflix.karyon.servo.KaryonServoModule;

import example.service1.Service1App.KaryonJerseyModuleImpl;
import example.shared.auth.AuthenticationService;
import example.shared.auth.DefaultAuthenticationService;
import example.shared.logging.LoggingInterceptor;

@ArchaiusBootstrap
@KaryonBootstrap(name = "service1", healthcheck = HealthCheck.class)
@Modules(include = {
        ShutdownModule.class,
        KaryonWebAdminModule.class,
        KaryonEurekaModule.class,
        KaryonJerseyModuleImpl.class,
        KaryonServoModule.class
})
public interface Service1App
{
    class KaryonJerseyModuleImpl extends KaryonJerseyModule
    {
        @Override
        protected void configureServer()
        {
            bind(AuthenticationService.class).to(DefaultAuthenticationService.class);
            interceptorSupport().forUri("/*").intercept(LoggingInterceptor.class);
//            interceptorSupport().forUri("/*").interceptIn(AuthInterceptor.class);
            server().port(8081).threadPoolSize(100);
        }
    }

    public static void main(String[] args)
    {
        try
        {
            System.setProperty("archaius.deployment.environment", "dev");
            Karyon.forApplication(Service1App.class, (LifecycleInjectorBuilderSuite[]) null).startAndWaitTillShutdown();
        }
        catch (Exception e)
        {
            System.err.println(e);
            System.exit(-1);
        }

        // In case we have non-daemon threads running
        System.exit(0);
    }
}