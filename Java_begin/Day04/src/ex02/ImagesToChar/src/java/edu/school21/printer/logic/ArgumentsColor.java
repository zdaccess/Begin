package edu.school21.printer;

import com.beust.jcommander.*;

@Parameters(separators = "=")
public class ArgumentsColor {
    @Parameter(names = "--white", required = true)
    private String white;

    @Parameter(names = "--black", required = true)
    private String black;

    public String getWhite() {
        return white;
    }

    public String getBlack() {
        return black;
    }
}