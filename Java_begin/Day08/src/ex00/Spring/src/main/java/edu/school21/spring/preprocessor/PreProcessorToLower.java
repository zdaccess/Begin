package edu.school21.spring;

public class PreProcessorToLower implements PreProcessor {
    @Override
    public String print(String str) {
        return str.toLowerCase();
    }
}
