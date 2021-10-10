package command.commands.update

import command.CommandBuilder
import command.exceptions.MissingArgument
import database.arangodb.ArangoDatabase
import java.io.File

class UpdateBuilder(
    private val schematicFiles: Array<String> = emptyArray()
): CommandBuilder {

    private fun with(schematicFiles: Array<String> = this.schematicFiles) = UpdateBuilder(
        schematicFiles = schematicFiles
    )

    override fun parse(args: Array<String>): UpdateBuilder {
        return args.fold(this) { builder, arg ->
            builder.with(
                schematicFiles = schematicFiles + arg
            )
        }
    }

    override fun build(): UpdateCommand = UpdateCommand(
        database = ArangoDatabase(),
        schematicFiles = schematicFiles
            .map { File(it) }
            .toTypedArray()
            .also { if (it.isEmpty()) throw MissingArgument() }
    )
}
