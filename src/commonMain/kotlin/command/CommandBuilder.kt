package command

interface CommandBuilder {

    fun parse(args: Array<String>): CommandBuilder
    fun build(): Command
}
