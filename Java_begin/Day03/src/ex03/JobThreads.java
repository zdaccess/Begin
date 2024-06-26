package src.ex03;

public class JobThreads implements Runnable {
    private String[][]  str;
    private Integer     index;
    Object              flag;
    FileData            fileData;

    public JobThreads(Integer index, Object flag, FileData fileData) {
        this.index = index + 1;
        this.flag = flag;
        this.fileData = fileData;
    }

    @Override
    public void run() {
        synchronized (flag) {
            str = fileData.getData();
            for (Integer i = 0; i < str.length; i++) {
                if (str[i][2].equals("notuploaded")) {
                    str[i][2] = "progres";
                    fileData.setData(str);
                    System.out.println("Thread-" + index +
                            " start download file number " + str[i][0]);
                    try {
                        flag.wait(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Thread-" + index +
                            " finish download file number " + str[i][0]);
                    str[i][2] = "uploaded";
                    fileData.setData(str);
                }
            }
        }
    }
}
