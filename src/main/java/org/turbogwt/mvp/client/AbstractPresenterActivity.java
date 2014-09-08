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
package org.turbogwt.mvp.client;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public abstract class AbstractPresenterActivity extends AbstractActivity {

    private GwtPresenter<?> presenter;
    private AcceptsOneWidget panel;

    public abstract GwtPresenter<?> getPresenter(EventBus eventBus);

    public abstract void onStart(EventBus eventBus);

    public final void init() {
        presenter.onStart();
        panel.setWidget(presenter.asWidget());
    }

    @Override
    public final void start(AcceptsOneWidget panel, EventBus eventBus) {
        this.panel = panel;
        this.presenter = getPresenter(eventBus);
        this.presenter.bind();
        onStart(eventBus);
    }

    @Override
    public String mayStop() {
        return presenter.mayStop();
    }

    @Override
    public void onStop() {
        presenter.onStop();
        presenter.unbind();
    }
}
