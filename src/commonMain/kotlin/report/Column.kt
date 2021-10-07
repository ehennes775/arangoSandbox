package report

import java.lang.Integer.max


class Column<T>(
    private val title: String,
    dataWidth: Int,
    val value: (T) -> String
) {
    private val ruleWidth = max(title.length, dataWidth)

    private val overallWidth = INTRA_COLUMN_SPACING + ruleWidth

    private val stringFormat = "%-${overallWidth}s"

    fun printTitle(lastColumn: Boolean) {
        printString(title, lastColumn)
    }

    fun printRule(lastColumn: Boolean) {
        printString(HORIZONTAL_RULE_CHARACTER.repeat(ruleWidth), lastColumn)
    }

    fun printData(data: T, lastColumn: Boolean) {
        printString(value(data), lastColumn)
    }

    private fun printString(data: String, lastColumn: Boolean) {
        if (lastColumn) {
            println(data)
        } else {
            print(String.format(stringFormat, data))
        }
    }

    companion object {

        const val HORIZONTAL_RULE_CHARACTER = "="
        const val INTRA_COLUMN_SPACING = 2
    }
}
