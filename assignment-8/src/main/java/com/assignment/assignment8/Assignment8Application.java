package com.assignment.assignment8;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*

Розробити веб-застосування клієнт-серверної архітектури, що реалізує алгоритм множення
матриць (або інший обчислювальний алгоритм, який був Вами реалізований іншими методами розподілених обчислень
в рамках курсу «Паралельні та розподілені обчислення») на стороні сервера. Розгляньте два варіанти реалізації
1) дані для обчислень знаходяться на сервері та
2) дані для обчислень знаходяться на клієнтській частині застосування

Дослідити швидкість виконання запиту користувача при різних обсягах даних

Порівняти реалізацію алгоритму в клієнт-серверній системи та в розподіленій системі з рівноправними процесорами.

 */
@SpringBootApplication
public class Assignment8Application {

    public static void main(String[] args) {
        SpringApplication.run(Assignment8Application.class, args);
    }

}
