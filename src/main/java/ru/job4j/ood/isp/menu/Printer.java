package ru.job4j.ood.isp.menu;

import java.util.Iterator;

public class Printer implements MenuPrinter {

    @Override
    public void print(Menu menu) {
        Iterator<Menu.MenuItemInfo> iterator = menu.iterator();
        while (iterator.hasNext()) {
            Menu.MenuItemInfo item = iterator.next();
            printWithIntention(item);
        }
    }

    private void printWithIntention(Menu.MenuItemInfo item) {
        String indention = "----";
        StringBuilder sb = new StringBuilder();
        String newLine = System.lineSeparator();
        int numberOfIndents = 2;
        while (item.getNumber().length() > numberOfIndents) {
            sb.append(indention);
            numberOfIndents += 2;
        }
        sb.append(item.getNumber()).append(" ").append(item.getName()).append(newLine);
        System.out.print(sb);
    }
}
