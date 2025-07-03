package org.main.machines

import entities.Player

class ClassicSlotMachine : SlotMachine() {
    companion object {
        private const val ALL_IDENTICAL_ICON_GAIN_FACTOR = 5
    }
    override val iconsNumber = 3

    override fun play(player: Player, betAmount: Int) {
        player.betPayment(betAmount)

        val resultSlots = getRandomSlotIcons().keys

        player.gain(computeGain(betAmount, resultSlots))
    }

    private fun computeGain(betAmount: Int, resultSlots: MutableSet<SlotMachineIcons>): Int {
        return when {
            (resultSlots.size == 1) -> betAmount * ALL_IDENTICAL_ICON_GAIN_FACTOR
            (resultSlots.size == 2) -> betAmount
            (resultSlots.size > 2) -> 0
            else -> throw Exception()
        }
    }
}