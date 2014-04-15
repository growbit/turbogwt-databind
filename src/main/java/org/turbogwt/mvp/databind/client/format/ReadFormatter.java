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
 * One-way formatter for read-only circumstances. Usually it will format a raw value to nicely present to the user.
 *
 * @param <MODEL> Type of the value at the model.
 * @param <VIEW>  Type of the value at the view.
 *
 * @author Danilo Reinert
 */
public interface ReadFormatter<MODEL, VIEW> {

    /**
     * Given the raw value, it returns a formatted value for view presentation. The View should know only about the
     * formatted value.
     *
     * @param rawValue raw value held by the model
     *
     * @return formatted value
     */
    @Nullable
    VIEW format(@Nullable MODEL rawValue) throws UnableToFormatException;
}
