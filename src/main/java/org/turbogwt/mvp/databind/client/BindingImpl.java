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

import java.util.Iterator;

import org.turbogwt.core.util.shared.Registration;
import org.turbogwt.mvp.databind.client.format.Formatter;
import org.turbogwt.mvp.databind.client.format.UnableToFormatException;
import org.turbogwt.mvp.databind.client.property.PropertyAccessor;
import org.turbogwt.mvp.databind.client.validation.Validation;
import org.turbogwt.mvp.databind.client.validation.Validator;

/**
 * Manager responsible for binding property accessors, validators and formatters to model properties. It updates these
 * values while the user interacts with the view.
 *
 * @param <T> Model type
 *
 * @author Danilo Reinert
 */
public class BindingImpl<T> implements Binding<T> {

    private final PresenterEngine<T> engine = new PresenterEngine<>();
    private final BindingView view;
    private T model;

    @SuppressWarnings("unchecked")
    public BindingImpl(BindingView view) {
        if (view == null) {
            throw new NullPointerException("The parameter _view_ cannot be null");
        }
        this.view = view;
        this.view.setBindingHandler(this);
    }

    @SuppressWarnings("unchecked")
    public BindingImpl(BindingView view, BindingHandler uiHandlers) {
        if (view == null) {
            throw new NullPointerException("The parameter _view_ cannot be null");
        }
        this.view = view;
        this.view.setBindingHandler(uiHandlers);
    }

    @Override
    public <F> Registration bind(String id, PropertyAccessor<T, F> propertyAccessor) {
        return engine.bind(id, propertyAccessor);
    }

    @Override
    public <F> Registration bind(String id, PropertyAccessor<T, F> propertyAccessor,
                                 Validator<T, F> validatesValue) {
        return engine.bind(id, propertyAccessor, validatesValue);
    }

    @Override
    public <F> Registration bind(String id, PropertyAccessor<T, F> propertyAccessor,
                                 Validator<T, F> validatesValue, Formatter<F, ?> formatter) {
        return engine.bind(id, propertyAccessor, validatesValue, formatter);
    }

    @Override
    public <F> Registration bind(String id, PropertyAccessor<T, F> propertyAccessor,
                                 Formatter<F, ?> formatter) {
        return engine.bind(id, propertyAccessor, formatter);
    }

    @Override
    public <F> Registration bind(boolean autoRefresh, String id,
                                 PropertyAccessor<T, F> propertyAccessor) {
        return engine.bind(autoRefresh, id, propertyAccessor);
    }

    @Override
    public <F> Registration bind(boolean autoRefresh, String id, PropertyAccessor<T, F> propertyAccessor,
                                 Formatter<F, ?> formatter) {
        return engine.bind(autoRefresh, id, propertyAccessor, formatter);
    }

    @Override
    public <F> Registration bind(boolean autoRefresh, String id, PropertyAccessor<T, F> propertyAccessor,
                                 Validator<T, F> validatesValue) {
        return engine.bind(autoRefresh, id, propertyAccessor, validatesValue);
    }

    @Override
    public <F> Registration bind(boolean autoRefresh, String id, PropertyAccessor<T, F> propertyAccessor,
                                 Validator<T, F> validatesValue, Formatter<F, ?> formatter) {
        return engine.bind(autoRefresh, id, propertyAccessor, validatesValue, formatter);
    }

    /**
     * Get all values from view, apply to the model and return if all of them were valid.
     *
     * @return {@code true} if all values were valid, {@code false} otherwise
     */
    @Override
    public boolean flush() {
        boolean isValid = true;
        for (String id : engine) {
            try {
                isValid = doFlush(id);
            } catch (ClassCastException e) {
                throwMistypedBindingException(id, e);
            }
        }
        return isValid;
    }

    /**
     * Get value from view, apply to the model and return if it was not invalid.
     *
     * @param id identification of the property
     *
     * @return {@code false} if it was invalid, {@code true} otherwise
     */
    @Override
    public boolean flush(String id) {
        if (engine.hasProperty(id)) {
            try {
                return doFlush(id);
            } catch (ClassCastException e) {
                throwMistypedBindingException(id, e);
            }
        } // Id not bound, then the value was not invalid
        return true;
    }

    /**
     * Get the bound model.
     *
     * @return model object
     */
    @Override
    public T getModel() {
        return model;
    }

    /**
     * Get property accessor from specified property.
     *
     * @param id identification of the property
     *
     * @return property accessor
     */
    @Override
    @SuppressWarnings("unchecked")
    public <V> PropertyAccessor<T, V> getPropertyAccessor(String id) {
        return (PropertyAccessor<T, V>) engine.getPropertyAccessor(id);
    }

    /**
     * Get validator from specified property.
     *
     * @param id identification of the property
     *
     * @return validator
     */
    @Override
    @SuppressWarnings("unchecked")
    public <V> Validator<T, V> getValidator(String id) {
        return (Validator<T, V>) engine.getValidatesValue(id);
    }

    /**
     * Get property's value from model.
     *
     * @param id identification of the property
     *
     * @return value retrieved from model's property
     */
    @Override
    @SuppressWarnings("unchecked")
    public <V> V getValue(String id) {
        return (V) engine.getRawValue(id, model);
    }

    /**
     * Get the bound view.
     *
     * @return the bound view
     */
    @Override
    public BindingView getView() {
        return view;
    }

    /**
     * Returns {@code true} if this binding has a property accessor bound to this property id.
     *
     * @param id identification of the property
     *
     * @return {@code true} if this property id is bound, {@code false} otherwise.
     */
    @Override
    public boolean hasProperty(String id) {
        return engine.hasProperty(id);
    }

    /**
     * Informs if the property should be automatically sent to view when updating model.
     *
     * @param id identification of the property
     *
     * @return {@code true} if property is set to view by updating the model, {@code false} otherwise
     */
    @Override
    public boolean isAutoRefresh(String id) {
        return engine.isAutoRefresh(id);
    }

    /**
     * Returns an iterator over bound properties' ids.
     *
     * @return an Iterator of ids.
     */
    @Override
    public Iterator<String> iterator() {
        return engine.iterator();
    }

    /**
     * Must be called by View when some bound widget changes value.
     * <p/>
     * Presenter must implement #DatabindUiHandlers and delegate the execution of #DatabindUiHandlers.onValueChanged to
     * this method.
     *
     * @param id    property id
     * @param value new value from view (may need unformatting)
     */
    @Override
    public void onValueChanged(String id, Object value) {
        try {
            setValueToModel(id, value);
        } catch (ClassCastException e) {
            throwMistypedBindingException(id, e);
        }
    }

    /**
     * Send all properties' values to view.
     */
    @Override
    public void refresh() {
        for (String id : engine) {
            try {
                setValueToView(id);
            } catch (ClassCastException e) {
                throwMistypedBindingException(id, e);
            }
        }
    }

    /**
     * Send specified property's value to view.
     *
     * @param id identification of the property
     */
    @Override
    public void refresh(String id) {
        if (engine.hasProperty(id)) {
            try {
                setValueToView(id);
            } catch (ClassCastException e) {
                throwMistypedBindingException(id, e);
            }
        }
    }

    /**
     * Send all auto refresh properties' values to view.
     */
    @Override
    public void refreshAutoOnly() {
        for (String id : engine) {
            if (engine.isAutoRefresh(id)) {
                try {
                    setValueToView(id);
                } catch (ClassCastException e) {
                    throwMistypedBindingException(id, e);
                }
            }
        }
    }

    /**
     * Set a model to this databinding and send all bound (auto refresh) properties to view.
     *
     * @param model model object
     */
    @Override
    public void setModel(T model) {
        this.model = model;
        refreshAutoOnly();
    }

    /**
     * Set value to model's property.
     *
     * @param id    identification of the property
     * @param value value to be applied
     */
    @Override
    public void setValue(String id, Object value) {
        engine.setRawValue(id, model, value);
        refresh(id);
    }

    @Override
    public boolean unbind(String id) {
        return engine.unbind(id);
    }

    private boolean doFlush(String id) {
        Object formattedValue = view.getValue(id);
        /*
        // Check if value has changed and submit to validation
        if (formattedValue != null && isValueDifferent(id, formattedValue)) {
            // Value differs from current
            if (!setValueToModel(id, formattedValue)) {
                // Could not set a different value to model, then it was invalid
                return false;
            }
        }
        */
        // Always submit value to validation, even it has not changed
        if (!setValueToModel(id, formattedValue)) {
            // Could not set value to model, then it was invalid
            return false;
        }
        return true;
    }

    /**
     * Check if the formatted value is different from the current value in model.
     *
     * @param id identification of the property
     * @param formattedValue value from view
     * @return {@code true} if the value is different from current, {@code false} otherwise
     */
    private boolean isValueDifferent(String id, Object formattedValue) throws UnableToFormatException {
        if (hasProperty(id)) {
            final Object value = engine.getRawValue(id, model);
            Object unformattedValue = engine.unformat(id, formattedValue);

            // If both are null then they are not different
            if (value == null && unformattedValue == null) {
                return false;
            }

            // Avoid NPE
            if (value != null) {
                return !value.equals(unformattedValue);
            }
        }
        // We cannot tell they are different because this id is not bound
        return false;
    }

    /**
     * Validate and apply a value to a property and return if it was successfully set. When the model is *null*, no
     * value is set and {@code false} is returned.
     *
     * @param id    identification of the property
     * @param value value to be applied
     *
     * @return {@code true} if the value was validated and applied, {@code false} otherwise
     */
    private boolean setValueToModel(String id, Object value) {
        if (model == null) {
            return false;
        }

        // First, validate the value
        final Validation validation = engine.isValueValid(id, model, value);
        if (validation.isValid()) {
            // If valid, then set to the model and fire valid value event
            engine.setFormattedValue(id, model, value);
            view.onValidationSuccess(id, validation.getValidationMessage());
            return true;
        } else {
            // It must be executed only when a validation occurs and it returns invalid
            view.onValidationFailure(id, validation.getValidationMessage());
        }

        return false;
    }

    private void setValueToView(String id) {
        if (model != null) {
            view.setValue(id, engine.getFormattedValue(id, model));
        } else {
            // If there is no model then send null to all properties
            view.setValue(id, null);
        }
    }

    private void throwMistypedBindingException(String id, ClassCastException e) {
        throw new MistypedBindingException("The binding of \"" + id + "\" is improperly set."
                + " The types of both sides of the binding (view and model) do not match."
                + " You should conform your widget value type to your property type", e);
    }
}
