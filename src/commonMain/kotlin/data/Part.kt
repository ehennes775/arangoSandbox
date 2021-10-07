package data

class Part(
    var manufacturer: String = "",
    var partNumber: String = ""
) {
    override fun toString(): String = "$partNumber, $manufacturer"
}
