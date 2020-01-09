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

package com.dent_in.nlp.lexicon;

public abstract class AbstractLexicon implements Lexicon {

    private Lexicon.TYPE type = TYPE.DEFAULT;

    /**
     * Returns the type of this lexicon.
     *
     * @return
     */
    public Lexicon.TYPE getType() {
        return this.type;
    }

    /**
     * Sets the type of this lexicon.
     *
     * @param type
     */
    public void setType(Lexicon.TYPE type) {
        this.type =type;
    }


}
