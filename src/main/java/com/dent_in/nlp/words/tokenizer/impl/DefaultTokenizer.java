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

package com.dent_in.nlp.words.tokenizer.impl;

import com.dent_in.nlp.words.tokenizer.Token;
import com.dent_in.nlp.words.tokenizer.Tokenizer;
import com.dent_in.nlp.words.tokenizer.exception.UninitializedTokenizerException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class DefaultTokenizer implements Tokenizer {

    /**
     * Keep everything that isn't a white space (e.g., by trimming tokens)
     * but split on anything that is not the usual letters and numbers
     * \w is a word, i.e., [a-zA-Z_0-9], whereas \W is a non-word [^\w].
     * Note, whitespaces are completely wiped off with the trim() method (this includes newlines)
     *
     * Initial version of the regex was "[\\s/\\-_!?.,;:$€'\"]+";
     */
    private static final String TOKENIZATION_REGEX="[\\W]+";
    private static final String NUMBER_REGEX="[\\d]+";

    private Pattern pattern = null;
    private Pattern numberPattern = null;
    private File inputFile = null;

    /**
     * Default constructor, just initializes the non-word and number patterns.
     */
    public DefaultTokenizer(){
        //compile matcher with the tokenization regex, only once at creation time
        this.pattern = Pattern.compile(TOKENIZATION_REGEX);
        this.numberPattern = Pattern.compile(NUMBER_REGEX);
    }

    /**
     * Creates a default tokenizer for give input file.
     *
     * @param inputFileName The name of the input file to be tokenized
     */
    public DefaultTokenizer(String inputFileName){
        //compile matcher with the tokenization regex, only once at creation time
        this.pattern = Pattern.compile(TOKENIZATION_REGEX);
        this.numberPattern = Pattern.compile(NUMBER_REGEX);

        inputFile = new File(inputFileName);
    }

    /**
     * A recursive tokenization method (recursion via a pass-through method below).
     * Allows only two-level recursion:
     * - first level tokenizes on non-words
     * - second level tokenizes on numbers
     * @param inputString
     * @param pattern
     * @return
     */
    private List<Token> recursiveTokenize(String inputString, Pattern pattern) {
        List<Token> result = new ArrayList<Token>();

        Matcher matcher =  pattern.matcher(inputString);
        int start = 0;
        while(matcher.find()) {
            int newStart = matcher.start();
            int end = matcher.end();
            String strToken = inputString.substring(start, newStart);
            String splitter = inputString.substring(newStart, end);
            String splitToken = splitter.trim(); //non-space splitters are also tokens
            if (strToken.length() > 0) {
                // Split on numbers, store new tokens in result
                splitOnNumbers(strToken, start, newStart, pattern, result);
            }
            if (splitToken.length() > 0) result.add(new Token(splitToken, newStart, end, false));
            start = end;
        }

        //Last token if any (i.e., that's after the last splitter)
        String strToken = inputString.substring(start);
        if (strToken.length() > 0) {
            // Split on numbers, store new tokens in result
            splitOnNumbers(strToken, start, start+strToken.length(), pattern, result);
        }

        return result;

    }

    /**
     * Just a handy pass-through method called in recursiveTokenize() and making the recursive call.
     * @param strToken
     * @param start
     * @param newStart
     * @param pattern
     * @param result
     */
    private void splitOnNumbers(String strToken, int start, int newStart, Pattern pattern, List<Token> result) {
        List<Token> tokensWithNumbers = new ArrayList<Token>();
        if (!pattern.pattern().equals(NUMBER_REGEX)) {
            tokensWithNumbers = recursiveTokenize(strToken, numberPattern);
            if (tokensWithNumbers.size() > 0) {
                for (Token token : tokensWithNumbers) {
                    result.add(new Token(token.getToken(), start+token.getStartOffset(), start+token.getEndOffset()));
                }
            } else {
                result.add(new Token(strToken, start, newStart));
            }
        } else {
            result.add(new Token(strToken, start, newStart));
        }
    }

    /**
     * Returns all tokens of this Tokenizer as a List for convenience.
     * @return Returns the list of tokens
     */
    public List<Token> tokenize() {
        if (this.inputFile != null)
            return this.tokenize(this.inputFile);
        else
            throw new UninitializedTokenizerException();
    }

    /**
     * Tokenizes a given string, practical for tokenizing trademarks.
     *
     * @param inputString The input string
     * @return Returns the list of tokens
     */
    public List<Token> tokenize(String inputString) {
        //first split on non-words, then recursively split on number therein
        return recursiveTokenize(inputString, pattern);
    }

    /**
     * For large files, this is not a practical method.
     * @param inputFile The input file name
     * @return Returns the list of tokens
     */
    public List<Token> tokenize(File inputFile) {
        //TODO: use utf-8 throughout

        List<Token> result = new ArrayList<Token>();
        BufferedReader br = null;
        FileReader fr = null;

        try {
            fr = new FileReader(inputFile);
            br = new BufferedReader(fr);
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                //FIXME: Adding millions of trademarks will crash this.
                result.addAll(this.tokenize(sCurrentLine));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
                if (fr != null)
                    fr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return result;
    }
}
