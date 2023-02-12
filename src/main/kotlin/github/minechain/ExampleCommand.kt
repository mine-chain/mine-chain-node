package github.minechain

import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class ExampleCommand : Command(
  "example",
  "info...",
  "use...",
  listOf("e")
) {
  override fun execute(sender: CommandSender, label: String, args: Array<out String>): Boolean {
    // Your command logic here
    println(String.format("command %s %s %s", sender.name, label, args.joinToString(":")))
    return true
  }

  override fun tabComplete(sender: CommandSender, alias: String, args: Array<out String>): List<String> {
    // Здесь можно выполнить любые действия для определения возможных вариантов завершения команды
    val possibleCompletions = listOf("option1", "option2", "option3")
    return possibleCompletions.filter { it.startsWith(args.lastOrNull() ?: "") }
  }
}
