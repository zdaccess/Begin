package edu.school21.spring;

public class PreProcessorToUpperImpl implements PreProcessor {
    @Override
    public String print(String str) {
        return str.toUpperCase();
    }
}
