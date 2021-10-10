package command.commands.where

import command.CommandBuilder
import command.exceptions.MissingArgument
import database.arangodb.ArangoDatabase

class WhereBuilder(
    private val partNumber: String? = null
): CommandBuilder {

    private fun with(partNumber: String? = this.partNumber) = WhereBuilder(
        partNumber = partNumber
    )

    override fun parse(args: Array<String>): WhereBuilder {
        return args.fold(this) { builder, arg ->
            builder.with(partNumber = arg)
        }
    }

    override fun build(): WhereCommand = WhereCommand(
        database = ArangoDatabase(),
        partNumber = partNumber ?: throw MissingArgument()
    )
}