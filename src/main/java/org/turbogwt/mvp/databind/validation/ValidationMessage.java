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
package org.turbogwt.mvp.databind.validation;

/**
 * Object that holds a textual message for the user and a type for styling purposes.
 * <p/>
 * Usually this message will provide a feedback notifying you him of some occurrence or enabling him to take some action
 * in order to handle an error.
 *
 * @author Danilo Reinert
 */
public class ValidationMessage {

    /**
     * The message type is useful for styling user message.
     * <p/>
     * Your {@link ValidationHandler} can present the message in different ways depending on the type.
     */
    public static enum Type {
        ERROR, INFO, NONE, SUCCESS, WARNING
    }

    private final String message;
    private final Type type;

    public ValidationMessage(String message, Type type) {
        this.message = message;
        this.type = type;
    }

    /**
     * Retrieves the user message.
     *
     * @return message for the user.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Retrieves the type of the Validation. It can be useful for decorating message's container in a enlightening way.
     *
     * @return validation type
     *
     * @see ValidationMessage.Type
     */
    public Type getType() {
        return type;
    }
}
