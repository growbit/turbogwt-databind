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
package org.turbogwt.mvp.databind;

/**
 * An interface that must be implemented by the Presenter supporting databind.
 *
 * @author Danilo Reinert
 */
public interface DatabindUiHandler {

    /**
     * Must be called by View when some bound widget changes value.
     *
     * @param id    property id
     * @param value new value from view (may need unformatting)
     */
    void onValueChanged(String id, Object value);
}
