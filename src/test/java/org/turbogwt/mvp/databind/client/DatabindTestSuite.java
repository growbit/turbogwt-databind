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
package org.turbogwt.mvp.databind.client;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.turbogwt.mvp.databind.client.validation.EmailValidatorTest;
import org.turbogwt.mvp.databind.client.validation.RequiredValidatorTest;

/**
 * @author Danilo Reinert
 */
public class DatabindTestSuite {

    public static Test suite() {
        TestSuite suite = new TestSuite("Databind Test Suite");

        /* Validators */
        suite.addTestSuite(RequiredValidatorTest.class);
        suite.addTestSuite(EmailValidatorTest.class);

        return suite;
    }
}
