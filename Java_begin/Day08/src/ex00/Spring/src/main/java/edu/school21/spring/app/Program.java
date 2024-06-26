package edu.school21.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.NestedRuntimeException;

public class Program {
    public static void main(String[] args) throws BeansException {
        PreProcessor preProcessor = new PreProcessorToUpperImpl();
        Renderer renderer = new RendererErrImpl(preProcessor);
        PrinterWithPrefixImpl printer = new PrinterWithPrefixImpl(renderer);
        printer.setPrefix("Prefix");
        printer.print("Hello!");

        ApplicationContext context = new ClassPathXmlApplicationContext(
                "context.xml");
        

        Printer printerWithPrefix = context.getBean("printerWithPrefix",
                                             Printer.class);
        printerWithPrefix.print("Hello!");
        Printer printerWithPrefixLower = context.getBean("printerWithPrefixLower",
                                                    Printer.class);
        printerWithPrefixLower.print("Hello!");
        Printer printerWithPrefixStandard = context.getBean("printerWithPrefixStandard",
                                                         Printer.class);
        printerWithPrefixStandard.print("Hello!");
        Printer printerWithPrefixLowerStandard = context.getBean("printerWithPrefixLowerStandard",
                                                            Printer.class);
        printerWithPrefixLowerStandard.print("Hello!");

        System.out.println("");

        Printer printerWithTime = context.getBean("printerWithTime",
                                                                 Printer.class);
        printerWithTime.print("Hello!");
        Printer printerWithTimeLower = context.getBean("printerWithTimeLower",
                                                  Printer.class);
        printerWithTimeLower.print("Hello!");
        Printer printerWithTimeStandard = context.getBean("printerWithTimeStandard",
                                                  Printer.class);
        printerWithTimeStandard.print("Hello!");
        Printer printerWithTimeLowerStandard = context.getBean("printerWithTimeLowerStandard",
                                                  Printer.class);
        printerWithTimeLowerStandard.print("Hello!");
    }
}

//public class Program {
//    public static void main(String[] args) {
////        PreProcessor preProcessor = new PreProcessorToUpperImpl();
////        Renderer renderer = new RendererErrImpl(preProcessor);
////        PrinterWithPrefixImpl printer = new PrinterWithPrefixImpl(renderer);
////        printer.setPrefix("Prefix");
////        printer.print("Hello!");
//
//        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
//        Printer printer = context.getBean("printerWithPrefix", Printer.class);
//        printer.print("Hello!");
//    }
//}
