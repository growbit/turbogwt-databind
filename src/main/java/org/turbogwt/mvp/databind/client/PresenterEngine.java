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

import java.util.Iterator;
import java.util.Map;

import org.turbogwt.core.collections.client.LightMap;
import org.turbogwt.core.util.shared.Registration;
import org.turbogwt.mvp.databind.client.format.Formatter;
import org.turbogwt.mvp.databind.client.format.UnableToFormatException;
import org.turbogwt.mvp.databind.client.property.Accessor;
import org.turbogwt.mvp.databind.client.validation.Validation;
import org.turbogwt.mvp.databind.client.validation.Validator;

/**
 * It is responsible for managing the binding on the Presenter side.
 *
 * @param <T> Model type
 *
 * @author Danilo Reinert
 */
@SuppressWarnings("unchecked")
public class PresenterEngine<T> implements PropertyBinder<T>, Iterable<String> {

    public static class PropertyBindingMap extends LightMap<PropertyBinding> { }

    public static class PropertyBinding {

        Accessor accessor;
        Validator validator;
        Formatter formatter;
        boolean autoRefresh;
        // TODO: Add readOnly flag?
        // The readOnly flag would not allow receiving values from View.

        PropertyBinding(boolean autoRefresh, Accessor accessor, Validator validator,
                        Formatter formatter) {
            this.accessor = accessor;
            this.validator = validator;
            this.formatter = formatter;
            this.autoRefresh = autoRefresh;
        }

        PropertyBinding(boolean autoRefresh, Accessor accessor) {
            this(autoRefresh, accessor, null, null);
        }

        PropertyBinding(boolean autoRefresh, Accessor accessor, Formatter formatter) {
            this(autoRefresh, accessor, null, formatter);
        }

        PropertyBinding(boolean autoRefresh, Accessor accessor, Validator validator) {
            this(autoRefresh, accessor, validator, null);
        }
    }

    private final Map<String, PropertyBinding> properties = GWT.create(PropertyBindingMap.class);

    public <F> Registration bind(String id, Accessor<T, F> propertyAccessor) {
        return bind(true, id, propertyAccessor);
    }

    public <F> Registration bind(String id, Accessor<T, F> propertyAccessor,
                                 Validator<T, F> validator) {
        return bind(true, id, propertyAccessor, validator);
    }

    @Override
    public <F> Registration bind(String id, Accessor<T, F> propertyAccessor,
                                 Validator<T, F> validator, Formatter<F, ?> formatter) {
        return bind(true, id, propertyAccessor, validator, formatter);
    }

    @Override
    public <F> Registration bind(String id, Accessor<T, F> propertyAccessor,
                                 Formatter<F, ?> formatter) {
        return bind(true, id, propertyAccessor, formatter);
    }

    public <F> Registration bind(boolean autoRefresh, String id, Accessor<T, F> propertyAccessor) {
        return bind(autoRefresh, id, propertyAccessor, null, null);
    }

    @Override
    public <F> Registration bind(boolean autoRefresh, String id, Accessor<T, F> propertyAccessor,
                                 Formatter<F, ?> formatter) {
        return bind(autoRefresh, id, propertyAccessor, null, formatter);
    }

    @Override
    public <F> Registration bind(boolean autoRefresh, String id, Accessor<T, F> propertyAccessor,
                                 Validator<T, F> validator) {
        return bind(autoRefresh, id, propertyAccessor, validator, null);
    }

    @Override
    public <F> Registration bind(boolean autoRefresh, String id, Accessor<T, F> propertyAccessor,
                                 Validator<T, F> validator, Formatter<F, ?> formatter) {
        if (properties.containsKey(id)) {
            PropertyBinding propertyBinding = properties.get(id);
            propertyBinding.accessor = propertyAccessor;
            propertyBinding.validator = validator;
            propertyBinding.formatter = formatter;
        } else {
            PropertyBinding propertyBinding = new PropertyBinding(autoRefresh, propertyAccessor, validator, formatter);
            properties.put(id, propertyBinding);
        }
        return BinderRegistration.of(this, id);
    }

    /**
     * Tells whether the specified value can be set to the property of the model.
     *
     * @param id    identification of the property
     * @param value value to be validated
     *
     * @return validation
     */
    public Validation isValueValid(String id, T data, Object value) {
        PropertyBinding propertyBinding = properties.get(id);
        if (propertyBinding != null && propertyBinding.validator != null) {
            Object rawValue;
            try {
                // Try to unformat the value.
                rawValue = unformat(id, value);
            } catch (UnableToFormatException e) {
                // Return invalid with the validation message inside the exception.
                return Validation.invalid(e.getValidationMessage());
            }
            return propertyBinding.validator.validate(data, rawValue);
        }
        return Validation.valid();
    }

    public Object getRawValue(String id, T data) {
        PropertyBinding propertyBinding = properties.get(id);
        if (propertyBinding != null) {
            return propertyBinding.accessor.getValue(data);
        }
        return null;
    }

    public Object getFormattedValue(String id, T data) {
        PropertyBinding propertyBinding = properties.get(id);
        if (propertyBinding != null) {
            if (propertyBinding.formatter != null) {
                return propertyBinding.formatter.format(propertyBinding.accessor.getValue(data));
            }
            return propertyBinding.accessor.getValue(data);
        }
        return null;
    }

    public void setRawValue(String id, T data, Object value) {
        PropertyBinding propertyBinding = properties.get(id);
        if (propertyBinding != null) {
            propertyBinding.accessor.setValue(data, value);
        }
    }

    public void setFormattedValue(String id, T data, Object value) {
        PropertyBinding propertyBinding = properties.get(id);
        if (propertyBinding != null) {
            if (propertyBinding.formatter != null) {
                propertyBinding.accessor.setValue(data, propertyBinding.formatter.unformat(value));
            } else {
                propertyBinding.accessor.setValue(data, value);
            }
        }
    }

    public Accessor<T, ?> getAccessor(String id) {
        return properties.get(id).accessor;
    }

    public Validator<T, ?> getValidator(String id) {
        return properties.get(id).validator;
    }

    public boolean isAutoRefresh(String id) {
        return properties.get(id).autoRefresh;
    }

    public boolean hasProperty(String id) {
        return properties.containsKey(id);
    }

    /**
     * Returns an iterator over bound properties' ids.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<String> iterator() {
        return properties.keySet().iterator();
    }

    @Override
    public boolean unbind(String id) {
        boolean removed = properties.containsKey(id);
        properties.remove(id);
        return removed;
    }

    public Object unformat(String id, Object formattedValue) throws UnableToFormatException {
        PropertyBinding propertyBinding = properties.get(id);
        if (propertyBinding != null && propertyBinding.formatter != null) {
            return propertyBinding.formatter.unformat(formattedValue);
        }
        return formattedValue;
    }
}
