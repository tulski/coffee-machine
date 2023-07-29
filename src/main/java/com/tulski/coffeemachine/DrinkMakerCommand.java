package com.tulski.coffeemachine;

public interface DrinkMakerCommand {

    enum DrinkMakerCommandType {
        TEA("T"),
        CHOCOLATE("H"),
        COFFEE("C"),
        MESSAGE("M");

        private final String code;

        static DrinkMakerCommandType fromCode(String code) {
            for (DrinkMakerCommandType type : DrinkMakerCommandType.values()) {
                if (type.code.equals(code)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Unknown code: " + code);
        }

        DrinkMakerCommandType(String code) {
            this.code = code;
        }

    }

    record NotifyCustomer(String message) implements DrinkMakerCommand {
    }

    record DrinkOrder(DrinkType drinkType, int sugarQuantity,
                      boolean stick) implements DrinkMakerCommand {

        public DrinkOrder {
            if (sugarQuantity < 0 || sugarQuantity > 2) {
                throw new IllegalArgumentException("Sugar quantity must be between 0 and 2");
            }
        }

    }
}
