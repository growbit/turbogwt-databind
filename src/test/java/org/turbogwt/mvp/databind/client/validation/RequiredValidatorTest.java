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

import junit.framework.TestCase;

/**
 * @author Danilo Reinert
 */
public class RequiredValidatorTest extends TestCase {

    public void testValidation() {
        final Object model = new Object();
        final String message = "value is required";
        RequiredValidator<Object, Object> validator = new RequiredValidator<Object, Object>(message);

        final Validation invalidValidation = validator.validate(model, null);
        assertFalse(invalidValidation.isValid());
        assertEquals(invalidValidation.getValidationMessage().getMessage(), message);

        final Validation validValidation = validator.validate(model, new Object());
        assertTrue(validValidation.isValid());
    }
}
