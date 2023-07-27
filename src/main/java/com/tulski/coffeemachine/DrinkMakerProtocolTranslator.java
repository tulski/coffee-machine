package com.tulski.coffeemachine;

import com.tulski.coffeemachine.DrinkMakerCommand.DrinkMakerCommandType;

class DrinkMakerProtocolTranslator {

    public DrinkMakerCommand translate(String order) {
        assertNotEmptyOrBlank(order);

        var parts = order.split(":", -1);

        var commandType = DrinkMakerCommandType.fromCode(parts[0]);

        if (commandType == DrinkMakerCommandType.MESSAGE) {
            assertValidExpressionsLength(parts, 2);
            return new DrinkMakerCommand.NotifyCustomer(parts[1]);
        }
        assertValidExpressionsLength(parts, 3);

        var sugarQuantity = parts[1].isBlank() ? 0 : Integer.parseInt(parts[1]);
        if (sugarQuantity < 0 || sugarQuantity > 2) {
            throw new IllegalArgumentException("Sugar quantity must be between 0 and 2");
        }
        var stick = sugarQuantity > 0;

        var drink = mapCommadTypeToDrinkType(commandType);

        return new DrinkMakerCommand.MakeDrink(drink, 1, sugarQuantity, stick);
    }

    private static Drink mapCommadTypeToDrinkType(DrinkMakerCommandType commandType) {
        switch (commandType) {
            case TEA:
                return Drink.TEA;
            case CHOCOLATE:
                return Drink.CHOCOLATE;
            case COFFEE:
                return Drink.COFFEE;
            default:
                throw new IllegalArgumentException("Unknown command type: " + commandType);
        }
    }

    private void assertNotEmptyOrBlank(String order) {
        if (order == null || order.isBlank()) {
            throw new IllegalArgumentException("Order cannot be null or empty");
        }
    }

    private void assertValidExpressionsLength(String[] parts, int expectedLength) {
        if (parts.length != expectedLength) {
            throw new IllegalArgumentException("Order must have " + expectedLength + " parts");
        }
    }
}
