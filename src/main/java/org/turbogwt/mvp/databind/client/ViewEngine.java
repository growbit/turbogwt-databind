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
package org.turbogwt.mvp.databind.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.event.shared.HandlerRegistration;

import java.util.Map;

import org.turbogwt.core.collections.client.LightMap;
import org.turbogwt.core.util.shared.Registration;

/**
 * It is responsible for managing the binding on the View side.
 * <p/>
 *
 * This class should be used by any {@link BindingView} as a delegatee.
 * <p/>
 *
 * You can even create an abstract view implementing {@link BindingView} and delegating the properly methods to this
 * engine. Then all your views supporting databinding could extend from this one.
 *
 * @author Danilo Reinert
 * @see BindingViewImpl
 */
@SuppressWarnings("unchecked")
public class ViewEngine implements WidgetBinder, HasBindingValues, HasBindingHandler {

    public static class WidgetBindingMap extends LightMap<WidgetBinding> { }

    public static class WidgetBinding {

        TakesValue widget;
        HandlerRegistration widgetHandlerRegistration;
        // TODO: Allow binding ValidationHandler?
//        ValidationHandler validationHandler;

        WidgetBinding(TakesValue widget, HandlerRegistration widgetHandlerRegistration) {
            this.widget = widget;
            this.widgetHandlerRegistration = widgetHandlerRegistration;
        }

        WidgetBinding(TakesValue widget) {
            this.widget = widget;
        }
    }

    private final Map<String, WidgetBinding> bindings = GWT.create(WidgetBindingMap.class);
    private BindingHandler uiHandler;

    @Override
    public <F> F getValue(String id) {
        final WidgetBinding widgetBinding = bindings.get(id);
        if (widgetBinding != null) {
            TakesValue<?> hasValue = widgetBinding.widget;
            if (hasValue != null) {
                return (F) hasValue.getValue();
            }
        }
        return null;
    }

    @Override
    public <F> void setValue(String id, F value) {
        final WidgetBinding widgetBinding = bindings.get(id);
        if (widgetBinding != null) {
            TakesValue<?> hasValue = widgetBinding.widget;
            if (hasValue != null) {
                ((TakesValue<F>) hasValue).setValue(value);
            }
        }
    }

    @Override
    public void setBindingHandler(BindingHandler uiHandler) {
        this.uiHandler = uiHandler;
    }

    @Override
    public <F> Registration bind(final String id, final HasValue<F> widget, Strategy strategy) {
        assert widget instanceof IsWidget : "HasValue parameter must be of type IsWidget";

        // Add update handler
        HandlerRegistration handlerRegistration = strategy.bind((IsWidget) widget, new Command() {
            @Override
            public void execute() {
                fireValueChangedEvent(id, widget);
            }
        });

        if (bindings.containsKey(id)) {
            // If id were already bound, then update the binding
            WidgetBinding widgetBinding = bindings.get(id);
            if (widgetBinding.widgetHandlerRegistration != null) {
                // Remove previous existing handler avoiding memory leak
                widgetBinding.widgetHandlerRegistration.removeHandler();
            }
            widgetBinding.widget = widget;
            widgetBinding.widgetHandlerRegistration = handlerRegistration;
        } else {
            WidgetBinding widgetBinding = new WidgetBinding(widget, handlerRegistration);
            bindings.put(id, widgetBinding);
        }
        return BinderRegistration.of(this, id);
    }

    @Override
    public <F> Registration bind(String id, TakesValue<F> widget) {
//        assert (widget instanceof IsWidget) : "TakesValue parameter must be of type IsWidget";

        if (bindings.containsKey(id)) {
            WidgetBinding widgetBinding = bindings.get(id);
            widgetBinding.widget = widget;
        } else {
            WidgetBinding widgetBinding = new WidgetBinding(widget);
            bindings.put(id, widgetBinding);
        }
        return BinderRegistration.of(this, id);
    }

    @Override
    public boolean unbind(String id) {
        final WidgetBinding widgetBinding = bindings.get(id);
        bindings.remove(id);
        if (widgetBinding != null) {
            if (widgetBinding.widgetHandlerRegistration != null) {
                widgetBinding.widgetHandlerRegistration.removeHandler();
            }
            return true;
        }
        return false;
    }

    private <F> void fireValueChangedEvent(String id, HasValue<F> widget) {
        // Avoid NPE. The null uiHandler should be notified before reaching here.
        if (uiHandler != null) {
            uiHandler.onValueChange(id, widget.getValue());
        }
    }
}
