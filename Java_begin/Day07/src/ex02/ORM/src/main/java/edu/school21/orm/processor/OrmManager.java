package edu.school21.orm;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import javax.sql.DataSource;

public class OrmManager {
    private DataSource dataSource;
    private Class<?> clas;
    private Field[] fields;
    private Map<Object, Integer> objectMap;
    private Integer id;

    public OrmManager() {
        objectMap = new HashMap<>();
    }

    public void connectDB() {
        HikariConfig connectData = new HikariConfig();
        connectData.setJdbcUrl("jdbc:postgresql://localhost:5432/day07");
        connectData.setUsername("postgres");
        connectData.setPassword("123456");
        dataSource = new HikariDataSource(connectData);
    }

    public String tableOrmEntity(Object entity) {
        int symbol;
        String[] findTable;
        Annotation[] annotations = clas.getAnnotations();
        for (Annotation copy : annotations) {
            if (copy.annotationType().getSimpleName().equals("OrmEntity")) {
                symbol = copy.toString().indexOf('(') + 1;
                findTable = copy.toString().
                            substring(symbol, copy.toString().length() - 1).
                            split("=");
                if (findTable[0].equals("table")) {
                    return (findTable[1].substring(1, findTable[1].length() - 1));
                } else {
                    System.err.println("Ошибка! Не найден table в OrmEntity");
                    System.exit(1);
                }
            }
        }
        return null;
    }

    public void initialization(Object entity) {
        connectDB();
        int symbol;
        String table = tableOrmEntity(entity);
        String command = "DROP TABLE IF EXISTS " + table + ";\n" +
                        "CREATE TABLE if NOT EXISTS " + table;
        try (Connection connection = dataSource.getConnection()) {
            fields = clas.getDeclaredFields();
            String[] list;
            String[] colTable;
            String str = "java.lang.";
            String columnName = null;
            String columnSize = null;
            String columnType = null;
            for (Field field : fields) {
                for (Annotation annotation : field.getAnnotations()) {
                    symbol = annotation.toString().indexOf('(') + 1;
                    list = annotation.toString().
                            substring(symbol, annotation.toString().length() - 1).
                            split(", ");
                    columnType = field.getAnnotatedType().
                                toString().substring(str.length());
                    for (int next = 0; next < list.length; next++) {
                        colTable = list[next].split("=");
                        if (colTable[0].equals("id")
                        && annotation.annotationType().
                                getSimpleName().equals("OrmColumnId")) {
                            command = command + "(\n\tid serial PRIMARY KEY,\n";
                        } else if (colTable[0].equals("length")) {
                            columnSize = colTable[1];
                        } else if (colTable[0].equals("name")) {
                            columnName = colTable[1].
                                        substring(1,colTable[1].length() - 1);
                        }
                    }
                    if (columnSize != null && columnName != null) {
                        if (columnType.equals("String") && Integer.valueOf(
                                columnSize) <= 65535 && Integer.valueOf(
                                columnSize) > 0) {
                            command = command + "\t" + columnName +
                                    " VARCHAR" + "(" + columnSize +
                                    ") NOT NULL,\n";
                        } else if (columnType.equals("String")
                          && Integer.valueOf(columnSize) > 65535) {
                            command = command + "\t" + columnName +
                                    " TEXT" + " NOT NULL,\n";
                        } else if (columnType.equals("Long")) {
                            command = command + "\t" + columnName + " BIGINT" +
                                    " NOT NULL,\n";
                        } else if (columnType.equals("Integer")) {
                            command = command + "\t" + columnName + " INTEGER" +
                                    " NOT NULL,\n";
                        } else if (columnType.equals("Boolean")) {
                            command = command + "\t" + columnName + " BOOLEAN" +
                                    " NOT NULL,\n";
                        } else if (columnType.equals("Double")) {
                            command = command + "\t" + columnName + " PRECISION"
                                    + " NOT NULL,\n";
                        } else {
                            System.err.println("Ошибка! Введен недопустимый " +
                                            "тип данных");
                            System.exit(1);
                        }
                    }
                }
            }
            command = command.substring(0, command.length() - 2) + "\n);";
            System.out.println(command);
            Statement statement = connection.createStatement();
            statement.executeUpdate(command);
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean searchInMap(Object entity) {
        int end = entity.toString().lastIndexOf("@");
        int start = entity.toString().substring(0, end).lastIndexOf(".") + 1;
        System.out.println(entity + " " + start + " " + end);
        String find = entity.toString().substring(start, end);
        System.out.println("find " + find);
        for (Map.Entry<Object, Integer> object: objectMap.entrySet()) {
            Integer objectId = object.getValue();
            end = object.toString().lastIndexOf("@");
            start = object.toString().substring(0, end).lastIndexOf(".") + 1;
            String str = object.toString().substring(start, end);
            if (str.equals(find)) {
                return true;
            }
        }
        return false;
    }

    public void save(Object entity) throws IllegalAccessException {
        clas = entity.getClass();
        if (!searchInMap(entity)) {
            initialization(entity);
        }
        if (!objectMap.containsKey(entity)) {
            Reflections reflections = new Reflections("edu.school21.orm",
                                                      new SubTypesScanner(false));
            String columnType;
            String table = tableOrmEntity(entity);
            System.out.println(table + " " + entity);
            String command = "INSERT INTO " + table + "(";
            Set<Class<?>> classes = reflections.getSubTypesOf(Object.class);
            int start;
            int end;
            for (int i = 0; i < 2; i++) {
                for (Class<?> find : classes) {
                    if (entity.getClass().toString().substring(6).
                                                equals(find.getName())) {
                        Field[] fields = find.getDeclaredFields();
                        for (Field field : fields) {
                            field.setAccessible(true);
                            Object value = field.get(entity);
                            Annotation[] annotations = field.
                                                        getDeclaredAnnotations();
                            columnType = field.getAnnotatedType().toString().substring(
                                    "java.lang.".length());
                            for (Annotation annotation : annotations) {
                                if (i == 0) {
                                    if (!annotation.toString().
                                            substring(annotation.toString().
                                            lastIndexOf(".") + 1,
                                            annotation.toString().
                                            indexOf("(")).equals("OrmColumnId")) {
                                        start = annotation.toString().indexOf(
                                                "name" + "=\"") + 6;
                                        end = annotation.toString().substring(
                                                start).indexOf("\"");
                                        command = command + annotation.toString().
                                                substring(start, start + end) +
                                                ", ";
                                    }
                                } else {
                                    if (!annotation.toString().
                                            substring(annotation.toString().
                                            lastIndexOf(".") + 1,
                                            annotation.toString().
                                            indexOf("(")).equals("OrmColumnId")) {
                                        if (columnType.equals("String")) {
                                            command = command + "'" + value +
                                                    "', ";
                                        } else {
                                            command = command + value + ", ";
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (i == 0) {
                    command = command.substring(0, command.length() - 2) +
                            ") VALUES (";
                }
            }
            command = command.substring(0, command.length() - 2) + ");";
            System.out.println(command);
            try (Connection connection = dataSource.getConnection()) {
                Statement statement = connection.createStatement();
                statement.executeUpdate(command);
                command = "SELECT id FROM " + table + "\n" +
                        "ORDER BY id DESC\n" + "LIMIT 1;";
                ResultSet resultSet = statement.executeQuery(command);
                while (resultSet.next()) {
                    id = resultSet.getInt(1);
                }
                objectMap.put(entity, id);
                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void update(Object entity) throws IllegalAccessException {
        if (objectMap.containsKey(entity)) {
            id = objectMap.get(entity);
            Reflections reflections = new Reflections("edu.school21.orm",
                                                      new SubTypesScanner(false));
            String columnType;
            String table = tableOrmEntity(entity);
            String command = "UPDATE " + table + " SET ";
            Set<Class<?>> classes = reflections.getSubTypesOf(Object.class);
            int start;
            int end;
            for (Class<?> find : classes) {
                if (entity.getClass().toString().substring(6).
                equals(find.getName())) {
                    Field[] fields = find.getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        Object value = field.get(entity);
                        Annotation[] annotations = field.getDeclaredAnnotations();
                        columnType = field.getAnnotatedType().toString().substring(
                                "java.lang.".length());
                        for (Annotation annotation : annotations) {
                            if (!annotation.toString().substring(
                                    annotation.toString().lastIndexOf(".") + 1,
                                    annotation.toString().indexOf("(")).equals(
                                    "OrmColumnId")) {
                                start = annotation.toString().indexOf(
                                        "name" + "=\"") + 6;
                                end = annotation.toString().substring(
                                        start).indexOf("\"");
                                command = command + annotation.toString().substring(
                                        start, start + end) + " = ";
                                if (columnType.equals("String")) {
                                    command = command + "'" + value + "', ";
                                } else {
                                    command = command + value + ", ";
                                }
                            }
                        }
                    }
                }
            }
            command = command.substring(0, command.length() - 2) +
                    " WHERE id = " + id + ";";
            System.out.println(command);
            try (Connection connection = dataSource.getConnection()) {
                Statement statement = connection.createStatement();
                statement.executeUpdate(command);
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public <T> T findById(Long id, Class<T> aClass) {
        int symbol = aClass.toString().lastIndexOf(".") + 1;
        String find = aClass.toString().substring(symbol);
        for (Map.Entry<Object, Integer> object: objectMap.entrySet()) {
            Integer objectId = object.getValue();
            int end = object.toString().lastIndexOf("@");
            int start = object.toString().substring(0, end).lastIndexOf(".") + 1;
            String str = object.toString().substring(start, end);
            if (str.equals(find)) {
                if (Integer.valueOf(Math.toIntExact(id)).equals(objectId)) {
                    return (T) object.getKey();
                }
            }
        }
        return null;
    }
}

