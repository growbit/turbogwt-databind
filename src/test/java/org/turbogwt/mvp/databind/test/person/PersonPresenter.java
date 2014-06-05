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
package org.turbogwt.mvp.databind.test.person;

import com.google.gwt.user.client.ui.IsWidget;

import org.turbogwt.mvp.databind.client.Binding;
import org.turbogwt.mvp.databind.client.BindingImpl;
import org.turbogwt.mvp.databind.client.BindingView;

/**
 * Presenter of an editing view of Person.
 *
 * @author Danilo Reinert
 */
public class PersonPresenter {

    /**
     * Editing view of Person.
     */
    interface PersonView extends BindingView, IsWidget { }

    private final Binding<Person> binding;

    public PersonPresenter(PersonView view) {
        // Initiate the binding with the view
        binding = new BindingImpl<Person>(view);

        // Bind the properties
        binding.bind("name", PersonProperties.NAME_ACCESSOR, PersonProperties.NAME_VALIDATOR);
        binding.bind("birthday", PersonProperties.BIRTHDAY_ACCESSOR, PersonProperties.BIRTHDAY_VALIDATOR);
        binding.bind("phoneNumber", PersonProperties.PHONE_NUMBER_ACCESSOR, PersonProperties.PHONE_NUMBER_VALIDATOR,
                PersonProperties.PHONE_NUMBER_FORMATTER);
        binding.bind("email", PersonProperties.EMAIL_ACCESSOR, PersonProperties.EMAIL_VALIDATOR);
    }

    public void edit(Person person) {
        binding.setModel(person);
    }

    public Person getPerson() {
        return binding.getModel();
    }

    void flush() {
        binding.flush();
    }
}
