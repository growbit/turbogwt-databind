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
package org.turbogwt.mvp.databind.client.mock;

import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.HasValue;

import org.turbogwt.core.util.shared.Registration;
import org.turbogwt.mvp.databind.client.BindingHandler;
import org.turbogwt.mvp.databind.client.BindingView;
import org.turbogwt.mvp.databind.client.Strategy;
import org.turbogwt.mvp.databind.client.ViewEngine;
import org.turbogwt.mvp.databind.client.validation.ValidationMessage;

/**
 * @author Danilo Reinert
 */
public class DatabindViewMock implements BindingView {

    private final ViewEngine engine = new ViewEngine();

    @Override
    public <F> Registration bind(String id, HasValue<F> widget, Strategy strategy) {
        return engine.bind(id, widget, strategy);
    }

    @Override
    public <F> Registration bind(String id, TakesValue<F> widget) {
        return engine.bind(id, widget);
    }

    @Override
    public <F> F getValue(String id) {
        return engine.getValue(id);
    }

    @Override
    public void onValidationFailure(String id, ValidationMessage message) {
    }

    @Override
    public void onValidationSuccess(String id, ValidationMessage message) {
    }

    @Override
    public void setBindingHandler(BindingHandler uiHandlers) {
        engine.setBindingHandler(uiHandlers);
    }

    @Override
    public <F> void setValue(String id, F value) {
        engine.setValue(id, value);
    }

    @Override
    public boolean unbind(String id) {
        return engine.unbind(id);
    }
}
