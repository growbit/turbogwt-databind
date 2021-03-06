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
package org.turbogwt.mvp.client.databind;

import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;

import javax.annotation.Nullable;

import org.turbogwt.core.util.shared.Registration;
import org.turbogwt.mvp.databind.client.BindingHandler;
import org.turbogwt.mvp.databind.client.Strategy;
import org.turbogwt.mvp.databind.client.ViewEngine;
import org.turbogwt.mvp.databind.client.validation.ValidationMessage;
import org.turbogwt.mvp.shared.ViewNotBoundException;

public abstract class AbstractDatabindView<H extends DatabindViewHandler> extends Composite implements DatabindView<H> {

    private final ViewEngine engine = new ViewEngine();

    @Override
    public <F> F getValue(String id) {
        return engine.getValue(id);
    }

    /**
     * Notify the view of a invalid try to update a property on the model.
     *
     * @param property model's property id
     * @param message  message from presenter
     */
    @Override
    public void onValidationFailure(String property, @Nullable ValidationMessage message) {
    }

    /**
     * Notify the view of a property updated on model.
     *
     * @param property model's property id
     * @param message  message from presenter
     */
    @Override
    public void onValidationSuccess(String property, @Nullable ValidationMessage message) {
    }

    @Override
    public <F> void setValue(String id, F value) {
        engine.setValue(id, value);
    }

    @Override
    public void setBindingHandler(BindingHandler handler) {
        engine.setBindingHandler(handler);
    }

    @Override
    public <F> Registration bind(String id, HasValue<F> widget, Strategy strategy) {
        return engine.bind(id, widget, strategy);
    }

    @Override
    public <F> Registration bind(String id, TakesValue<F> widget) {
        return engine.bind(id, widget);
    }

    @Override
    public boolean unbind(String id) {
        return engine.unbind(id);
    }

    @Override
    public void setHandler(H handler) {
        engine.setBindingHandler(handler);
    }

    @Override
    public H getHandler() {
        @SuppressWarnings("unchecked")
        final H handler = (H) engine.getBindingHandler();
        if (engine.getBindingHandler() == null)
            throw new ViewNotBoundException("There's no Presenter bound to this View.");
        return handler;
    }
}
