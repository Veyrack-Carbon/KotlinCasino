package entities

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.main.entities.Chip

class PlayerTest {
    @Test
    fun shouldGainAmount() {
        val player = Player("123")
        player.gain(518)
        assertEquals(5, player.chips[Chip.HUNDRED])
        assertEquals(1, player.chips[Chip.TEN])
        assertEquals(1, player.chips[Chip.FIVE])
        assertEquals(1, player.chips[Chip.TWO])
        assertEquals(1, player.chips[Chip.ONE])
    }

    @Test
    fun shouldWithdrawAmount() {
        val player = Player("123")
        player.gain(528)
        assertEquals(2, player.chips[Chip.TEN])

        player.betPayment(10)
        assertEquals(1, player.chips[Chip.TEN])
        assertEquals(1, player.chips[Chip.ONE])
        assertEquals(1, player.chips[Chip.TWO])
        assertEquals(1, player.chips[Chip.FIVE])
        assertEquals(5, player.chips[Chip.HUNDRED])
    }

    @Test
    fun shouldThrowOnWithdrawAmount() {
        val player = Player("123")
        player.gain(518)

        assertThrows<Exception>(
            "Le montant ne peut pas être inférieur à 0",
            {
                player.betPayment(1000)
            })
    }

    @Test
    fun shouldComputeFullAMount() {
        val player = Player("123")
        player.gain(515)
        player.gain(30)
        player.gain(5)
        assertEquals(550, player.totalAmount())
    }

    @Test
    fun shouldPrintFormattedBalance() {
        val player = Player("123")
        player.gain(515)
        player.gain(30)
        player.gain(5)
        assertEquals("Votre solde est de 550 €", player.formattedBalance("€"))
    }

}