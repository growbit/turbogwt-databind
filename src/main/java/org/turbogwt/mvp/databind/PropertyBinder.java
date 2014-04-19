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

import org.turbogwt.core.util.Registration;
import org.turbogwt.mvp.databind.format.Formatter;
import org.turbogwt.mvp.databind.property.PropertyAccessor;
import org.turbogwt.mvp.databind.validation.Validator;

/**
 * A Binder that supports binding property accessors, validators and formatters to a property id.
 *
 * @param <T> Model type
 *
 * @author Danilo Reinert
 */
public interface PropertyBinder<T> extends Binder {

    <F> Registration bind(String id, PropertyAccessor<T, F> propertyAccessor);

    <F> Registration bind(String id, PropertyAccessor<T, F> propertyAccessor,
                          Validator<T, F> validatesValue);

    <F> Registration bind(String id, PropertyAccessor<T, F> propertyAccessor,
                          Validator<T, F> validatesValue, Formatter<F, ?> formatter);

    <F> Registration bind(String id, PropertyAccessor<T, F> propertyAccessor, Formatter<F, ?> formatter);

    <F> Registration bind(boolean autoRefresh, String id, PropertyAccessor<T, F> propertyAccessor);

    <F> Registration bind(boolean autoRefresh, String id, PropertyAccessor<T, F> propertyAccessor,
                          Validator<T, F> validatesValue);

    <F> Registration bind(boolean autoRefresh, String id, PropertyAccessor<T, F> propertyAccessor,
                          Validator<T, F> validatesValue, Formatter<F, ?> formatter);

    <F> Registration bind(boolean autoRefresh, String id, PropertyAccessor<T, F> propertyAccessor,
                          Formatter<F, ?> formatter);
}
