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

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * Represents the way values are updated on the databinding.
 *
 * @author Danilo Reinert
 */
public enum Strategy implements WidgetBindingStrategy {
    // TODO: Add other strategies: onKey(Up).
    ON_CHANGE {
        @Override
        @SuppressWarnings("unchecked")
        public HandlerRegistration bind(IsWidget widget, final Scheduler.ScheduledCommand command) {
            assert widget instanceof HasValue : "Widget must implement HasValue interface.";
            HasValue hasValue = (HasValue) widget;
            return hasValue.addValueChangeHandler(new ValueChangeHandler() {
                @Override
                public void onValueChange(ValueChangeEvent event) {
                    command.execute();
                }
            });
        }
    }, ON_KEY_UP {
        @Override
        public HandlerRegistration bind(IsWidget widget, final Scheduler.ScheduledCommand command) {
            assert widget instanceof HasKeyUpHandlers : "Widget must implement HasKeyUpHandlers interface.";
            HasKeyUpHandlers hasKeyUpHandlers = (HasKeyUpHandlers) widget;
            return hasKeyUpHandlers.addKeyUpHandler(new KeyUpHandler() {
                @Override
                public void onKeyUp(KeyUpEvent event) {
                    command.execute();
                }
            });
        }
    }, NONE {
        @Override
        public HandlerRegistration bind(IsWidget widget, Scheduler.ScheduledCommand command) {
            return null;
        }
    }
}
