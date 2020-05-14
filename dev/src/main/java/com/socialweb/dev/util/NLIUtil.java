package com.socialweb.dev.util;

import java.util.HashSet;
import java.util.Set;

public class NLIUtil {
    private static String[] stopList =
            {"a", "about", "above", "after", "again", "against", "ain", "all", "am", "an", "and", "any",
            "are", "aren", "aren't", "as", "at", "be", "because", "been", "before", "being", "below", "between", "both",
            "but", "by", "can", "couldn", "couldn't", "d", "did", "didn", "didn't", "do", "does", "doesn", "doesn't",
            "doing", "don", "don't", "down", "during", "each", "few", "for", "from", "further", "had", "hadn", "hadn't",
            "has", "hasn", "hasn't", "have", "haven", "haven't", "having", "he", "her", "here", "hers", "herself", "him",
            "himself", "his", "how", "i", "if", "in", "into", "is", "isn", "isn't", "it", "it's", "its", "itself", "just",
            "ll", "m", "ma", "me", "mightn", "mightn't", "more", "most", "mustn", "mustn't", "my", "myself", "needn",
            "needn't", "no", "nor", "not", "now", "o", "of", "off", "on", "once", "only", "or", "other", "our", "ours",
            "ourselves", "out", "over", "own", "re", "s", "same", "shan", "shan't", "she", "she's", "should", "should've",
            "shouldn", "shouldn't", "so", "some", "such", "t", "than", "that", "that'll", "the", "their", "theirs",
            "them", "themselves", "then", "there", "these", "they", "this", "those", "through", "to", "too", "under",
            "until", "up", "ve", "very", "was", "wasn", "wasn't", "we", "were", "weren", "weren't", "what", "when",
            "where", "which", "while", "who", "whom", "why", "will", "with", "won", "won't", "wouldn", "wouldn't", "y",
            "you", "you'd", "you'll", "you're", "you've", "your", "yours", "yourself", "yourselves", "could", "he'd",
            "he'll", "he's", "here's", "how's", "i'd", "i'll", "i'm", "i've", "let's", "ought", "she'd", "she'll",
            "that's", "there's", "they'd", "they'll", "they're", "they've", "we'd", "we'll", "we're", "we've", "what's",
            "when's", "where's", "who's", "why's", "would"};

    public static Set<String> getTokens(String str){
        Set<String> stopWords = new HashSet<>();
        for(String s : stopList){
            stopWords.add(s);
        }
        Set<String> tokens = new HashSet<>();
        String s = str.toLowerCase();
        s = s.replaceAll("[\\pP+\\pS+\\pN]", " ");
        String[] trimed = s.trim().split(" ");
        for(String w : trimed){
            if(w.length() == 0 || stopWords.contains(w)){
                continue;
            }
            tokens.add(w);
        }
        return tokens;
    }

    public static String getQuery(String field, String condition, String str){
        //field: title, content, ...
        StringBuilder temp = new StringBuilder();
        temp.append("select * from posts where ");
        //condition: AND/OR
        condition = " " + condition + " ";
        Set<String> tokens = getTokens(str);
        for(String t : tokens){
            temp.append(field + " like '%" + t + "%'");
            temp.append(condition);
        }
        String res = temp.substring(0, temp.length() - condition.length()) + " order by create_time DESC LIMIT 10";
        return res;
    }

}
