/*
 * Copyright 2014 Grow Bit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.turbogwt.mvp.databind;

import com.google.web.bindery.event.shared.HandlerRegistration;

import org.turbogwt.core.util.shared.Registration;

/**
 * A default Registration to be used by {@link Binder}s.
 *
 * @author Danilo Reinert
 */
public class BinderRegistration implements Registration {

    private final Binder binder;
    private final HandlerRegistration[] handlerRegistrations;
    private final String id;

    private BinderRegistration(Binder binder, String id,
                               HandlerRegistration[] handlerRegistrations) {
        this.binder = binder;
        this.id = id;
        this.handlerRegistrations = handlerRegistrations;
    }

    /**
     * Creates a {@link BinderRegistration} bound with other HandlerRegistrations. By the time of unbinding, all
     * handlers will be removed.
     *
     * @param binder               container
     * @param id                   identification of the binding
     * @param handlerRegistrations handlers to be chained removed
     *
     * @return the composite handler created
     */
    public static Registration of(Binder binder, String id,
                                  HandlerRegistration... handlerRegistrations) {
        return new BinderRegistration(binder, id, handlerRegistrations);
    }

    /**
     * Creates a {@link BinderRegistration} associated with given id.
     *
     * @param binder container
     * @param id     identification of the binding
     *
     * @return the handler created
     */
    public static Registration of(Binder binder, String id) {
        return new BinderRegistration(binder, id, null);
    }

    @Override
    public void removeHandler() {
        if (handlerRegistrations != null) {
            // If there were other handlers bound to this one, then remove them
            for (HandlerRegistration handlerRegistration : handlerRegistrations) {
                handlerRegistration.removeHandler();
            }
        }
        binder.unbind(id);
    }
}
