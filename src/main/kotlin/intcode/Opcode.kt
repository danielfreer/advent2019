package intcode

enum class Opcode {
    ADD,
    MULTIPLY,
    WRITE,
    READ,
    HALT,
    ;

    companion object {
        fun of(code: Int) = when (code) {
            1 -> ADD
            2 -> MULTIPLY
            3 -> WRITE
            4 -> READ
            99 -> HALT
            else -> throw IllegalArgumentException("Unknown opcode: $code")
        }
    }
}
