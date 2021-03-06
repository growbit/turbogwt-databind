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

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

/**
 * @param <V> Value type
 *
 * @author Danilo Reinert
 */
public class HasValueMock<V> extends TakesValueMock<V> implements HasValue<V>, IsWidget {

    private HandlerManager handlerManager;

    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<V> handler) {
        return addHandler(handler, ValueChangeEvent.getType());
    }

    /**
     * Returns the {@link com.google.gwt.user.client.ui.Widget} aspect of the receiver.
     */
    @Override
    public Widget asWidget() {
        return null;
    }

    @Override
    public void fireEvent(GwtEvent<?> event) {
        if (handlerManager != null) {
            handlerManager.fireEvent(event);
        }
    }

    public final <H extends EventHandler> HandlerRegistration addHandler(H handler, GwtEvent.Type<H> type) {
        return ensureHandlerManager().addHandler(type, handler);
    }

    @Override
    public void setValue(V value) {
        setValue(value, true);
    }

    @Override
    public void setValue(V value, boolean fireEvents) {
        V oldValue = getValue();
        super.setValue(value);
        if (fireEvents) {
            ValueChangeEvent.fireIfNotEqual(this, oldValue, value);
        }
    }

    private HandlerManager ensureHandlerManager() {
        return handlerManager == null ? handlerManager = new HandlerManager(this) : handlerManager;
    }
}
