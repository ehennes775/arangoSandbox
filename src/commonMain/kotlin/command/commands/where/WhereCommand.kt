package command.commands.where

import database.arangodb.ArangoDatabase
import command.Command
import report.Report

class WhereCommand(private val partNumber: String): Command {

    private val database = ArangoDatabase()

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