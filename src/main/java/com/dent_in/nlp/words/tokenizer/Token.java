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

public class Token {
    String token = "";
    boolean isWord = true;
    int startOffset = -1;
    int endOffset = -1;

    public Token(String token){
        this.token = token;
        this.isWord = true;
    }

    public Token(String token, int startOffset, int endOffset){
        this(token);
        this.startOffset = startOffset;
        this.endOffset = endOffset;
    }

    public Token(String token, int startOffset, int endOffset, boolean isWord){
        this(token, startOffset, endOffset);
        this.isWord = isWord;
    }

    //Setters

    public void setToken(String token){
        this.token = token;
    }

    public void setStartOffset(int startOffset){
        this.startOffset = startOffset;
    }

    public void setEndOffset(int endOffset){
        this.endOffset = endOffset;
    }

    public void setIsWord(boolean isWord){
        this.isWord = isWord;
    }

    //Getters

    public String getToken(){
        return this.token;
    }

    public int getStartOffset(){
        return this.startOffset;
    }

    public int getEndOffset(){
        return this.endOffset;
    }

    public boolean isWord(){
        return this.isWord;
    }
}
