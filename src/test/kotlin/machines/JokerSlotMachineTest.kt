package machines

import entities.Player
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.spyk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.main.machines.SlotMachineIcons
import org.main.machines.JokerSlotMachine
import kotlin.random.Random

class JokerSlotMachineTest {
    val slotMachine = spyk(JokerSlotMachine())
    lateinit var player: Player
    val initialAmount = 518
    val betAmount = 10

    @BeforeEach
    fun setupPlayer() {
        player = Player("123")
        player.gain(initialAmount)
    }

    @Test
    fun shouldWinJokerSlotMachineWithJoker() {
        mockkObject(Random.Default)

        every { Random.nextInt(2) } returns 0
        every { slotMachine.getRandomSlotIcon() } returns SlotMachineIcons.COIN

        assertEquals(initialAmount, player.totalAmount())
        slotMachine.play(player, betAmount)
        assertEquals(initialAmount + betAmount * 8, player.totalAmount())
    }

    @Test
    fun shouldWinJokerSlotMachineWithoutJoker() {
        mockkObject(Random.Default)

        every { Random.nextInt(2) } returns 1
        every { slotMachine.getRandomSlotIcon() } returns SlotMachineIcons.COIN

        assertEquals(initialAmount, player.totalAmount())
        slotMachine.play(player, betAmount)
        assertEquals(initialAmount + betAmount * 9, player.totalAmount())
    }

    @Test
    fun shouldWinPartiallyJokerSlotMachineWithJoker() {
        mockkObject(Random.Default)

        every { Random.nextInt(2) } returns 0
        val draw = listOf(SlotMachineIcons.CHERRIES, SlotMachineIcons.COIN,
            SlotMachineIcons.CROWN, SlotMachineIcons.COIN, SlotMachineIcons.COIN, SlotMachineIcons.CHERRIES)
        var i = 0
        every { slotMachine.getRandomSlotIcon() } answers { draw[i++] }

        assertEquals(initialAmount, player.totalAmount())
        slotMachine.play(player, betAmount)
        assertEquals(initialAmount + betAmount * 4, player.totalAmount())
    }

    @Test
    fun shouldReturnBetJokerSlotMachineWithoutJoker() {
        mockkObject(Random.Default)

        every { Random.nextInt(2) } returns 1
        val draw = listOf(SlotMachineIcons.CHERRIES, SlotMachineIcons.COIN,
            SlotMachineIcons.CROWN, SlotMachineIcons.COIN, SlotMachineIcons.COIN, SlotMachineIcons.CHERRIES)
        var i = 0
        every { slotMachine.getRandomSlotIcon() } answers { draw[i++] }

        assertEquals(initialAmount, player.totalAmount())
        slotMachine.play(player, betAmount)
        assertEquals(initialAmount, player.totalAmount())
    }

    @Test
    fun shouldLoseJokerSlotMachineWithoutJoker() {
        mockkObject(Random.Default)

        every { Random.nextInt(2) } returns 1
        val sequence = listOf(SlotMachineIcons.CHERRIES, SlotMachineIcons.COIN,
            SlotMachineIcons.CROWN, SlotMachineIcons.BELLS, SlotMachineIcons.TRIPLE_COIN, SlotMachineIcons.PLUMS)
        var i = 0
        every { slotMachine.getRandomSlotIcon() } answers { sequence[i++] }

        assertEquals(initialAmount, player.totalAmount())
        slotMachine.play(player, betAmount)
        assertEquals(initialAmount - betAmount, player.totalAmount())
    }
}