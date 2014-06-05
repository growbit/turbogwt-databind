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
package org.turbogwt.mvp.databind.client.validation;

import javax.annotation.Nullable;

/**
 * Interface used to validate databind flushes from view.
 * <p/>
 * You should implement one validator for each property that requires a specific validation. The property is assumed to
 * be known outside its validator.
 *
 * @param <T> Model type
 * @param <F> Field type
 *
 * @author Danilo Reinert
 */
public interface Validator<T, F> {

    /**
     * Validates a value to be set in a model and returns a {@link Validation}. The validation contains a flag informing
     * if it was valid or not and a {@link ValidationMessage} provided with a textual user-friendly message and a {@link
     * ValidationMessage.Type}.
     * <p/>
     * The lack of an property's id argument does not limits its implementation because the property is known by the
     * time of binding.
     *
     * @param t model which will hold (or not) the value
     * @param value  value being passed to the model
     *
     * @return validation result
     */
    Validation validate(T t, @Nullable F value);
}
