package ru.sc.vsu.berezin_y_a;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class LogicTest {

    Logic logic = new Logic();
    List<Integer> correctResult;
    List<Integer> actualResult;

    @Test
    public void test1() {
        int[] arr1 = Util.readIntArrayFromFile("testFiles/input01.txt");
        correctResult = Arrays.asList(1, 2, 3, 4, 5);
        assert arr1 != null;
        actualResult = (logic.createNewList(logic.intArrayToList(arr1)));
        Assert.assertEquals(correctResult, actualResult);
    }

    @Test
    public void test2() {
        int[] arr2 = Util.readIntArrayFromFile("testFiles/input02.txt");
        correctResult = Arrays.asList(5, 4, 6, 4, 5);
        assert arr2 != null;
        actualResult = (logic.createNewList(logic.intArrayToList(arr2)));
        Assert.assertEquals(correctResult, actualResult);
    }

    @Test
    public void test3() {
        int[] arr3 = Util.readIntArrayFromFile("testFiles/input03.txt");
        correctResult = Arrays.asList(0);
        assert arr3 != null;
        actualResult = (logic.createNewList(logic.intArrayToList(arr3)));
        Assert.assertEquals(correctResult, actualResult);
    }

    @Test
    public void test4() {
        int[] arr4 = Util.readIntArrayFromFile("testFiles/input04.txt");
        correctResult = Arrays.asList(1, 2, 1);
        assert arr4 != null;
        actualResult = (logic.createNewList(logic.intArrayToList(arr4)));
        Assert.assertEquals(correctResult, actualResult);
    }

    @Test
    public void test5() {
        int[] arr5 = Util.readIntArrayFromFile("testFiles/input05.txt");
        correctResult = Arrays.asList(0, 1, 0, 1, 0);
        assert arr5 != null;
        actualResult = (logic.createNewList(logic.intArrayToList(arr5)));
        Assert.assertEquals(correctResult, actualResult);
    }

}
