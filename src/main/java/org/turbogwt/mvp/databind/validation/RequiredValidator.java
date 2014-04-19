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
package org.turbogwt.mvp.databind.validation;

/**
 * Validator for non-null properties.
 * <p/>
 * Empty string are treated as invalid too.
 *
 * @param <T> Model type
 * @param <F> Field type
 *
 * @author Danilo Reinert
 */
public class RequiredValidator<T, F> implements Validator<T, F> {

    private final Validation invalid;

    /**
     * Creates a {@link RequiredValidator} with given message for invalid results.
     *
     * @param message invalid message
     */
    public RequiredValidator(String message) {
        this.invalid = Validation.invalid(new ValidationMessage(message, ValidationMessage.Type.ERROR));
    }

    @Override
    public Validation validate(T t, F value) {
        return (value != null && !value.toString().isEmpty()) ? Validation.valid() : invalid;
    }
}
