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
package io.fabric8.process.manager.commands;

import io.fabric8.api.scr.ValidatingReference;
import io.fabric8.boot.commands.support.AbstractCommandComponent;
import io.fabric8.process.manager.ProcessManager;
import io.fabric8.process.manager.commands.support.ProcessNumberCompleter;
import org.apache.felix.gogo.commands.Action;
import org.apache.felix.gogo.commands.basic.AbstractCommand;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.felix.service.command.Function;
import org.osgi.framework.BundleContext;

@Component(immediate = true)
@Service({ Function.class, AbstractCommand.class })
@org.apache.felix.scr.annotations.Properties({
        @Property(name = "osgi.command.scope", value = Environment.SCOPE_VALUE),
        @Property(name = "osgi.command.function", value = Environment.FUNCTION_VALUE)
})
public class Environment extends AbstractCommandComponent {

    public static final String SCOPE_VALUE = "process";
    public static final String FUNCTION_VALUE = "environment";
    public static final String DESCRIPTION = "Environment variables of a managed process";

    @Reference(referenceInterface = ProcessManager.class)
    private final ValidatingReference<ProcessManager> processManager = new ValidatingReference<ProcessManager>();

    @Reference(referenceInterface = ProcessNumberCompleter.class, bind = "bindProcessNumberCompleter", unbind = "unbindProcessNumberCompleter")
    private ProcessNumberCompleter processNumberCompleter; // dummy field

    private BundleContext bundleContext;

    @Activate
    void activate(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
        activateComponent();
    }

    @Deactivate
    void deactivate() {
        deactivateComponent();
    }

    @Override
    public Action createNewAction() {
        assertValid();
        return new EnvironmentAction(processManager.get());
    }

    void bindProcessManager(ProcessManager processManager) {
        this.processManager.bind(processManager);
    }

    void unbindProcessManager(ProcessManager processManager) {
        this.processManager.unbind(processManager);
    }

    void bindProcessNumberCompleter(ProcessNumberCompleter completer) {
        bindCompleter(completer);
    }

    void unbindProcessNumberCompleter(ProcessNumberCompleter completer) {
        unbindCompleter(completer);
    }

}
