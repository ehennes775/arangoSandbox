package command.commands.find

import command.Command
import database.Database
import report.Report

class FindCommand(
    private val database: Database,
    private val partNumber: String
): Command {

    override fun execute() {
        database.find(partNumber).let { result ->
            Report.generateFrom(result) {
                column(
                    title = "partNumber",
                    value = { partNumber }
                )
                column(
                    title = "partType",
                    value = { partType }
                )
                column(
                    title = "description",
                    value = { description }
                )
            }
        }
    }
}
