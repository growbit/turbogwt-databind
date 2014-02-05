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
 * An object that implements this interface should be a widget with values mapped by String keys, named ids. These ids
 * are useful for identifying source properties in Presenters with databind support.
 *
 * @author Danilo Reinert
 */
public interface HasDatabindValues {

    <F> F getValue(String id);

    <F> void setValue(String id, F value);
}
