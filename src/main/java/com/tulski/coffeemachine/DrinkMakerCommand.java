package com.tulski.coffeemachine;

public interface DrinkMakerCommand {

    enum DrinkMakerCommandType {
        TEA("T"),
        CHOCOLATE("H"),
        COFFEE("C"),
        MESSAGE("M"),
        ORANGE_JUICE("O");

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
                      boolean stick, boolean extraHot) implements DrinkMakerCommand {

        public DrinkOrder(DrinkType drinkType, int sugarQuantity, boolean extraHot) {
            this(drinkType, sugarQuantity, sugarQuantity > 0, extraHot);
        }

        public DrinkOrder(DrinkType drinkType, int sugarQuantity) {
            this(drinkType, sugarQuantity, sugarQuantity > 0, false);
        }

        public DrinkOrder(DrinkType drinkType) {
            this(drinkType, 0, false, false);
        }

        public DrinkOrder {
            if (sugarQuantity < 0 || sugarQuantity > 2) {
                throw new IllegalArgumentException("Sugar quantity must be between 0 and 2");
            }
            if (drinkType == DrinkType.ORANGE_JUICE && extraHot) {
                throw new IllegalArgumentException("Orange juice cannot be extra hot");
            }
        }

    }
}
