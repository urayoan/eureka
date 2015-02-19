package netflix.adminresources.resources;

import com.google.inject.ImplementedBy;
import com.netflix.eureka2.client.EurekaClient;

@ImplementedBy(EurekaClientProviderImpl.class)
public interface EurekaClientProvider {
    EurekaClient get();
}