package edu.school21.numbers;

public class NumberWorker {

    public boolean isPrime(int number) throws IllegalNumberException {
        if (number <= 1) {
            throw new IllegalNumberException();
        }
        int j = 0;
        for (int i = 1; i * i <= number; i++) {
            if (number % i == 0 && j != 0 ) {
                return false;
            }
            j++;
        }
        return true;
    }

    public int digitsSum(int number) {
        int sum = 0;
        while ((number / 10) != 0) {
            sum = sum + number % 10;
            number = number / 10;
        }
        sum = sum + number % 10;
        return sum;
    }
}
