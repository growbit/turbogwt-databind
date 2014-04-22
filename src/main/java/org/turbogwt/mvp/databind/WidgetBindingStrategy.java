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
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.event.shared.HandlerRegistration;

import javax.annotation.Nullable;

/**
 * Represents a strategy of binding some command execution to a widget event.
 *
 * @author Danilo Reinert
 */
public interface WidgetBindingStrategy {

    /**
     * Adds a handler to the widget executing the command and returns the HandlerRegistration.
     *
     * @param widget    The widget to bind the command execution
     * @param command   The command to be executed according to the biding strategy
     *
     * @return  The registration of the biding
     */
    @Nullable
    HandlerRegistration bind(IsWidget widget, Scheduler.ScheduledCommand command);
}
