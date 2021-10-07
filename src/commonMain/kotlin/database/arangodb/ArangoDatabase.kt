package database.arangodb

import com.arangodb.ArangoDB
import com.arangodb.mapping.ArangoJack
import com.fasterxml.jackson.module.kotlin.KotlinModule
import data.WhereResult
import database.Database

class ArangoDatabase: Database {

    private val arangoDb = ArangoDB.Builder()
        .serializer(ArangoJack().apply {
            configure {
                it.registerModule(KotlinModule())
            }
        })
        .build()

    override fun where(partNumber: String): List<WhereResult> = arangoDb
        .db(DATABASE_NAME)
        .query(
            """
                FOR d in $SEARCH_VIEW
                    SEARCH ANALYZER(LIKE(d.groups.parts.partNumber, @like), "identity")
                    FOR q in FLATTEN(d.groups[*].parts[* FILTER LIKE(CURRENT.partNumber, @like)])
                        RETURN {
                            part: q,
                            specification: d.partNumber,
                            description: d.description,
                            id: d._id
                            }
            """,
            mapOf(
                "like" to partNumber.toLikeOperand()
            ),
            WhereResult::class.java
        )
        .toList()

    companion object {

        const val DATABASE_NAME = "_system"
        const val SEARCH_VIEW = "searchTest"

        private val FIND_SPECIAL = Regex("[_%]")
        private const val REPLACE_SPECIAL = "\\\\\\\\$0"

        private fun String.toLikeOperand(): String =
            "%${FIND_SPECIAL.replace(this, REPLACE_SPECIAL)}%"
    }
}