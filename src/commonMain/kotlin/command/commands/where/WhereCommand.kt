package command.commands.where

import database.arangodb.ArangoDatabase
import command.Command
import database.Database
import report.Report

class WhereCommand(
    private val database: Database,
    private val partNumber: String
    ): Command {

    override fun execute() {
        database.where(partNumber).let { result ->
            Report.generateFrom(result) {
                column(
                    title = "manufacturer",
                    value = { part.manufacturer }
                )
                column(
                    title = "partNumber",
                    value = { part.partNumber }
                )
                column(
                    title = "specification",
                    value = { specification }
                )
                column(
                    title = "description",
                    value = { description }
                )
            }
        }
    }
}