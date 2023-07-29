package com.tulski.coffeemachine;

import com.tulski.coffeemachine.DrinkMakerCommand.DrinkMakerCommandType;

class DrinkMakerProtocolTranslator {

    public DrinkMakerCommand translate(String order) {
        assertNotEmptyOrBlank(order);

        var parts = order.split(":", -1);

        var commandCode = parts[0].substring(0, 1);
        var extraHot = parts[0].substring(1).equals("h");

        var commandType = DrinkMakerCommandType.fromCode(commandCode);

        if (commandType == DrinkMakerCommandType.MESSAGE) {
            assertValidExpressionsLength(parts, 2);
            return new DrinkMakerCommand.NotifyCustomer(parts[1]);
        }
        assertValidExpressionsLength(parts, 3);

        var sugarQuantity = parts[1].isBlank() ? 0 : Integer.parseInt(parts[1]);

        var drink = mapCommadTypeToDrinkType(commandType);

        return new DrinkMakerCommand.DrinkOrder(drink, sugarQuantity, extraHot);
    }

    private static DrinkType mapCommadTypeToDrinkType(DrinkMakerCommandType commandType) {
        return switch (commandType) {
            case TEA -> DrinkType.TEA;
            case CHOCOLATE -> DrinkType.CHOCOLATE;
            case COFFEE -> DrinkType.COFFEE;
            case ORANGE_JUICE -> DrinkType.ORANGE_JUICE;
            default -> throw new IllegalArgumentException("Unknown command type: " + commandType);
        };
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
