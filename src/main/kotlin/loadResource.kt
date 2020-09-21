fun loadResource(fileName: String): List<String> {
    return object {}.javaClass.getResource(fileName)
        .readText()
        .split("\n")
        .filter(String::isNotBlank)
}
