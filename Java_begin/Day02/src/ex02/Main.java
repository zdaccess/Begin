import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    private static Path uri;
    private static String OS_SYSTEM = "/";
    private static String PROHIBITED_1 = "\\\\";
    public static void main(String[] args) throws IOException {
        if (args.length == 1) {
            String[] arguments = args[0].split("=");
            if (arguments[0].equals("--current-folder")) {
                if (arguments.length > 1) {
                    File fileUrl = new File(arguments[1]);
                    if (fileUrl.exists() && fileUrl.isAbsolute()) {
                        uri = Paths.get(arguments[1]);
                        mainProgram(fileUrl);
                    } else {
                        System.out.println("Error. This folder " +
                                "does not exist or the link is not absolute!");
                    }
                } else {
                    System.out.println("Error. You did not enter the folder URL");
                }
            } else {
                System.out.println("Error! Enter one argument! --current-folder=[URL]");
            }
        } else {
            System.out.println("Error! Enter one argument! --current-folder=[URL]");
        }
    }

    public static void reNameFilesFolders(Path url, Path urlFolder) throws IOException {
        if (url.toFile().isFile() && url.toFile().exists()) {
            if (url.toFile().canWrite() && url.toFile().canRead()) {
                if (urlFolder.toFile().exists() && urlFolder.toFile().isDirectory()) {
                    if (urlFolder.toFile().exists() && urlFolder.toFile().isFile())
                        System.out.println("Error! A file with the same name already exists!");
                    else
                        Files.move(url, urlFolder.resolve(url.getFileName()));
                } else {
                    if (urlFolder.toFile().exists() && urlFolder.toFile().isFile())
                        System.out.println("Error! A file with the same name already exists!");
                    else
                        Files.move(url, urlFolder);
                }
            } else
                System.out.println("Error! The file is read and write protected!");
        } else
            System.out.println("Error! The object is not a file or the object does not exist!");
    }

    public static Integer findSymbol(String[] splitUrl) {
        Integer flag = 0;
        Integer i = 0;
        while (i < splitUrl.length) {
            if (splitUrl[i].equals("..") || splitUrl[i].equals("."))
                flag = 1;
            i++;
        }
        return flag;
    }

    public static Path joinStringForUri(String[] str, String command, Integer findStart) {
        Path copyUri;
        if (findStart == 0) {
            copyUri = Paths.get("/", str[1]);
        } else
            copyUri = Paths.get(str[1]);
        if (copyUri.isAbsolute()) {
            for (Integer i = 2; i < str.length; i++) {
                if (copyUri == null)
                    break;
                else if (str[i].equals(".")) {
                    copyUri = copyUri;
                } else if (str[i].equals("..") && copyUri.getParent() != null) {
                    copyUri = copyUri.getParent();
                } else if (str[i].equals("..") && copyUri.getParent() == null) {
                    copyUri = Paths.get("/");
                } else if (copyUri.toFile().exists() && i == str.length - 1
                        || !copyUri.toFile().exists() && command.equals("mv2") && i == str.length - 1) {
                    return Paths.get(copyUri.toString(), str[i]);
                } else {
                    copyUri = Paths.get(copyUri.toString(), str[i]);
                }
                if (i == str.length - 1 && !copyUri.toFile().exists() && !command.equals("mv2")){
                    System.out.println("Error! This folder does not exist!");
                }
            }
        } else {
            copyUri = uri;
            for (Integer i = 0; i < str.length; i++) {
                if (copyUri == null)
                    break;
                else if (str[i].equals(".")) {
                    copyUri = copyUri;
                } else if (str[i].equals("..") && copyUri.getParent() != null) {
                    copyUri = copyUri.getParent();
                } else if (str[i].equals("..") && copyUri.getParent() == null) {
                    copyUri = Paths.get("/");
                    return copyUri;
                } else if (copyUri.toFile().exists() && i == str.length - 1
                        || !copyUri.toFile().exists() && command.equals("mv2") && i == str.length - 1) {
                    return Paths.get(copyUri.toString(), str[i]);
                } else {
                    copyUri = Paths.get(copyUri.toString(), str[i]);
                }
                if (i == str.length - 1 && !copyUri.toFile().exists() && !command.equals("mv2")){
                    System.out.println("Error! This folder does not exist!");
                }
            }
        }
        return copyUri;
    }

    public static String findQuotes(String[] arguments, Integer start, Integer end) {
        String url = "";
        Integer i = start;
        while (i < end + 1) {
            if (i == start)
                arguments[i] = arguments[i].substring(1).trim();
            if (arguments[i].indexOf("\"") == 0 && i == end) {
                arguments[i] = arguments[i].replaceAll("^\"|\"$", "");
                break;
            }
            url = url + " " + arguments[i];
            i++;
        }
        url = url.replaceAll("^\"|\"$", "").trim();
        return url;
    }

    public static Path  processReturnUrl(String[] arguments, Integer start,
                                            Integer end, String command, String argument) {
        Integer count = 0;
        Integer findStart = null;
        Path copyurl = uri;
        String data = "";
        if (start != 0 && end != 0) {
            data = findQuotes(arguments, start, end);
        } else {
            if (command.equals("mv2") || command.equals("mv")) {
                data = argument;
                if (data.equals("..") || data.equals(".")) {
                    if (data.equals("..") && uri.getParent() != null) {
                        return uri.getParent();
                    } else if (data.equals("..") && uri.getParent() == null) {
                        return uri;
                    }
                }
            }
            else {
                data = arguments[1];
            }
        }
        findStart = data.indexOf("/Users");
        Path url = Paths.get(data);
        Path current = url;
        String[] splitUrl = data.split(OS_SYSTEM);
        if (splitUrl.length > 1) {
            Path copyUri = Paths.get(uri.toString(), splitUrl[0]);
            if (current.toFile().exists() && !splitUrl[0].equals("..") && !splitUrl[0].equals(".")) {
                if (findSymbol(splitUrl) == 0) {
                    url = Paths.get(data);
                } else
                    url = joinStringForUri(splitUrl, command, findStart);
            } else if (command.equals("mv2")) {
                url = joinStringForUri(splitUrl, command, findStart);
            } else if ((findSymbol(splitUrl) == 1) || copyUri.toFile().exists()) {
                count = data.indexOf(PROHIBITED_1);
                if (count != -1)
                    System.out.println("Error! This folder does not exist!");
                else {
                    url = joinStringForUri(splitUrl, command, findStart);
                }
            }  else {
                System.out.println("Error! This folder does not exist!");
                url = uri;
            }
        }
        else {
            if (current.isAbsolute()) {
                if (current.toFile().exists()) {
                    url = current;
                }
            } else if (command.equals("mv2")) {
                return Paths.get(uri.toString(), url.toString());
            } else if (data.equals("..") || data.equals(".")
                    || data.equals("../") || data.equals("./")) {
                if (data.equals("..") && uri.getParent() != null
                        || data.equals("../") && uri.getParent() != null) {
                    url = uri.getParent();
                } else if (data.equals("..") && uri.getParent() == null
                        || data.equals("../") && uri.getParent() == null) {
                    url = uri;
                }
                else if (data.equals("."))
                    url = uri;

            } else {
                current = Paths.get(uri.toString(), data);
                if (current.toFile().exists()) {
                    url = current;
                }
            }
        }
        return url;
    }
    public static Path returnUrl(String[] arguments, Integer[] quoteLocation,
                                    String command, Integer quoteCount) {
        Path url = null;
        if (command.equals("cd") && quoteCount == 0) {
            url = processReturnUrl(arguments, 0, 0, command, "");
        } else if (command.equals("cd") && quoteCount == 2) {
            url = processReturnUrl(arguments, quoteLocation[0],
                    quoteLocation[1], "cd", "");
        } else if (command.equals("mv") && quoteCount == 0) {
            url = processReturnUrl(arguments, 0, 0, "mv", arguments[1]);
        } else if (command.equals("mv2") && quoteCount == 0) {
            url = processReturnUrl(arguments, 0, 0, "mv2", arguments[2]);
        } else if (command.equals("mv") && quoteCount == 2
                && quoteLocation[0] == 1 && quoteLocation[1] == arguments.length - 2) {
            url = processReturnUrl(arguments, quoteLocation[0],
                    quoteLocation[1], "mv", "");
        } else if (command.equals("mv2") && quoteCount == 2
                && quoteLocation[0] == 1 && quoteLocation[1] == arguments.length - 2) {
            url = processReturnUrl(arguments, 0, 0,
                    "mv2", arguments[arguments.length - 1]);
        } else if (command.equals("mv") && quoteCount == 2
                && quoteLocation[0] == 2 && quoteLocation[1] == arguments.length - 1) {
            url = processReturnUrl(arguments, 0, 0, "mv", arguments[1]);
        } else if (command.equals("mv2") && quoteCount == 2
                && quoteLocation[0] == 2 && quoteLocation[1] == arguments.length - 1) {
            url = processReturnUrl(arguments, quoteLocation[0],
                    quoteLocation[1], "mv2", "");
        } else if (command.equals("mv") && quoteCount == 4) {
            url = processReturnUrl(arguments, quoteLocation[0],
                    quoteLocation[1], "mv", "");
        } else if (command.equals("mv2") && quoteCount == 4) {
            url = processReturnUrl(arguments, quoteLocation[2],
                    quoteLocation[3], "mv2", "");
        }
        return url;
    }

    public static void findUrlInArguments(String command, String[] arguments) throws IOException {
        Integer[] quoteLocation = new Integer[5];
        Path currentUrl = null;
        Path mvFileUrl = null;
        Path mvFileOrDirectory = null;
        Integer quoteCount = 0;
        Integer j = 0;
        for (Integer i = 1; i < arguments.length; i++) {
            if (arguments[i].contains("\"") && j < 5) {
                if (arguments[i].charAt(0) == '\"'
                        && arguments[i].charAt(arguments[i].length() - 1) == '\"') {
                    arguments[i] = arguments[i].substring(0);
                    arguments[i] = arguments[i].replaceAll("^\"|\"$", "");
                } else {
                    quoteLocation[j] = i;
                    quoteCount++;
                    j++;
                }
            }
        }
        if (command.equals("cd")) {
            if (quoteCount == 0 && arguments.length == 2) {
                currentUrl = returnUrl(arguments, quoteLocation, command, quoteCount);
                checkDirectoryorFile(currentUrl, command);
            } else if (quoteCount == 2 && quoteLocation[1] == arguments.length - 1 && quoteLocation[0] == 1) {
                currentUrl = returnUrl(arguments, quoteLocation, command, quoteCount);
                checkDirectoryorFile(currentUrl, command);
            } else {
                System.out.println("Error! You entered more than two " +
                        "directories or did not close one of the quotes!");
            }
        } else {
            if (quoteCount == 0) {
                mvFileUrl = returnUrl(arguments, quoteLocation, command, quoteCount);
                mvFileOrDirectory = returnUrl(arguments, quoteLocation, "mv2", quoteCount);
                reNameFilesFolders(mvFileUrl, mvFileOrDirectory);
            } else if (quoteCount == 2 && quoteLocation[0] == 1 && quoteLocation[1] == arguments.length - 2) {
                mvFileUrl = returnUrl(arguments, quoteLocation, command, quoteCount);
                mvFileOrDirectory = returnUrl(arguments, quoteLocation, "mv2", quoteCount);
                reNameFilesFolders(mvFileUrl, mvFileOrDirectory);
            } else if (quoteCount == 2 && quoteLocation[1] == arguments.length - 1 && quoteLocation[0] == 2) {
                mvFileUrl = returnUrl(arguments, quoteLocation, command, quoteCount);
                mvFileOrDirectory = returnUrl(arguments, quoteLocation, "mv2", quoteCount);
                reNameFilesFolders(mvFileUrl, mvFileOrDirectory);
            } else if (quoteCount == 4 && quoteLocation[0] == 1 && (quoteLocation[1] == quoteLocation[2] - 1)
                    && quoteLocation[3] == arguments.length - 1) {
                mvFileUrl = returnUrl(arguments, quoteLocation, command, quoteCount);
                mvFileOrDirectory = returnUrl(arguments, quoteLocation, "mv2", quoteCount);
                reNameFilesFolders(mvFileUrl, mvFileOrDirectory);
            } else {
                System.out.println("Error! You entered more than " +
                        "two directories or did not close the quotation mark!");
            }
        }
    }

    public static void checkDirectoryorFile(Path url, String command) {
        if (url.toFile().isDirectory() && command.equals("cd"))
            uri = url;
        else
            System.out.println("Error! This folder does not exist!");
    }

    public static void mainProgram(File fileUrl) throws IOException {
        System.out.println(uri);
        Scanner scan = new Scanner(System.in);
        Path current;
        Integer quotes = 0;
        Integer count = 0;
        Integer count2 = 0;
        String[] command = scan.nextLine().split(" ");
        while (!command[0].equals("exit")) {
            quotes = 0;
            if (command[0].equals("cd") ) {
                if (command.length >= 2) {
                    findUrlInArguments("cd", command);
                    System.out.println(uri);
                } else
                    System.out.println("Error! Enter one argument to navigate through directories!");
                command = scan.nextLine().split(" ");
            } else if (command[0].equals("ls")) {
                if (command.length == 1) {
                    long size = 0;
                    File[] files = uri.toFile().listFiles();
                    if (files != null) {
                        for (File file : files) {
                            if (file.isDirectory()) {
                                size = listFiles(file.toPath().toFile());
                                System.out.println(file.getName() + " " + size / 1024 + " KB");
                            } else {
                                System.out.println(file.getName() + " " + file.length() / 1024 + " KB");
                            }
                        }
                    }
                } else
                    System.out.println("Error! Just enter the ls command!");
                command = scan.nextLine().split(" ");
            } else if (command[0].equals("mv")) {
                if (command.length >= 3) {
                    findUrlInArguments("mv", command);
                    command = scan.nextLine().split(" ");
                } else {
                    System.out.println("Error! Enter two arguments to rename or move the file!");
                    command = scan.nextLine().split(" ");
                }
            }
            else {
                System.out.println("Error! Unknown command!");
                command = scan.nextLine().split(" ");
            }
        }
    }

    public static long listFiles(File dir) {
        long size = 0;
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    size = size + listFiles(file);
                } else {
                    size = size + file.length();
                }
            }
        }
        return size;
    }
}
