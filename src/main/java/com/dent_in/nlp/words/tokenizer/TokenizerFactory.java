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

package com.dent_in.nlp.words.tokenizer;

import com.dent_in.nlp.words.tokenizer.impl.DefaultTokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TokenizerFactory {

    private final static Logger logger = LoggerFactory.getLogger(TokenizerFactory.class);

    public Tokenizer createInstance(Tokenizer.TYPE type) {

        logger.info("Creating Tokenizer");

        switch (type) {
            case ENGLISH:
                //XXX: Default tokenizer for now
                return new DefaultTokenizer();
            case FRENCH:
                //XXX: Default tokenizer for now
                return new DefaultTokenizer();
            case DUTCH:
                //XXX: Default tokenizer for now
                return new DefaultTokenizer();
            case SPANISH:
                //XXX: Default tokenizer for now
                return new DefaultTokenizer();
            case CHINESE:
                //XXX: Default tokenizer for now
                return new DefaultTokenizer();
            default:
                return new DefaultTokenizer();
        }
    }
}
