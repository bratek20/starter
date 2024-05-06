package pl.bratek20.architecture.properties.impl;

import pl.bratek20.architecture.properties.api.Properties;
import pl.bratek20.architecture.properties.api.PropertiesTest;
import pl.bratek20.architecture.properties.api.PropertiesSource;

import java.util.List;

class PropertiesImplTest extends PropertiesTest {

    @Override
    protected Properties createInstance(List<PropertiesSource> params) {
        return new PropertiesImpl(params);
    }
}