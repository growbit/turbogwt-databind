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
package org.turbogwt.mvp.databind.client.format;

import javax.annotation.Nullable;

/**
 * Two-way formatter used for formatting and parsing values to/from view.
 *
 * @param <MODEL> Type of the value at the model.
 * @param <VIEW>  Type of the value at the view.
 *
 * @author Danilo Reinert
 */
public interface Formatter<MODEL, VIEW> extends ReadFormatter<MODEL, VIEW> {

    /**
     * Given the formatted value, it parses into a row value for sending to the model. The Model should know only about
     * the raw value.
     *
     * @param formattedValue held by the view
     *
     * @throws org.turbogwt.mvp.databind.client.format.UnableToFormatException if could not perform unformatting.
     *
     * @return raw value
     */
    @Nullable
    MODEL unformat(@Nullable VIEW formattedValue) throws UnableToFormatException;
}
