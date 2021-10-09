package database.arangodb

import com.arangodb.ArangoDB
import com.arangodb.mapping.ArangoJack
import com.fasterxml.jackson.module.kotlin.KotlinModule
import data.FindResult
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

    override fun find(partNumber: String): List<FindResult> = listOf(
        findElectronicAssembly(partNumber),
        findPartSpecification(partNumber),
        findPurchasedPart(partNumber)
    ).flatten()


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

    private fun findElectronicAssembly(partNumber: String): List<FindResult> = arangoDb
        .db(DATABASE_NAME)
        .query(
            """
                FOR d in $ELECTRONIC_ASSEMBLY_VIEW
                    SEARCH ANALYZER(LIKE(d.partNumber, @like), "identity")
                        RETURN {
                            partNumber: d.partNumber,
                            partType: @partType,
                            description: d.description,
                            id: d._id
                            }
            """,
            mapOf(
                "like" to partNumber.toLikeOperand(),
                "partType" to ELECTRONIC_ASSEMBLY
            ),
            FindResult::class.java
        )
        .toList()

    private fun findPartSpecification(partNumber: String): List<FindResult> = arangoDb
        .db(DATABASE_NAME)
        .query(
            """
                FOR d in $PART_SPECIFICATION_VIEW
                    SEARCH ANALYZER(LIKE(d.partNumber, @like), "identity")
                        RETURN {
                            partNumber: d.partNumber,
                            partType: @partType,
                            description: d.description,
                            id: d._id
                            }
            """,
            mapOf(
                "like" to partNumber.toLikeOperand(),
                "partType" to PART_SPECIFICATION
            ),
            FindResult::class.java
        )
        .toList()

    private fun findPurchasedPart(partNumber: String): List<FindResult> = arangoDb
        .db(DATABASE_NAME)
        .query(
            """
                FOR d in $PURCHASED_PART_VIEW
                    SEARCH ANALYZER(LIKE(d.groups.parts.partNumber, @like), "identity")
                    FOR q in FLATTEN(d.groups[*].parts[* FILTER LIKE(CURRENT.partNumber, @like)])
                        RETURN {
                            partNumber: q.partNumber,
                            partType: @partType,
                            description: d.description,
                            id: d._id
                            }
            """,
            mapOf(
                "like" to partNumber.toLikeOperand(),
                "partType" to PURCHASED_PART
            ),
            FindResult::class.java
        )
        .toList()

    companion object {

        const val DATABASE_NAME = "_system"
        const val SEARCH_VIEW = "searchTest"

        const val ELECTRONIC_ASSEMBLY = "electronicAssembly"
        const val ELECTRONIC_ASSEMBLY_VIEW = "electronicAssemblySearch"

        const val PART_SPECIFICATION = "partSpecification"
        const val PART_SPECIFICATION_VIEW = SEARCH_VIEW

        const val PURCHASED_PART = "purchasedPart"
        const val PURCHASED_PART_VIEW = SEARCH_VIEW

        private val FIND_SPECIAL = Regex("[_%]")
        private const val REPLACE_SPECIAL = "\\\\\\\\$0"

        private fun String.toLikeOperand(): String =
            "%${FIND_SPECIAL.replace(this, REPLACE_SPECIAL)}%"
    }
}