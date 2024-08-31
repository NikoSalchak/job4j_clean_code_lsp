package ru.job4j.ood.isp.menu;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class SimpleMenuTest {

    public static final ActionDelegate STUB_ACTION = System.out::println;

    @Test
    public void whenAddThenReturnSame() {
        Menu menu = new SimpleMenu();
        menu.add(Menu.ROOT, "Сходить в магазин", STUB_ACTION);
        menu.add(Menu.ROOT, "Покормить собаку", STUB_ACTION);
        menu.add("Сходить в магазин", "Купить продукты", STUB_ACTION);
        menu.add("Купить продукты", "Купить хлеб", STUB_ACTION);
        menu.add("Купить продукты", "Купить молоко", STUB_ACTION);
        assertThat(new Menu.MenuItemInfo("Сходить в магазин",
                List.of("Купить продукты"), STUB_ACTION, "1."))
                .isEqualTo(menu.select("Сходить в магазин").get());
        assertThat(new Menu.MenuItemInfo(
                "Купить продукты",
                List.of("Купить хлеб", "Купить молоко"), STUB_ACTION, "1.1."))
                .isEqualTo(menu.select("Купить продукты").get());
        assertThat(new Menu.MenuItemInfo(
                "Покормить собаку", List.of(), STUB_ACTION, "2."))
                .isEqualTo(menu.select("Покормить собаку").get());
        menu.forEach(i -> System.out.println(i.getNumber() + i.getName()));
    }

    @Test
    public void whenAddThenSelectWillReturnSame() {
        Menu menu = new SimpleMenu();
        menu.add(Menu.ROOT, "Задача общая", STUB_ACTION);
        menu.add("Задача общая", "Задача первая", STUB_ACTION);
        menu.add("Задача общая", "Задача вторая", STUB_ACTION);
        menu.add("Задача первая", "Цель задачи", STUB_ACTION);
        menu.add("Задача первая", "Ограничения и требования к решению", STUB_ACTION);
        assertThat(new Menu.MenuItemInfo(
                "Задача общая", List.of("Задача первая", "Задача вторая"), STUB_ACTION, "1.")
        ).isEqualTo(menu.select("Задача общая").get());
        assertThat(new Menu.MenuItemInfo(
                "Цель задачи", List.of(), STUB_ACTION, "1.1.1.")
        ).isEqualTo(menu.select("Цель задачи").get());
    }
}