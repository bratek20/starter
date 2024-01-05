package pl.bratek20.commons.modules.impl;

import pl.bratek20.commons.modules.api.Startable;

import java.util.Collection;

public class StartCaller {
    public StartCaller(Collection<Startable> startables) {
        startables.forEach(Startable::start);
    }
}
