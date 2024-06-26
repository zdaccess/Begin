package edu.school21.spring;

public class PrinterWithPrefixImpl implements Printer {
    private final Renderer renderer;
    private String prefix;

    public PrinterWithPrefixImpl(Renderer renderer) {
        this.renderer = renderer;
    }

    public void setPrefix(String str) {
        prefix = str;
    }

    @Override
    public void print(String str) {
        renderer.print(prefix + " " + str);
    }
}
