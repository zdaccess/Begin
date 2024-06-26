package edu.school21.annotations;

import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import javax.annotation.processing.Processor;
@SupportedAnnotationTypes({"edu.school21.annotations.HtmlForm",
                            "edu.school21.annotations.HtmlInput"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)

public class HtmlProcessor extends AbstractProcessor {
    private FileWriter fileWriter;
    private String action;
    private String method;
    private String fileName;
    private String type;
    private String name;
    private String pHolder;

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {
        int i = 0;
        for (Element anno : roundEnv.getElementsAnnotatedWith(
                                HtmlForm.class)) {

            HtmlForm htmlForm = anno.getAnnotation(HtmlForm.class);
            action = htmlForm.action();
            method = htmlForm.method();
            fileName = htmlForm.fileName();

            for (Element annotate : roundEnv.getElementsAnnotatedWith(
                    HtmlInput.class)) {
                HtmlInput htmlInput =  annotate.getAnnotation(HtmlInput.class);
                type = htmlInput.type();
                name = htmlInput.name();
                pHolder = htmlInput.placeholder();
                try {
                    if (i == 0) {
                        fileWriter = new FileWriter("target/classes/" +
                                                       fileName);
                        fileWriter.write(
                                "<form action = \"" + action +
                                "\" method = \"" + method + "\">\n"
                        );
                        fileWriter.flush();
                    }
                    fileWriter.write(
                            "\t<input type = \"" + type + "\" name = " +
                            "\"" + name + "\" placeholder = \"" + pHolder +
                            "\">\n"
                    );
                    fileWriter.flush();
                    if (i == 2) {
                        fileWriter.write(
                                "\t<input type = \"submit\" value = \"Send\">\n" +
                                "</form>"
                        );
                        fileWriter.flush();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                i++;
            }
        }
        return false;
    }
}
