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

/**
 * It defines an object that supports binding.
 * <p/>
 *
 * The underlying implementation should provide methods for binding instances to ids and must support an #unbind method
 * that cancels the binding of some id.
 *
 * @author Danilo Reinert
 */
public interface Binder {

    /**
     * Releases any binding previously made with the given id.
     *
     * @param id id of the binding
     *
     * @return {@code true} if any binding associated with the id was removed, {@code false} otherwise.
     */
    boolean unbind(String id);
}
