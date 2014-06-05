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
package org.turbogwt.mvp.databind.client.property;

import javax.annotation.Nullable;

import org.turbogwt.core.util.shared.ProvidesValue;

/**
 * This interface is meant to provide access to some property of an object. Simply, it should call the getter and setter
 * of a specific property.
 * <p/>
 * Serves as support for third-party subjects to manage object properties, following the IoC pattern.
 *
 * @param <T> Model type
 * @param <F> Property type
 *
 * @author Danilo Reinert
 * @see <a href="http://martinfowler.com/bliki/InversionOfControl.html">InversionOfControl</a>
 */
public interface PropertyAccessor<T, F> extends ProvidesValue<T, F> {

    /**
     * Sets the given value to the object. The property is known at compile time.
     *
     * @param t     the object containing the property
     * @param value the value to be set into object's property
     */
    void setValue(T t, @Nullable F value);
}
