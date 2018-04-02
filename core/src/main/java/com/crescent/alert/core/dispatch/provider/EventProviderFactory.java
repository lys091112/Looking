package com.crescent.alert.core.dispatch.provider;

import com.crescent.alert.common.exception.InitializationException;
import com.crescent.alert.core.AlertProvider;
import com.google.common.base.Preconditions;
import java.util.Iterator;
import java.util.ServiceLoader;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class EventProviderFactory {

    private final static Logger LOGGER = LoggerFactory.getLogger(EventProviderFactory.class);

    public abstract BaseEventsProvider createEventProvider();

    protected BaseEventsProvider createEventProvider(AlertProvider alertProvider, String eventProviderType) {
        Preconditions.checkArgument(StringUtils.isNoneBlank(eventProviderType), "Event window type is blank!");

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Load event provider! event provider type is {}", eventProviderType);
        }

        BaseEventsProvider provider = null;
        ServiceLoader<BaseEventsProvider> loadProducers = ServiceLoader.load(BaseEventsProvider.class);
        Iterator<BaseEventsProvider> iterator = loadProducers.iterator();
        while (iterator.hasNext()) {
            BaseEventsProvider t = iterator.next();
            if (eventProviderType.equalsIgnoreCase(t.eventWindowType())) {
                provider = t;
                break;
            }
        }
        if (provider == null) {
            throw new InitializationException("Classes that have not been found to implement the BaseEventsProvider interface!"
                + "event provider type is " + eventProviderType);
        }

        provider.setAlertProvider(alertProvider);
        return provider;
    }
}
