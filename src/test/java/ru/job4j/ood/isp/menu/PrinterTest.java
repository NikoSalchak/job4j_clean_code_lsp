package ru.job4j.ood.isp.menu;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.*;

class PrinterTest {

    @Test
    void whenAddThenPrintOut() {
        ActionDelegate actionDelegate = System.out::println;
        Menu menu = new SimpleMenu();
        menu.add(Menu.ROOT, "Сходить в магазин", actionDelegate);
        menu.add("Сходить в магазин", "Купить продукты", actionDelegate);
        menu.add("Купить продукты", "Купить хлеб", actionDelegate);
        menu.add("Купить продукты", "Купить молоко", actionDelegate);
        menu.add(Menu.ROOT, "Покормить собаку", actionDelegate);
        MenuPrinter printer = new Printer();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        String newLine = System.lineSeparator();
        String expected =
                "1. Сходить в магазин" + newLine
                + "----1.1. Купить продукты" + newLine
                + "--------1.1.1. Купить хлеб" + newLine
                + "--------1.1.2. Купить молоко" + newLine
                + "2. Покормить собаку" + newLine;
        printer.print(menu);
        assertThat(outputStream.toString()).isEqualTo(expected);
    }
}