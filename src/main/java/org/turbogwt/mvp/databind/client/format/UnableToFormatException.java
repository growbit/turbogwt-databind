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

import org.turbogwt.mvp.databind.client.validation.ValidationMessage;

/**
 * @author Danilo Reinert
 */
public class UnableToFormatException extends RuntimeException {

    private final ValidationMessage validationMessage;

    /**
     * Create an exception with a {@link org.turbogwt.mvp.databind.client.validation.ValidationMessage}
     * of the given message and type {@link org.turbogwt.mvp.databind.client.validation.ValidationMessage.Type#ERROR}.
     *
     * @param message   Message of the validation
     */
    public UnableToFormatException(String message) {
        super(message);
        this.validationMessage = new ValidationMessage(message, ValidationMessage.Type.ERROR);
    }

    /**
     * Create an exception with a {@link org.turbogwt.mvp.databind.client.validation.ValidationMessage}
     * of the given message and type.
     *
     * @param message   Message of the validation
     * @param type      Type of the validation
     */
    public UnableToFormatException(String message, ValidationMessage.Type type) {
        super(message);
        this.validationMessage = new ValidationMessage(message, type);
    }

    /**
     * Create an exception with a {@link org.turbogwt.mvp.databind.client.validation.ValidationMessage}
     * of the given message and type.
     *
     * @param message   Message of the validation
     * @param type      Type of the validation
     * @param throwable Original exception
     */
    public UnableToFormatException(String message, ValidationMessage.Type type, Throwable throwable) {
        super(message, throwable);
        this.validationMessage = new ValidationMessage(message, type);
    }

    /**
     * Create an exception with a {@link org.turbogwt.mvp.databind.client.validation.ValidationMessage}
     * of the given message and type {@link org.turbogwt.mvp.databind.client.validation.ValidationMessage.Type#ERROR}.
     *
     * @param message   Message of the validation
     * @param throwable Original exception
     */
    public UnableToFormatException(String message, Throwable throwable) {
        super(message, throwable);
        this.validationMessage = new ValidationMessage(message, ValidationMessage.Type.ERROR);
    }

    public ValidationMessage getValidationMessage() {
        return validationMessage;
    }
}
