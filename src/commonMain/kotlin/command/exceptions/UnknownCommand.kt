package command.exceptions

class UnknownCommand(command: String): Exception("Unknown command: \"$command\"")
