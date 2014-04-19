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

import junit.framework.TestCase;

/**
 * @author Danilo Reinert
 */
public class EmailValidatorTest extends TestCase {

    public void testValidation() {
        final Object model = new Object();
        final String message = "email invalid";
        EmailValidator<Object> validator = new EmailValidator<Object>(message);

        // Test malformed email
        final String invalidEmail = "abc.def";
        final Validation invalidValidation = validator.validate(model, invalidEmail);
        assertFalse(invalidValidation.isValid());
        assertEquals(invalidValidation.getValidationMessage().getMessage(), message);

        // Test valid email
        final String validEmail = "abc@def.com";
        final Validation validValidation = validator.validate(model, validEmail);
        assertTrue(validValidation.isValid());

        // Test not required
        final Validation nullInvalidValidation = validator.validate(model, null);
        assertTrue(nullInvalidValidation.isValid());
    }

    public void testValidationRequired() {
        final Object model = new Object();
        final String message = "email invalid";
        EmailValidator<Object> validator = new EmailValidator<Object>(message, true);

        // Test required
        Validation nullInvalidValidation = validator.validate(model, null);
        assertFalse(nullInvalidValidation.isValid());
        assertEquals(nullInvalidValidation.getValidationMessage().getMessage(), message);

        // Test malformed email
        final String invalidEmail = "abc.def";
        final Validation invalidValidation = validator.validate(model, invalidEmail);
        assertFalse(invalidValidation.isValid());
        assertEquals(invalidValidation.getValidationMessage().getMessage(), message);

        // Different message for null value
        final String nullMessage = "email null";
        validator = new EmailValidator<Object>(message, true, nullMessage);

        // Test required
        nullInvalidValidation = validator.validate(model, null);
        assertFalse(nullInvalidValidation.isValid());
        assertEquals(nullInvalidValidation.getValidationMessage().getMessage(), nullMessage);
    }
}
