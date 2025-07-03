package org.main

import entities.Player
import org.main.machines.ClassicSlotMachine
import org.main.machines.JokerSlotMachine
import org.main.machines.SlotMachine

fun main() {
    val player = initGame()

    val slotMachine: SlotMachine = slotMachineSelection()

    playMachine(player, slotMachine)
}

private fun playMachine(player: Player, slotMachine: SlotMachine) {
    while (true) {
        println("Entrez votre mise (0 pour quitter) : ")
        val input = readLine()

        val bet = input?.toIntOrNull()
        if (bet == null || bet < 0) {
            println("Mise invalide. Réessayez.")
            continue
        }
        if (bet == 0) {
            println("Merci d'avoir joué ! Vous repartez avec ${player.totalAmount()} €.")
            break
        }
        if (bet > player.totalAmount()) {
            println("Vous n'avez pas assez de jetons pour cette mise.")
            continue
        }

        slotMachine.play(player, bet)

        println("Jetons restants : ${player.totalAmount()} €")

        if (player.totalAmount() <= 0) {
            println("Vous êtes à court de jetons, la partie est terminée.")
            break
        }
    }
}

private fun slotMachineSelection(): SlotMachine {
    var slotMachine: SlotMachine
    while (true) {
        println(
            "Selectionnez la machine sur laquelle vous souhaitez jouer : \n" +
                    "1 pour la Classique ou 2 pour la Joker"
        )
        val machine = readLine()
        when {
            machine?.toIntOrNull() == 1 -> {
                slotMachine = ClassicSlotMachine()
                break
            }

            machine?.toIntOrNull() == 2 -> {
                slotMachine = JokerSlotMachine()
                break
            }

            else -> {
                println("Vous n'avez pas rentrer une bonne machine")
                continue
            }
        }
    }
    return slotMachine
}

private fun initGame(): Player {
    val player = Player("123")
    player.gain(100)
    println("Bienvenue au casino ! Vous avez ${player.totalAmount()} € en jetons.\n")
    return player
}
