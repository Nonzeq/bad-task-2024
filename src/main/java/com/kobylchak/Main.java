package com.kobylchak;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    private static final String PATH_TO_FILE = "src/main/resources/files/input_file.txt";
    private static final String PATH_TO_TEST_FILE = "src/main/resources/files/test_file.txt";

    public static void main(String[] args) {
        System.out.println(ConsoleColors.YELLOW_BOLD + "Input file should be in current " +
                "directory" + ConsoleColors.RESET);
        System.out.print("Enter a file name: ");
        String path;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            path = bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Cannot read line",e);
        }
        System.out.println(ConsoleColors.GREEN_BOLD +   "Processing..." + ConsoleColors.RESET);
        long start = System.currentTimeMillis();
        List<String> data;
        if (path == null) {
            throw new RuntimeException("Path is null");
        }
        try {
            data = Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException("Cannot read file: " + path ,e);
        }
        int[] ar = new int[data.size()];
        for (int i = 0; i < data.size(); i++) {
            ar[i] = Integer.parseInt(data.get(i));
        }

        int[] largeIncreaseSequance = getLargeIncreaseSequence(ar);
        int[] largeDecreaseSequance = getLargeDecreaseSequence(ar);

        Arrays.sort(ar);

        long median = ar.length % 2 == 1 ? ar[ar.length / 2] :
                (ar[ar.length / 2 - 1] + ar[ar.length / 2]) / 2;
        long end = System.currentTimeMillis();
        System.out.println("Exectution time: " + (end - start) + " ms");
        System.out.println("Max: " + ar[ar.length - 1]);
        System.out.println("Min: " + ar[0]);
        System.out.println("Avg: " + Arrays.stream(ar).sum() / ar.length);
        System.out.println("Median: " + median);
        System.out.println("Increase sequence: " + Arrays.stream(largeIncreaseSequance)
                .mapToObj(String::valueOf).collect(Collectors.joining(",")));
        System.out.println("Decrease sequence: " + Arrays.stream(largeDecreaseSequance)
                .mapToObj(String::valueOf).collect(Collectors.joining(",")));
    }

    private static int[] getLargeIncreaseSequence(int[] ar) {
        int maxCount = 0;
        int left = 0;
        int right = 0;
        for (int i = 0; i < ar.length; i++) {
            int c = 0;
            for (int j = i + 1; j < ar.length; j++) {
                if (ar[j] > ar[j - 1]) {
                    c++;
                } else {
                    if (c > maxCount) {
                        maxCount = c;
                        left = i;
                        right = j - i - 1;
                    }
                    break;
                }
            }
            if (c == ar.length - 1) {
                left = i;
                right = ar.length - 1;
                break;
            }
        }
        int[] res = new int[right + 1];
        for (int i = 0; i < res.length; i++) {
            res[i] = ar[left++];
        }
        return res;
    }

    private static int[] getLargeDecreaseSequence(int[] ar) {
        int maxCount = 0;
        int left = 0;
        int right = 0;
        for (int i = 0; i < ar.length; i++) {
            int c = 0;
            for (int j = i + 1; j < ar.length; j++) {
                if (ar[j] < ar[j - 1]) {
                    c++;
                } else {
                    if (c > maxCount) {
                        maxCount = c;
                        left = i;
                        right = j - i - 1;
                    }
                    break;
                }
            }
            if (c == ar.length - 1) {
                left = i;
                right = ar.length - 1;
                break;
            }
        }
        int[] res = new int[right + 1];
        for (int i = 0; i < res.length; i++) {
            res[i] = ar[left++];
        }
        return res;
    }
}