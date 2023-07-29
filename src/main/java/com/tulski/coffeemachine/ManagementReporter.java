package com.tulski.coffeemachine;

interface ManagementReporter {
    void collect(CompletedDrinkOrder completedDrinkOrder);

    void report();
}
