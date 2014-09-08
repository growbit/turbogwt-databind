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

import com.google.gwt.user.client.ui.Composite;

import org.turbogwt.mvp.shared.ViewHandler;
import org.turbogwt.mvp.shared.ViewNotBoundException;

public abstract class AbstractView<H extends ViewHandler> extends Composite implements GwtView<H> {

    private H handler;

    @Override
    public void setHandler(H handler) {
        this.handler = handler;
    }

    @Override
    public H getHandler() {
        if (handler == null)
            throw new ViewNotBoundException("There's no Presenter bound to this View.");
        return handler;
    }
}
