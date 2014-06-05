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
 * Object used as return of validations by {@link Validator}.
 * <p/>
 * It provides a simple boolean result stating whether the validation was successful or not and an optional {@link
 * ValidationMessage} with some useful feedback for the user.
 *
 * @author Danilo Reinert
 */
public class Validation {

    // Cache recurring results
    protected static Validation defaultInvalid = new Validation(false, null);
    protected static Validation defaultValid = new Validation(true, null);
    private final boolean valid;
    private final ValidationMessage validationMessage;

    /**
     * This constructor should not be externally used. Otherwise, it is recommended that static factory methods be made
     * available for instantiation.
     * <p/>
     * It is left as protected in order to allow inheritance.
     *
     * @param valid             validation result
     * @param validationMessage validation message for the user
     */
    protected Validation(boolean valid, @Nullable ValidationMessage validationMessage) {
        this.valid = valid;
        this.validationMessage = validationMessage;
    }

    public static Validation invalid() {
        return defaultInvalid;
    }

    public static Validation invalid(ValidationMessage validationMessage) {
        return new Validation(false, validationMessage);
    }

    public static Validation valid() {
        return defaultValid;
    }

    public static Validation valid(ValidationMessage validationMessage) {
        return new Validation(true, validationMessage);
    }

    /**
     * Valid is the final result of some validation. It simply tells if the validation was successful or not.
     *
     * @return {@code true} if valid, {@code false} otherwise.
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * Validation message is an optional feedback for the user. It can be just a neutral info or a useful guideline for
     * handling some error.
     *
     * @return validation message
     */
    @Nullable
    public ValidationMessage getValidationMessage() {
        return validationMessage;
    }
}
