package edu.school21.numbers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

public class NumberWorkerTest {

    @ParameterizedTest()
    @ValueSource(ints = {211, 401, 167})
    void isPrimeForPrimes(int number) throws IllegalNumberException {
        NumberWorker numberWorker = new NumberWorker();
        Assertions.assertTrue(numberWorker.isPrime(number));
    }

    @ParameterizedTest()
    @ValueSource(ints = {55, 100, 9})
    void isPrimeForNotPrimes(int number) throws IllegalNumberException {
        NumberWorker numberWorker = new NumberWorker();
        Assertions.assertFalse(numberWorker.isPrime(number));
    }

    @ParameterizedTest()
    @ValueSource(ints = {-100,0,1})
    void isPrimeForIncorrectNumbers(int number) throws IllegalNumberException {
        NumberWorker numberWorker = new NumberWorker();
        Assertions.assertThrows(IllegalNumberException.class, () -> {
            numberWorker.isPrime(number);
        });
    }

    @ParameterizedTest()
    @CsvFileSource(resources = "/data.csv")
    void isDigitsSum(int expected, int actual) {
        NumberWorker numberWorker = new NumberWorker();
        Assertions.assertEquals(actual, numberWorker.digitsSum(expected));
    }
}
