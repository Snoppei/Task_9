import org.junit.Assert;
import org.junit.Test;
import ru.sc.vsu.berezin_y_a.Logic;
import ru.sc.vsu.berezin_y_a.Util;

import java.util.Arrays;
import java.util.List;

public class LogicTest {

    Logic logic = new Logic();
    List<Integer> correctResult;
    List<Integer> actualResult;

    @Test
    public void test01() {
        int[] arr1 = Util.readIntArrayFromFile("testSrc/testFiles/input01.txt");
        correctResult = Arrays.asList(1, 2, 3, 4, 5);
        assert arr1 != null;
        actualResult = (logic.createNewList(logic.intArrayToList(arr1)));
        Assert.assertEquals(correctResult, actualResult);
    }

    @Test
    public void test02() {
        int[] arr2 = Util.readIntArrayFromFile("testSrc/testFiles/input02.txt");
        correctResult = Arrays.asList(5, 4, 6, 4, 5);
        assert arr2 != null;
        actualResult = (logic.createNewList(logic.intArrayToList(arr2)));
        Assert.assertEquals(correctResult, actualResult);
    }

    @Test
    public void test03() {
        int[] arr3 = Util.readIntArrayFromFile("testSrc/testFiles/input03.txt");
        correctResult = Arrays.asList(0);
        assert arr3 != null;
        actualResult = (logic.createNewList(logic.intArrayToList(arr3)));
        Assert.assertEquals(correctResult, actualResult);
    }

    @Test
    public void test04() {
        int[] arr4 = Util.readIntArrayFromFile("testSrc/testFiles/input04.txt");
        correctResult = Arrays.asList(1, 2, 1);
        assert arr4 != null;
        actualResult = (logic.createNewList(logic.intArrayToList(arr4)));
        Assert.assertEquals(correctResult, actualResult);
    }

    @Test
    public void test05() {
        int[] arr5 = Util.readIntArrayFromFile("testSrc/testFiles/input05.txt");
        correctResult = Arrays.asList(0, 1, 0, 1, 0);
        assert arr5 != null;
        actualResult = (logic.createNewList(logic.intArrayToList(arr5)));
        Assert.assertEquals(correctResult, actualResult);
    }

    @Test
    public void test06() {
        int[] arr5 = Util.readIntArrayFromFile("testSrc/testFiles/input06.txt");
        correctResult = Arrays.asList(3, 4);
        assert arr5 != null;
        actualResult = (logic.createNewList(logic.intArrayToList(arr5)));
        Assert.assertEquals(correctResult, actualResult);
    }

    @Test
    public void test07() {
        int[] arr5 = Util.readIntArrayFromFile("testSrc/testFiles/input07.txt");
        correctResult = Arrays.asList(11, 22, 11);
        assert arr5 != null;
        actualResult = (logic.createNewList(logic.intArrayToList(arr5)));
        Assert.assertEquals(correctResult, actualResult);
    }

    @Test
    public void test08() {
        int[] arr5 = Util.readIntArrayFromFile("testSrc/testFiles/input08.txt");
        correctResult = Arrays.asList(100, 1, 100, 1);
        assert arr5 != null;
        actualResult = (logic.createNewList(logic.intArrayToList(arr5)));
        Assert.assertEquals(correctResult, actualResult);
    }

    @Test
    public void test09() {
        int[] arr5 = Util.readIntArrayFromFile("testSrc/testFiles/input09.txt");
        correctResult = Arrays.asList(12, 2, 12, 122, 2);
        assert arr5 != null;
        actualResult = (logic.createNewList(logic.intArrayToList(arr5)));
        Assert.assertEquals(correctResult, actualResult);
    }

    @Test
    public void test10() {
        int[] arr5 = Util.readIntArrayFromFile("testSrc/testFiles/input10.txt");
        correctResult = Arrays.asList(1, 2);
        assert arr5 != null;
        actualResult = (logic.createNewList(logic.intArrayToList(arr5)));
        Assert.assertEquals(correctResult, actualResult);
    }

}
