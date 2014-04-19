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

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.HasValue;
import com.google.web.bindery.event.shared.HandlerRegistration;

import org.turbogwt.mvp.databind.mock.HasValueMock;

/**
 * @author Danilo Reinert
 */
public class DatabindViewEngineTest extends GWTTestCase {

    @Override
    public String getModuleName() {
        return "org.turbogwt.mvp.databind.DatabindTest";
    }

    interface BindWidgetCallback {
        HandlerRegistration bind(DatabindViewEngine engine, String propertyId, TakesValue widget);
    }

    public void testBindReadOnlyWidget() {
        doTestBindWidget(new BindWidgetCallback() {
            @Override
            public HandlerRegistration bind(DatabindViewEngine engine, String propertyId, TakesValue widget) {
                return engine.bind(propertyId, widget);
            }
        });
    }

    public void testBindWidget() {
        doTestBindWidget(new BindWidgetCallback() {
            @Override
            public HandlerRegistration bind(DatabindViewEngine engine, String propertyId, TakesValue widget) {
                return engine.bind(propertyId, (HasValue<?>) widget, Strategy.ON_CHANGE);
            }
        });
    }

    public void testUnbindReadOnlyWidget() {
        doTestUnbindWidget(new BindWidgetCallback() {
            @Override
            public HandlerRegistration bind(DatabindViewEngine engine, String propertyId, TakesValue widget) {
                return engine.bind(propertyId, widget);
            }
        });
    }

    public void testUnbindWidget() {
        doTestUnbindWidget(new BindWidgetCallback() {
            @Override
            public HandlerRegistration bind(DatabindViewEngine engine, String propertyId, TakesValue widget) {
                return engine.bind(propertyId, (HasValue<?>) widget, Strategy.ON_CHANGE);
            }
        });
    }

    private void doTestBindWidget(BindWidgetCallback callback) {
        /*
        The binding must be tested performing both #DatabindViewEngine.getValue and #DatabindViewEngine.setValue.
         */
        final DatabindViewEngine engine = new DatabindViewEngine();

        final String propertyId = "someProperty";
        final String initialValue = "initialValue";

        final HasValue<String> takesString = new HasValueMock<String>();
        takesString.setValue(initialValue);

        // Bind the widget to the view
        callback.bind(engine, propertyId, takesString);

        // Assert the view delegates the value to the bound widget
        assertEquals(initialValue, engine.getValue(propertyId));

        final String newValue = "newValue";

        // Perform set operation through the view
        engine.setValue(propertyId, newValue);

        // Assert the previous set operation was successful
        assertEquals(newValue, engine.getValue(propertyId));
    }

    private void doTestUnbindWidget(BindWidgetCallback callback) {
        /*
        The unbinding must be tested performing both #DatabindViewEngine.unbind and #HandlerRegistration.removeHandler.
         */
        final DatabindViewEngine engine = new DatabindViewEngine();

        final String someProperty = "someProperty";
        final String somePropertyInitialValue = "initialValue";

        final String otherProperty = "otherProperty";
        final Double otherPropertyInitialValue = 3.42;

        final HasValue<String> somePropertyWidget = new HasValueMock<String>();
        somePropertyWidget.setValue(somePropertyInitialValue);

        final HasValue<Double> otherPropertyWidget = new HasValueMock<Double>();
        otherPropertyWidget.setValue(otherPropertyInitialValue);

        // Bind the widget to the view
        HandlerRegistration somePropertyHandlerRegistration = callback.bind(engine, someProperty, somePropertyWidget);
        HandlerRegistration otherPropertyHandlerRegistration = callback.bind(engine, otherProperty,
                otherPropertyWidget);

        // Assert handler registrations created
        assertNotNull(somePropertyHandlerRegistration);
        assertNotNull(otherPropertyHandlerRegistration);

        // Assert unbind via handler registration
        somePropertyHandlerRegistration.removeHandler();
        assertFalse(engine.unbind(someProperty));

        // Assert unbind via engine
        assertTrue(engine.unbind(otherProperty));
        assertFalse(engine.unbind(otherProperty));
    }
}
