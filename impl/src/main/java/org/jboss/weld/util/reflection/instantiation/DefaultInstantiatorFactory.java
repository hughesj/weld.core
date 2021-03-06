/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.weld.util.reflection.instantiation;

import org.jboss.weld.config.ConfigurationKey;
import org.jboss.weld.config.WeldConfiguration;
import org.jboss.weld.resources.spi.ResourceLoader;

/**
 * A factory class for obtaining the first available instantiator
 *
 * @author Nicklas Karlsson
 * @author Ales Justin
 */
public class DefaultInstantiatorFactory extends AbstractInstantiatorFactory {

    private final ResourceLoader loader;

    public DefaultInstantiatorFactory(ResourceLoader resourceLoader, WeldConfiguration configuration) {
        super(configuration);
        this.loader = resourceLoader;
    }

    @edu.umd.cs.findbugs.annotations.SuppressWarnings(value = "DC_DOUBLECHECK", justification = "Field is volatile")
    public boolean useInstantiators() {
        if (enabled == null) {
            synchronized (this) {
                if (enabled == null) {
                    boolean tmp = configuration.getBooleanProperty(ConfigurationKey.PROXY_UNSAFE) || loader.getResource(MARKER) != null;

                    if (tmp) {
                        tmp = checkInstantiator();
                    }

                    enabled = tmp;
                }
            }
        }
        return enabled;
    }

    public void cleanup() {
    }
}
