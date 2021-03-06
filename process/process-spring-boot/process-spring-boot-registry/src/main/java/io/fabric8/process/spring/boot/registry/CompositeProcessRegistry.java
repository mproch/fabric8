/**
 *  Copyright 2005-2014 Red Hat, Inc.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package io.fabric8.process.spring.boot.registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * The default type of the registry used by the Spring Boot container is the {@link CompositeProcessRegistry}. This is
 * kind of the proxy implementation of the registry delegating properties resolution to the list of the <i>real</i>
 * registries.
 * <br><br>
 * Composite registry will try to resolve given property using the first aggregated registry. If it won't find the
 * property there, it will fallback to the next registry in the list. If none of the registry in the aggregated list
 * contains the desired property, the composite resolver returns {@code null}.
 */
public class CompositeProcessRegistry implements ProcessRegistry {

    private final static Logger LOG = LoggerFactory.getLogger(CompositeProcessRegistry.class);

    private final List<ProcessRegistry> registries;

    public CompositeProcessRegistry(ProcessRegistry... registries) {
        this(asList(registries));
    }

    public CompositeProcessRegistry(List<ProcessRegistry> registries) {
        this.registries = registries;
        LOG.debug("Created composite registry delegating to the following registries: {}", registries);
    }

    @Override
    public String readProperty(String key) {
        LOG.debug("Resolving property: {}", key);
        for(ProcessRegistry processRegistry : registries) {
            String propertyValue = processRegistry.readProperty(key);
            if(propertyValue != null) {
                LOG.debug("Found property {} with value {} in registry {}.", key, propertyValue, processRegistry);
                return propertyValue;
            }
        }
        LOG.debug("Property {} not found in any of the registries.");
        return null;
    }

}
