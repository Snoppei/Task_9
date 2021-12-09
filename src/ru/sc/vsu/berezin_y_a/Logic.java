package ru.sc.vsu.berezin_y_a;

import java.util.*;

public class Logic {

    public List<Integer> createNewList(List<Integer> list) {
        for (int i = 1; i < list.size(); i++) {
            if (Objects.equals(list.get(i - 1), list.get(i))) {
                list.remove(i - 1);
                i--;
            }
        }
        return list;
    }

    public void fillList(List<Integer> list) {
        list.clear();
        Scanner scn = new Scanner(System.in);
        System.out.println("Enter integer numbers (to end enter a negative number):");
        while (true) {
            System.out.print("> ");
            int num = scn.nextInt();
            if (num < 0) {
                break;
            }
            list.add(num);
        }
    }

    public <T> void printCollectionInConsole(Collection<T> collection) {
        boolean first = true;
        for (T item : collection) {
            System.out.print((first ? "" : ", ") + item);
            first = false;
        }
        System.out.println();
    }

    public List<Integer> intArrayToList(int[] arr) {
        List<Integer> list = new ArrayList<>(arr.length);
        for (int i = 0; i < arr.length; i++) {
            list.add(i, arr[i]);
        }
        return list;
    }

}
