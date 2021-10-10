package command.commands.find

import command.CommandBuilder
import command.exceptions.MissingArgument
import database.arangodb.ArangoDatabase

class FindBuilder(
    private val partNumber: String? = null
): CommandBuilder {

    private fun with(partNumber: String? = this.partNumber) = FindBuilder(
        partNumber = partNumber
    )

    override fun parse(args: Array<String>): FindBuilder {
        return args.fold(this) { builder, arg ->
            builder.with(arg)
        }
    }

    override fun build(): FindCommand = FindCommand(
        database = ArangoDatabase(),
        partNumber = partNumber ?: throw MissingArgument()
    )
}