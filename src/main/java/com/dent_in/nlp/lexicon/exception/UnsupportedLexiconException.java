/*
 * Licensed to Dent-in Tech Solutions under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * Dent-in Tech Solutions licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dent_in.nlp.lexicon.exception;

public class UnsupportedLexiconException extends UnsupportedOperationException {

    public UnsupportedLexiconException() {
        super();
    }

    public UnsupportedLexiconException(String message) {
        super(message);
    }

    public UnsupportedLexiconException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedLexiconException(Throwable cause) {
        super(cause);
    }

}
