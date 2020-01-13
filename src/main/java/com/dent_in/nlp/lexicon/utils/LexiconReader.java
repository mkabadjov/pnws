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

package com.dent_in.nlp.lexicon.utils;


import com.dent_in.nlp.lexicon.Lexicon;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LexiconReader {

    private static final String NONSPACE ="[\\S]+";

    /**
     * A pass through method to load a corpus lexicon assuming a UTF-8 encoding.
     *
     * @param lexiconFilePath The input file path where the word frequency is found
     * @param lexicon The lexicon where the words and frequencies (if any) will be loaded
     */
    public static void loadLexicon(String lexiconFilePath, Lexicon lexicon) {
        LexiconReader.loadLexicon(lexiconFilePath, lexicon, StandardCharsets.UTF_8);
    }

    /**
     * A factory style method which invokes the corresponding lexicon loader based on the Lexicon.TYPE passed.
     *
     * @param lexiconFilePath The input file path where the word frequency is found
     * @param lexicon The lexicon where the words and frequencies (if any) will be loaded
     * @param encoding The character encoding of the input file
     */
    public static void loadLexicon(String lexiconFilePath, Lexicon lexicon, Charset encoding) {
        Path path = FileSystems.getDefault().getPath(lexiconFilePath);

        switch (lexicon.getType()) {
            case NGRAMCORPUSLEXICON:
                LexiconReader.loadNgramCorpusLexicon(path, lexicon, encoding);
                break;
            case CORPUSLEXICON:
                LexiconReader.loadCorpusLexicon(path, lexicon, encoding);
                break;
            case DICTIONARYLEXICON:
                LexiconReader.loadDictionaryLexicon(path, lexicon, encoding);
                break;
            case THESAURUSLEXICON:
                LexiconReader.loadThesaurusLexicon(path, lexicon, encoding);
                break;
            case DEFAULT:
                LexiconReader.loadLexicon(path, lexicon, encoding);
        }
    }

    private static void loadDictionaryLexicon(Path path, Lexicon lexicon, Charset encoding) {
        //TODO: This is a dictionary with words and their definitions, either monolingual or bilingual.
    }

    private static void loadThesaurusLexicon(Path path, Lexicon lexicon, Charset encoding) {
        //TODO: This is a thesaurus, will possibly contain a synset and definition per word.
    }

    /**
     * This is the simplest type of lexicon loading, assumes only a list of words (i.e., all frequencies set to 1).
     *
     * @param path The path to the word frequency file
     * @param lexicon The lexicon where the words and frequencies (if any) will be loaded
     * @param encoding The character encoding of the input file
     */
    public static void loadLexicon(Path path, Lexicon lexicon, Charset encoding) {
        Map<String, Long> mapWordFrequencies = (Map<String, Long>)lexicon.getBackBone();
        try (BufferedReader reader = Files.newBufferedReader(path, encoding)) {
            String line = null;
            int lineCounter = 0;
            while ((line = reader.readLine()) != null) {
                String key = line;
                Long value = new Long(1);
                mapWordFrequencies.put(key, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads a word frequency list in an efficient manner and loads it into the map passed as parameter: mapWordFrequencies.
     * Assumes a two-column file separated by a space (either tab or single space), otherwise throws warning of ill-formatting.
     *
     * @param path The path to the word frequency file
     * @param lexicon The lexicon where the words and frequencies (if any) will be loaded
     * @param encoding The character encoding of the input file
     */
    public static void loadCorpusLexicon(Path path, Lexicon lexicon, Charset encoding) {
        Map<String, Long> mapWordFrequencies = (Map<String, Long>)lexicon.getBackBone();
        Pattern pattern = Pattern.compile(NONSPACE);
        //JDK7+ pattern for reading files efficiently
        try (BufferedReader reader = Files.newBufferedReader(path, encoding)) {
            String line = null;
            int lineCounter = 0;
            while ((line = reader.readLine()) != null) {
                Matcher matcher =  pattern.matcher(line);
                int safeguard = 0;
                String key = "";
                String value = "";
                while(matcher.find()) {
                    int start = matcher.start();
                    int end = matcher.end();
                    String token = line.substring(start, end);
                    switch(safeguard) {
                        case 0:
                            key = token;
                            break;
                        case 1:
                            value = token;
                            break;
                        default:
                            //there's an unexpected token
                            System.out.println("WARN: There is an unexpected token, more than one space found at line: " + lineCounter);
                    }
                    safeguard++;
                }
                try {
                    mapWordFrequencies.put(key, Long.parseLong(value));
                } catch (NumberFormatException nfe) {
                    System.out.println("WARN: There is a wrong formatted frequency: " + value + " at line: " + lineCounter);
                }
                lineCounter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * A method to load bigram frequencies whereby single space separates the two elements
     * in the bigram and tab separates the bigram from the frequency count. This follows
     * the convention used by Peter Norvig in his word segmentation algorithm that
     * uses recourses extracted from the Google ngram corpus.
     *
     * @param path The path to the input file containing a lexicon
     * @param lexicon The lexicon into which the input file will be loaded
     * @param encoding The encoding to use when reading the input file
     */
    public static void loadNgramCorpusLexicon(Path path, Lexicon lexicon, Charset encoding) {
        Map<String, Long> mapWordFrequencies = (Map<String, Long>)lexicon.getBackBone();
        String NONSPACE ="[\\S]+";
        Pattern pattern = Pattern.compile(NONSPACE);
        //JDK7+ pattern for reading files efficiently
        try (BufferedReader reader = Files.newBufferedReader(path, encoding)) {
            String line = null;
            int lineCounter = 0;
            while ((line = reader.readLine()) != null) {
                Matcher matcher =  pattern.matcher(line);
                int safeguard = 0;
                String key = "";
                String value = "";
                while(matcher.find()) {
                    int start = matcher.start();
                    int end = matcher.end();
                    String token = line.substring(start, end);
                    switch(safeguard) {
                        case 0:
                            key += token + " ";
                            break;
                        case 1:
                            value = token;
                            break;
                        default:
                            //there's an unexpected token
                            System.out.println("WARN: There is an unexpected token, more than one space found at line: " + lineCounter);
                    }
                    //look ahead
                    if(end < line.length() && line.charAt(end) == '\t')
                        safeguard++;
                }
                try {
                    mapWordFrequencies.put(key.trim(), Long.parseLong(value));
                } catch (NumberFormatException nfe) {
                    System.out.println("WARN: There is a wrong formatted frequency: " + value + " at line: " + lineCounter);
                }
                lineCounter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
