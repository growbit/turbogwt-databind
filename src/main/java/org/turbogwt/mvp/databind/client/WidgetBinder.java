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

import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.HasValue;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * @author Danilo Reinert
 */
public interface WidgetBinder extends Binder {

    /**
     * Bind a widget to a property id. Every time an event related to the selected strategy occurs, the updated value is
     * sent to the uiHandlers (Presenter)
     *
     * @param id       identification of property
     * @param widget   widget to bind
     * @param strategy strategy of value update
     * @param <F>      value type
     *
     * @return HandlerRegistration of this binding
     */
    <F> HandlerRegistration bind(String id, HasValue<F> widget, Strategy strategy);

    /**
     * Bind a widget with default strategy (NONE). This binding will behavior like a read-only widget.
     *
     * @param id     identification of property
     * @param widget widget to bind
     * @param <F>    value type
     *
     * @return HandlerRegistration of this binding
     */
    <F> HandlerRegistration bind(String id, TakesValue<F> widget);
}
