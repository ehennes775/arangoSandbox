package command

import command.commands.find.FindBuilder
import command.commands.update.UpdateBuilder
import command.commands.where.WhereBuilder
import command.exceptions.CommandExpected
import command.exceptions.UnknownCommand

object CommandFactory {

    fun createCommand(args: Array<String>) = args
        .elementAtOrElse(0) { throw CommandExpected() }
        .let { commandName ->
            when (commandName) {
                "find" -> FindBuilder()
                "update" -> UpdateBuilder()
                "where" -> WhereBuilder()
                else -> throw UnknownCommand(commandName)
            }
        }
        .parse(args.drop(1).toTypedArray())
        .build()
}
