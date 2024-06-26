package src.ex02;

import java.util.Random;

class Program {
    public static void main(String[] args) throws InterruptedException {
        if (args.length == 2) {
            String[] str = args[0].split("=");
            String[] str2 = args[1].split("=");
            if (str[0].equals("--arraySize") && str2[0].equals("--threadsCount")
                && (str.length == 2 && str2.length == 2)) {
                if ((str[1].matches("-?\\d+")
                    && str2[1].matches("-?\\d+")) &&
                    (Integer.parseInt(str[1]) <= 2000000)
                    && (Integer.parseInt(str2[1]) <= 2000000)) {
                    Integer[] array = new Integer[Integer.parseInt(str[1])];
                    Integer treadsCount = Integer.parseInt(str2[1]);
                    if (array.length >= treadsCount) {
                        Random random = new Random();
                        Object flag = new Object();
                        Integer sumThreads = 0;
                        for (Integer i = 0, sum = 0; i < array.length; i++) {
                            array[i] = random.nextInt() % 1000;
                            sum += array[i];
                            if (i == array.length - 1)
                                System.out.println("Sum: " + sum);
                        }
                        Integer segment = (int) Math.ceil((float) (array.length)
                                          / treadsCount);
                        Integer delCount = array.length / segment;
                        Integer endCountArray = array.length -
                                                (delCount * segment);
                        if (delCount <= treadsCount && segment > endCountArray
                            && endCountArray < 0
                            || segment * treadsCount > array.length
                            && treadsCount != 3) {
                            segment = (array.length) / treadsCount;
                            delCount = treadsCount - 1;
                            endCountArray = array.length - (delCount * segment);
                        }

                        ThreadsJob[] runnable = new ThreadsJob[treadsCount];
                        Integer start = 0;
                        for (Integer i = 0; i < treadsCount; i++) {
                             if (delCount == 0 && (array.length  - (start
                                + endCountArray)) < segment)  {
                                 runnable[i] = new ThreadsJob(i, array, start,
                                               (start + endCountArray - 1),
                                                flag, treadsCount);
                                 start += endCountArray - 1;
                             } else if (delCount == 0) {
                                     runnable[i] = new ThreadsJob(i, array,
                                                   start, (start + endCountArray),
                                                   flag, treadsCount);
                                     start += endCountArray;
                                 } else {
                                     runnable[i] = new ThreadsJob(i, array,
                                                   start, (start + segment - 1),
                                                   flag, treadsCount);
                                     start += segment;
                                     delCount--;
                                 }
                            Thread thread = new Thread(runnable[i]);
                            thread.start();
                            thread.join();
                        }
                        for (Integer i = 0; i < treadsCount; i++) {
                            sumThreads += runnable[i].getSum();
                        }
                        System.out.println("Sum by threads: " + sumThreads);
                    } else
                        System.out.println("Error! The number of threads is " +
                                                   "greater than the size of " +
                                                   "the array!");
                } else
                    System.out.println("Error! Please enter a number greater " +
                                               "than zero!");
            } else
                System.out.println("Error! Enter the number!");
        } else
            System.out.println("Error! Enter --arraySize=number " +
                                       "--threadsCount=number to run the " +
                                       "program!");
    }
}