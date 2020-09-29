package intcode

enum class ParameterMode {
    POSITION,
    IMMEDIATE,
    ;

    companion object {
        fun of(mode: Int) = when (mode) {
            0 -> POSITION
            1 -> IMMEDIATE
            else -> throw IllegalArgumentException("Unknown parameter mode: $mode")
        }
    }
}
