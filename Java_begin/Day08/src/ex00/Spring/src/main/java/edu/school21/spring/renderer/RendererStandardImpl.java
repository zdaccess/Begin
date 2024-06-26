package edu.school21.spring;

public class RendererStandardImpl implements Renderer {
    private PreProcessor preprocessor;

    public RendererStandardImpl(PreProcessor preprocessor) {
        this.preprocessor = preprocessor;
    }

    @Override
    public void print (String str) {
        System.out.println(preprocessor.print(str));
    }
}
