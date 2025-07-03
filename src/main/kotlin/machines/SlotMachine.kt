package org.main.machines

import entities.Player

abstract class SlotMachine {
    protected open val iconsNumber: Int = 3

    abstract fun play(player: Player, betAmount: Int)

    internal fun getRandomSlotIcon(): SlotMachineIcons = SlotMachineIcons.entries.random()

    internal fun getRandomSlotIcons(): MutableMap<SlotMachineIcons, Int> {
        val resultSlots: MutableMap<SlotMachineIcons, Int> = mutableMapOf()
        repeat(iconsNumber) {
            val slotIcon = getRandomSlotIcon()
            resultSlots.put(slotIcon, resultSlots[slotIcon]?.plus(1) ?: 1)
        }
        return resultSlots
    }
}