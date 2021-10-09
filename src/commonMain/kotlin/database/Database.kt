package database

import data.FindResult
import data.WhereResult

interface Database {

    fun find(partNumber: String): List<FindResult>
    fun where(partNumber: String): List<WhereResult>
}
