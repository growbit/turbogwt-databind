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

import com.google.web.bindery.event.shared.HandlerRegistration;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.turbogwt.mvp.databind.client.format.Formatter;
import org.turbogwt.mvp.databind.client.property.PropertyAccessor;
import org.turbogwt.mvp.databind.client.validation.Validation;
import org.turbogwt.mvp.databind.client.validation.Validator;

/**
 * @param <T> Model type
 *
 * @author Danilo Reinert
 */
@SuppressWarnings("unchecked")
public class PresenterEngine<T> implements PropertyBinder<T>, Iterable<String> {

    private static class PropertyBinding {

        PropertyAccessor propertyAccessor;
        Validator validator;
        Formatter formatter;
        boolean autoRefresh;
        // TODO: Add readOnly flag?
        // The readOnly flag would not allow receiving values from View.

        PropertyBinding(boolean autoRefresh, PropertyAccessor propertyAccessor, Validator validator,
                        Formatter formatter) {
            this.propertyAccessor = propertyAccessor;
            this.validator = validator;
            this.formatter = formatter;
            this.autoRefresh = autoRefresh;
        }

        PropertyBinding(boolean autoRefresh, PropertyAccessor propertyAccessor) {
            this(autoRefresh, propertyAccessor, null, null);
        }

        PropertyBinding(boolean autoRefresh, PropertyAccessor propertyAccessor, Formatter formatter) {
            this(autoRefresh, propertyAccessor, null, formatter);
        }

        PropertyBinding(boolean autoRefresh, PropertyAccessor propertyAccessor, Validator validator) {
            this(autoRefresh, propertyAccessor, validator, null);
        }
    }

    // TODO: substitute map by a simple javascript object to increase performance
    private final Map<String, PropertyBinding> properties = new HashMap<String, PropertyBinding>();

    public <F> HandlerRegistration bind(String id, PropertyAccessor<T, F> propertyAccessor) {
        return bind(true, id, propertyAccessor);
    }

    public <F> HandlerRegistration bind(String id, PropertyAccessor<T, F> propertyAccessor,
                                        Validator<T, F> validator) {
        return bind(true, id, propertyAccessor, validator);
    }

    @Override
    public <F> HandlerRegistration bind(String id, PropertyAccessor<T, F> propertyAccessor,
                                        Validator<T, F> validator, Formatter<F, ?> formatter) {
        return bind(true, id, propertyAccessor, validator, formatter);
    }

    @Override
    public <F> HandlerRegistration bind(String id, PropertyAccessor<T, F> propertyAccessor,
                                        Formatter<F, ?> formatter) {
        return bind(true, id, propertyAccessor, formatter);
    }

    public <F> HandlerRegistration bind(boolean autoRefresh, String id, PropertyAccessor<T, F> propertyAccessor) {
        return bind(autoRefresh, id, propertyAccessor, null, null);
    }

    @Override
    public <F> HandlerRegistration bind(boolean autoRefresh, String id, PropertyAccessor<T, F> propertyAccessor,
                                        Formatter<F, ?> formatter) {
        return bind(autoRefresh, id, propertyAccessor, null, formatter);
    }

    @Override
    public <F> HandlerRegistration bind(boolean autoRefresh, String id, PropertyAccessor<T, F> propertyAccessor,
                                        Validator<T, F> validator) {
        return bind(autoRefresh, id, propertyAccessor, validator, null);
    }

    @Override
    public <F> HandlerRegistration bind(boolean autoRefresh, String id, PropertyAccessor<T, F> propertyAccessor,
                                        Validator<T, F> validator, Formatter<F, ?> formatter) {
        if (properties.containsKey(id)) {
            PropertyBinding propertyBinding = properties.get(id);
            propertyBinding.propertyAccessor = propertyAccessor;
            propertyBinding.validator = validator;
            propertyBinding.formatter = formatter;
        } else {
            PropertyBinding propertyBinding = new PropertyBinding(autoRefresh, propertyAccessor, validator, formatter);
            properties.put(id, propertyBinding);
        }
        return BinderHandlerRegistration.of(this, id);
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
            return propertyBinding.validator.validate(data, unformat(id, value));
        }
        return Validation.valid();
    }

    public Object getRawValue(String id, T data) {
        PropertyBinding propertyBinding = properties.get(id);
        if (propertyBinding != null) {
            return propertyBinding.propertyAccessor.getValue(data);
        }
        return null;
    }

    public Object getFormattedValue(String id, T data) {
        PropertyBinding propertyBinding = properties.get(id);
        if (propertyBinding != null) {
            if (propertyBinding.formatter != null) {
                return propertyBinding.formatter.format(propertyBinding.propertyAccessor.getValue(data));
            }
            return propertyBinding.propertyAccessor.getValue(data);
        }
        return null;
    }

    public void setRawValue(String id, T data, Object value) {
        PropertyBinding propertyBinding = properties.get(id);
        if (propertyBinding != null) {
            propertyBinding.propertyAccessor.setValue(data, value);
        }
    }

    public void setFormattedValue(String id, T data, Object value) {
        PropertyBinding propertyBinding = properties.get(id);
        if (propertyBinding != null) {
            if (propertyBinding.formatter != null) {
                propertyBinding.propertyAccessor.setValue(data, propertyBinding.formatter.unformat(value));
            } else {
                propertyBinding.propertyAccessor.setValue(data, value);
            }
        }
    }

    public PropertyAccessor<T, ?> getPropertyAccessor(String id) {
        return properties.get(id).propertyAccessor;
    }

    public Validator<T, ?> getValidatesValue(String id) {
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
        return properties.remove(id) != null;
    }

    public Object unformat(String id, Object formattedValue) {
        PropertyBinding propertyBinding = properties.get(id);
        if (propertyBinding != null && propertyBinding.formatter != null) {
            return propertyBinding.formatter.unformat(formattedValue);
        }
        return formattedValue;
    }
}