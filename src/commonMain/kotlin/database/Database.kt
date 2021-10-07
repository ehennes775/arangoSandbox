package database

import data.WhereResult

interface Database {

    fun where(partNumber: String): List<WhereResult>
}
