package edu.school21.spring;

import java.time.LocalDateTime;
import static java.time.LocalDateTime.now;

public class PrinterWithDateTimeImpl implements Printer {
    private final Renderer renderer;
    private LocalDateTime time;

    public PrinterWithDateTimeImpl(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void print(String str) {
        time = now();
        renderer.print(time + " " + str);
    }
}
