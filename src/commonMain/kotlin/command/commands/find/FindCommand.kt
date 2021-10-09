package command.commands.find

import command.Command
import database.arangodb.ArangoDatabase
import report.Report

class FindCommand(
    private val partNumber: String
): Command {

    private val database = ArangoDatabase()

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
