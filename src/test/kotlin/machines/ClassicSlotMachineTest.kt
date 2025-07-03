package machines

import entities.Player
import io.mockk.every
import io.mockk.spyk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.main.machines.ClassicSlotMachine
import org.main.machines.SlotMachineIcons


class ClassicSlotMachineTest {

    val slotMachine = spyk(ClassicSlotMachine())
    lateinit var player: Player
    val initialAmount = 518

    @BeforeEach
    fun setupPlayer() {
        player = Player("123")
        player.gain(initialAmount)
    }

    @Test
    fun shouldWinClassicSlotMachine() {
        val betAmount = 10
        every { slotMachine.getRandomSlotIcon() } returns SlotMachineIcons.COIN

        assertEquals(initialAmount, player.totalAmount())
        slotMachine.play(player, betAmount)
        assertEquals(initialAmount + betAmount * 4, player.totalAmount())
    }

    @Test
    fun shouldReturnBetClassicSlotMachine() {
        val betAmount = 10

        val draw = listOf(SlotMachineIcons.COIN, SlotMachineIcons.COIN, SlotMachineIcons.CROWN)
        var i = 0
        every { slotMachine.getRandomSlotIcon() } answers { draw[i++] }

        assertEquals(initialAmount, player.totalAmount())
        slotMachine.play(player, betAmount)
        assertEquals(initialAmount, player.totalAmount())
    }

    @Test
    fun shouldLoseClassicSlotMachine() {
        val betAmount = 10

        val draw = listOf(SlotMachineIcons.COIN, SlotMachineIcons.TRIPLE_COIN, SlotMachineIcons.CROWN)
        var i = 0
        every { slotMachine.getRandomSlotIcon() } answers { draw[i++] }

        assertEquals(initialAmount, player.totalAmount())
        slotMachine.play(player, betAmount)
        assertEquals(initialAmount - betAmount, player.totalAmount())
    }
}