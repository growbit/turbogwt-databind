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

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;

import javax.annotation.Nullable;

import org.turbogwt.mvp.databind.client.DatabindViewImpl;
import org.turbogwt.mvp.databind.client.Strategy;
import org.turbogwt.mvp.databind.client.validation.ValidationMessage;

/**
 * Implementation of {@link PersonPresenter.PersonView}.
 *
 * @author Danilo Reinert
 */
public class PersonViewImpl extends DatabindViewImpl implements PersonPresenter.PersonView {

    final TextBox name = new TextBox();
    final DateBox birthday = new DateBox();
    final TextBox phoneNumber = new TextBox();
    final TextBox email = new TextBox();

    boolean nameValid, birthdayValid, phoneNumberValid, emailValid = true;

    public PersonViewImpl() {
        // Construct the View (you can do it via UiBinder too)
        FlowPanel panel = new FlowPanel();
        panel.add(name);
        panel.add(birthday);
        panel.add(phoneNumber);
        panel.add(email);
        initWidget(panel);

        // Bind the widgtes
        bind("name", name, Strategy.ON_CHANGE);
        bind("birthday", birthday, Strategy.ON_CHANGE);
        bind("phoneNumber", phoneNumber, Strategy.ON_CHANGE);
        bind("email", email, Strategy.ON_CHANGE);
    }

    @Override
    public void onValidationFailure(String property, @Nullable ValidationMessage message) {
        // You can optionally handle any validation failures.
        // Usually you will notify the user about the failure and how to fix it.

        if ("name".equals(property)) {
            nameValid = false;
        } else if ("birthday".equals(property)) {
            birthdayValid = false;
        } else if ("phoneNumber".equals(property)) {
            phoneNumberValid = false;
        } else if ("email".equals(property)) {
            emailValid = false;
        }
    }

    @Override
    public void onValidationSuccess(String property, @Nullable ValidationMessage message) {
        // You can optionally handle any validation success.
        // Usually you will clean any error message on display.

        if ("name".equals(property)) {
            nameValid = true;
        } else if ("birthday".equals(property)) {
            birthdayValid = true;
        } else if ("phoneNumber".equals(property)) {
            phoneNumberValid = true;
        } else if ("email".equals(property)) {
            emailValid = true;
        }
    }
}
