package entities

import org.main.entities.Chip

data class Player(val id: String, val chips: MutableMap<Chip, Int> = mutableMapOf()) {

    init {
        for (chip in Chip.entries) {
            chips.put(chip, 0)
        }
    }

    fun gain(amount: Int) {
        setChipsFromAmount(totalAmount() + amount)
    }

    fun betPayment(amount: Int) {
        if (amount > totalAmount()) {
            throw Exception("Le montant ne peut pas être inférieur à 0")
        }
        setChipsFromAmount(totalAmount() - amount)
    }

    fun totalAmount(): Int {
        return chips
            .map { it.key.value * it.value }
            .sum()
    }

    private fun setChipsFromAmount(amount: Int) {
        chips.map { chip -> chips.put(chip.key, 0) }
        var remainingAmount = amount
        val chipsDesc = Chip.entries.sortedByDescending { it.value }

        for (chip in chipsDesc) {
            val chipsValue = remainingAmount / chip.value
            if (chipsValue > 0) {
                addChip(chip, chipsValue)
                remainingAmount = remainingAmount.minus(chipsValue * chip.value)
            }
        }
    }

    private fun addChip(chip: Chip, chipsNumberToAdd: Int) {
        val totalChipsNumber = chips[chip]?.plus(chipsNumberToAdd) ?: chipsNumberToAdd
        chips.put(chip, totalChipsNumber)
    }
}

fun Player.formattedBalance(currency: String): String {
    return "Votre solde est de ${totalAmount()} $currency"
}