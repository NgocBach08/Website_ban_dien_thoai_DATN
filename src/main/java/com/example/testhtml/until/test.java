package com.example.testhtml.until;

import java.util.Scanner;

/**
 * Description:
 *
 * @author: hieu
 * @since: 11/12/2023
 * Project_name: com.example.testhtml.until
 */
public class test {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int w = sc.nextInt();

        if (w % 2 == 0) {
            System.out.printf("YES");
        } else {
            System.out.printf("NO");
        }

    }
}
