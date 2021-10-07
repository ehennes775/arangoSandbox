package command.commands.where

import command.CommandBuilder
import command.exceptions.MissingArgument

class WhereBuilder(
    private val partNumber: String? = null
): CommandBuilder {

    private fun with(partNumber: String? = this.partNumber) = WhereBuilder(
        partNumber = partNumber
    )

    override fun parse(args: Array<String>): WhereBuilder {
        return args.fold(this) { builder, arg ->
            builder.with(arg)
        }
    }

    override fun build(): WhereCommand = WhereCommand(
        partNumber = partNumber ?: throw MissingArgument()
    )
}