package ru.job4j.ood.isp.menu;

/**
 * Класс представляет собой консольное приложение, которое позволяет:
 * Добавить элемент в корень меню;
 * Добавить элемент к родительскому элементу;
 * Вызвать действие, привязанное к пункту меню (по умолчанию действие представлено константой DEFAULT_ACTION)
 * Вывести меню в консоль.
 */
public class TodoApp {
    public static final  ActionDelegate DEFAULT_ACTION = () -> System.out.println("some action");
    private final Output out;

    public TodoApp(Output output) {
        this.out = output;
    }

    public void startApp(Input input, Menu menu) {
        boolean run = true;
        while (run) {
            out.println(showMenu());
            int select = input.askInt("Please, select point from menu");
            switch (select) {
                case 1:
                    boolean taskAdded = menu.add(Menu.ROOT, input.askStr("Please, input the task name"), DEFAULT_ACTION);
                    if (taskAdded) {
                        out.println("Task successfully added");
                    }
                    break;
                case 2:
                    String task = input.askStr("Please, input existed task name");
                    String subTask = input.askStr("Please, input subtask name");
                    boolean subTaskAdded = menu.add(task, subTask, DEFAULT_ACTION);
                    if (subTaskAdded) {
                        out.println("Task successfully added");
                    }
                    break;
                case 3:
                    menu.select(input.askStr("Input name of task which linked with action"))
                            .get()
                            .getActionDelegate()
                            .delegate();
                    break;
                case 4:
                    MenuPrinter printer = new Printer();
                    printer.print(menu);
                    out.println(System.lineSeparator());
                    break;
                default:
                    run = false;
            }
        }
    }

    private String showMenu() {
        String newLine = System.lineSeparator();
        return "1. Create a main task" + newLine
                + "2. Create a subtask" + newLine
                + "3. Call the action linked to an task or subtask" + newLine
                + "4. Print the menu with tasks" + newLine
                + "Print any another letter or number to Exit" + newLine;
    }

    public static void main(String[] args) {
        Input input = new ConsoleInput();
        Output output = new ConsoleOutput();
        Input validateInput = new ValidateInput(output, input);
        Menu menu = new SimpleMenu();
        TodoApp app = new TodoApp(output);
        app.startApp(validateInput, menu);
    }
}
