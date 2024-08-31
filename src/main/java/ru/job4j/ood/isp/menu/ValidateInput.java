package ru.job4j.ood.isp.menu;

public class ValidateInput implements Input {
    private final Output out;
    private final Input input;

    public ValidateInput(Output out, Input input) {
        this.out = out;
        this.input = input;
    }

    @Override
    public String askStr(String question) {
        return input.askStr(question);
    }

    @Override
    public int askInt(String question) {
        boolean invalid = true;
        int rsl = -1;
        do {
            try {
                rsl = input.askInt(question);
                invalid = false;
            } catch (NumberFormatException e) {
                out.println("Please, input correct number");
            }
        } while (invalid);
        return rsl;
    }
}
