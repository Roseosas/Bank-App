package Enums;

public enum Menu {
    MAIN_MENU(1),
    CUSTOMER_MENU(2),
    ADMIN_MENU(3);

    private final int value;

    Menu(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
