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
package org.turbogwt.mvp.client.databind;

import com.google.gwt.user.client.ui.Widget;

import org.turbogwt.mvp.databind.client.Binding;

public abstract class AbstractDatabindPresenter<V extends DatabindView<?>> implements DatabindPresenter<V> {

    private final V view;
    private final Binding<?> binding;

    protected AbstractDatabindPresenter(V view, Binding<?> binding) {
        this.view = view;
        this.binding = binding;
    }

    @Override
    public void unbind() {
        view.setHandler(null);
    }

    @Override
    public void onStart() {
    }

    @Override
    public String mayStop() {
        return null;
    }

    @Override
    public void onStop() {
    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
    }

    @Override
    public V getView() {
        return view;
    }

    @Override
    public void onValueChange(String id, Object value) {
        binding.onValueChange(id, value);
    }
}
