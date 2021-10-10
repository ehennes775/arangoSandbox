package command.commands.update

import command.Command
import database.arangodb.ArangoDatabase
import java.io.File

class UpdateCommand(
    schematicFiles: Array<File>
): Command {

    private val database = ArangoDatabase()

    override fun execute() {
    }
}
