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

import org.turbogwt.mvp.databind.client.property.PropertyAccessor;
import org.turbogwt.mvp.databind.client.validation.Validator;

/**
 * Manager responsible for binding property accessors, validators and formatters to model properties. It updates these
 * values while the user interacts with the view.
 *
 * @param <T> Model type
 *
 * @author Danilo Reinert
 */
public interface Binding<T> extends PropertyBinder<T>, BindingHandler, Iterable<String>, Binder {

    /**
     * Get all values from view, apply to the model and return if all of them were valid.
     *
     * @return {@code true} if all values were valid, {@code false} otherwise
     */
    boolean flush();

    /**
     * Get value from view, apply to the model and return if it was not invalid.
     *
     * @param id identification of the property
     *
     * @return {@code false} if it was invalid, {@code true} otherwise
     */
    boolean flush(String id);

    /**
     * Get the bound model.
     *
     * @return model object
     */
    T getModel();

    /**
     * Get property accessor from specified property.
     *
     * @param id identification of the property
     *
     * @return property accessor
     */
    <V> PropertyAccessor<T, V> getPropertyAccessor(String id);

    /**
     * Get validator from specified property.
     *
     * @param id identification of the property
     *
     * @return validator
     */
    <V> Validator<T, V> getValidator(String id);

    /**
     * Get property's value from model.
     *
     * @param id identification of the property
     *
     * @return value retrieved from model's property
     */
    <V> V getValue(String id);

    /**
     * Get the bound view.
     *
     * @return the bound view
     */
    BindingView getView();

    /**
     * Returns {@code true} if this binding has a property accessor bound to this property id.
     *
     * @param id identification of the property
     *
     * @return {@code true} if this property id is bound, {@code false} otherwise.
     */
    boolean hasProperty(String id);

    /**
     * Informs if the property should be automatically sent to view when updating model.
     *
     * @param id identification of the property
     *
     * @return {@code true} if property is set to view by updating the model, {@code false} otherwise
     */
    boolean isAutoRefresh(String id);

    /**
     * Returns an iterator over bound properties' ids.
     *
     * @return an Iterator of ids.
     */
    @Override
    Iterator<String> iterator();

    /**
     * Send all properties' values to view.
     */
    void refresh();

    /**
     * Send specified property's value to view.
     *
     * @param id identification of the property
     */
    void refresh(String id);

    /**
     * Send all auto refresh properties' values to view.
     */
    void refreshAutoOnly();

    /**
     * Set a model to this databinding and send all bound (auto refresh) properties to view.
     *
     * @param model model object
     */
    void setModel(T model);

    /**
     * Set value to model's property.
     *
     * @param id    identification of the property
     * @param value value to be applied
     */
    void setValue(String id, Object value);
}
