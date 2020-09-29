package intcode

enum class Opcode {
    ADD,
    MULTIPLY,
    WRITE,
    READ,
    JUMP_IF_TRUE,
    JUMP_IF_FALSE,
    LESS_THAN,
    EQUALS,
    HALT,
    ;

    companion object {
        fun of(code: Int) = when (code) {
            1 -> ADD
            2 -> MULTIPLY
            3 -> WRITE
            4 -> READ
            5 -> JUMP_IF_TRUE
            6 -> JUMP_IF_FALSE
            7 -> LESS_THAN
            8 -> EQUALS
            99 -> HALT
            else -> throw IllegalArgumentException("Unknown opcode: $code")
        }
    }
}
