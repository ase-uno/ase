package de.dhbw.karlsruhe.tinf20b2.ase.uno.model.console;

public enum ConsoleColor {

    BLACK(30),
    RED(31),
    GREEN(32),
    YELLOW(33),
    BLUE(34),
    MAGENTA(35),
    CYAN(36),
    WHITE(37),
    RESET(39);

    private final int colorNumber;
    ConsoleColor(int colorNumber) {
        this.colorNumber = colorNumber;
    }

    public int getColorNumber() {
        return colorNumber;
    }
}
