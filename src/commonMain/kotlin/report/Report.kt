package report

class Report<T>(private val data: List<T>) {

    private val columns = mutableListOf<Column<T>>()

    fun column(title: String, value: T.() -> String) {
        columns.add(Column(
            title,
            data.maxOf { value(it).length },
            value
        ))
    }

    private fun output() {
        columns.forEachIndexed { index, column ->
            column.printTitle(index == columns.lastIndex)
        }
        columns.forEachIndexed { index, column ->
            column.printRule(index == columns.lastIndex)
        }
        data.forEach { row ->
            columns.forEachIndexed { index, column ->
                column.printData(row, index == columns.lastIndex)
            }
        }
    }

    companion object {

        fun<T> generateFrom(data: List<T>, configure: Report<T>.() -> Unit) {
            if (data.any()) {
                Report(data).let {
                    configure(it)
                    it.output()
                }
            }
            else {
                println("No parts found")
            }
        }
    }
}

