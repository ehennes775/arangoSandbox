package command.commands.update

import command.Command
import database.Database
import java.io.File

class UpdateCommand(
    val database: Database,
    val schematicFiles: Array<File>
): Command {

    override fun execute() {
    }
}
