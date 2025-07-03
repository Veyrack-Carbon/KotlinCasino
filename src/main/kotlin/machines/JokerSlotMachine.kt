package org.main.machines

import entities.Player
import kotlin.random.Random

class JokerSlotMachine: SlotMachine() {
    companion object {
        private const val ALL_IDENTICAL_ICON_WITHOUT_JOKER_GAIN_FACTOR = 10
        private const val ALL_IDENTICAL_ICON_WITH_JOKER_GAIN_FACTOR = 9
        private const val ALL_BUT_ONE_IDENTICAL_ICON_GAIN_FACTOR = 5
    }
    override val iconsNumber = 5

    override fun play(player: Player, betAmount: Int) {
        player.betPayment(betAmount)

        val (resultSlots, hasJocker) = getRandomSlotIconsWithJocker()

        player.gain(computeGain(betAmount, resultSlots, hasJocker))
    }

    private fun getRandomSlotIconsWithJocker(): Pair<MutableMap<SlotMachineIcons, Int>, Boolean> {
        val resultSlots = getRandomSlotIcons()
        val hasJocker = Random.Default.nextInt(2) == 0

        getRandomSlotIcon().takeIf { hasJocker }.let { jocker ->
            val maxOccurrenceSlotIcon = resultSlots.maxBy { it.value }
            val jockerSlotIconOccurrence = resultSlots[jocker] ?: 0
            if (maxOccurrenceSlotIcon.key != jocker) {
                resultSlots.remove(jocker)
                resultSlots.put(maxOccurrenceSlotIcon.key, maxOccurrenceSlotIcon.value + jockerSlotIconOccurrence)
            }
        }
        return resultSlots to hasJocker
    }

    private fun computeGain(betAmount: Int, resultSlots: MutableMap<SlotMachineIcons, Int>, hasJocker: Boolean): Int {
        val maxOccurrenceSlotIcon = resultSlots.maxBy { it.value }.value
        return when {
            (maxOccurrenceSlotIcon == iconsNumber) -> betAmount * (if (hasJocker) ALL_IDENTICAL_ICON_WITH_JOKER_GAIN_FACTOR else ALL_IDENTICAL_ICON_WITHOUT_JOKER_GAIN_FACTOR)
            (maxOccurrenceSlotIcon == iconsNumber - 1) -> betAmount * ALL_BUT_ONE_IDENTICAL_ICON_GAIN_FACTOR
            (maxOccurrenceSlotIcon == iconsNumber - 2) -> betAmount
            (maxOccurrenceSlotIcon < iconsNumber - 2) -> 0
            else -> throw Exception()
        }
    }
}