package edu.school21.spring;

public class RendererErrImpl implements Renderer {
    private final PreProcessor preprocessor;

    public RendererErrImpl(PreProcessor preprocessor) {
        this.preprocessor = preprocessor;
    }

    @Override
    public void print (String str) {
        System.err.println(preprocessor.print(str));
    }
}
