package org.turbogwt.mvp.databind;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.event.shared.HandlerRegistration;

import javax.annotation.Nullable;

/**
 * @author Danilo Reinert
 */
public interface WidgetBindingStrategy {

    @Nullable
    HandlerRegistration bind(IsWidget widget, Scheduler.ScheduledCommand command);
}
