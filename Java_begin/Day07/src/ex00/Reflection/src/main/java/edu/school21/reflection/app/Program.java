package edu.school21.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import java.util.Set;

public class Program {
    static String line = "---------------------";
    static Scanner scanner;
    static String[] array;
    static Class<?> clas;
    static String str;
    static String javaLang = "class java.lang.";

    public static void main(String[] args) throws IllegalAccessException,
                                            NoSuchFieldException,
                                            InvocationTargetException,
                                            NoSuchMethodException,
                                            InstantiationException {
        String pack = "edu.school21.reflection";
        clas = findClass(pack);
        if (clas != null) {
            printFieldsMetods();
        }
    }


    public static Class<?> findClass(String pack) {
        String str;
        Reflections reflections = new Reflections("edu.school21.reflection",
                                                  new SubTypesScanner(false));
        Set<Class<?>> classes = reflections.getSubTypesOf(Object.class);
        System.out.println("Classes:");
        for (Class<?> clas : classes) {
            str = clas.getName().substring(pack.length() + 1);
            if (!str.equals("Program")) System.out.println("  - " + str);
        }
        System.out.println(line);
        System.out.println("Enter class name:");
        scanner = new Scanner(System.in);
        array = scanner.nextLine().split(" ");
        for (Class<?> element : classes) {
            str = element.getName().substring(pack.length() + 1);
            if (str.equals(array[0])) {
                return element;
            }
        }
        return null;
    }

    public static void printFieldsMetods() throws IllegalAccessException,
                                            NoSuchFieldException,
                                            NoSuchMethodException,
                                            InvocationTargetException,
                                            InstantiationException {
        Field[] fields = clas.getDeclaredFields();
        Method[] methods = clas.getDeclaredMethods();
        System.out.println(line);
        System.out.println("fields:");
        for (Field field : fields) {
            str = field.getType().toString().substring(javaLang.length());
            System.out.println("\t" + str + " " + field.getName());
        }
        System.out.println("methods:");
        for (Method method : methods) {
            str = method.getReturnType().toString().substring(javaLang.length());
            if (!method.getName().equals("toString"))
                System.out.println("\t" + str + " " + method.getName());
        }
        System.out.println(line);
        System.out.println("Let’s create an object.");
        Field setFunctionField;
        Object object = clas.getDeclaredConstructor().newInstance();
        for (Field field : fields) {
            str = field.getType().toString().substring(javaLang.length());
            System.out.println(field.getName() + ":");
            array = scanner.nextLine().split(" ");
            if (str.equals("String")) {
                setFunctionField = clas.getDeclaredField(field.getName());
                setFunctionField.setAccessible(true);
                setFunctionField.set(object, array[0]);
            } else if ((str.equals("int")
            && array[0].matches("[0-9]+")) || (str.equals("Integer")
            && array[0].matches("[0-9]+"))) {
                setFunctionField = clas.getDeclaredField(field.getName());
                setFunctionField.setAccessible(true);
                setFunctionField.set(object, Integer.valueOf(array[0]));
            }
        }
        System.out.println("Object created: " + object.toString());
        System.out.println(line);
        System.out.println("Enter name of the field for changing:");
        array = scanner.nextLine().split(" ");
        for (Field field : fields) {
            if (array[0].equals(field.getName())) {
                str = field.getType().toString().substring(javaLang.length());
                System.out.println("Enter " + str + " value:");
                array = scanner.nextLine().split(" ");
                if (str.equals("String") || (str.equals(
                        "int") && array[0].matches("[0-9]+")) || (str.equals(
                        "Integer") && array[0].matches("[0-9]+"))) {
                    setFunctionField = clas.getDeclaredField(field.getName());
                    setFunctionField.setAccessible(true);
                    setFunctionField.set(object, array[0]);
                }
            }
        }
        System.out.println("Object created: " + object.toString());
        System.out.println(line);
        System.out.println("Enter name of the method for call:");
        array = scanner.nextLine().split(" ");
        for (Method method : methods) {
            str = method.getReturnType().toString().substring(javaLang.length());
            if (!method.getName().equals("toString")
            && method.getName().equals(array[0])) {
                method.setAccessible(true);
                Object result = method.invoke(object);
                System.out.println("Method returned:");
                System.out.println(result);
            }
        }
    }
}
