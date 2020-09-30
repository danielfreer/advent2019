import kotlin.time.ExperimentalTime

@ExperimentalTime
fun day6(input: List<String>): List<Solution> {
    return listOf(
        solve(6, 1) { countTotalOrbits(input) }
    )
}

fun countTotalOrbits(orbitMap: List<String>): Int {
    val rootNode = parseRootNode(orbitMap)
    val directOrbits = countDirectOrbits(rootNode)
    val indirectOrbits = countIndirectOrbits(rootNode)
    return directOrbits + indirectOrbits
}

data class Node(val id: String, var parent: Node? = null, val children: MutableList<Node> = mutableListOf()) {
    fun allChildren(): List<Node> = children + children.flatMap { it.allChildren() }

    fun pathToParent(parentId: String, path: List<Node> = emptyList()): List<Node> {
        return if (id == parentId) {
            path
        } else {
            parent?.pathToParent(parentId, path + this) ?: path
        }
    }

    override fun toString() = "Node(id=$id, parent=${parent?.id}, children=$children)"
}

fun parseRootNode(orbitMap: List<String>): Node {
    @Suppress("ReplaceGetOrSet")
    return orbitMap
        .map { it.split(")") }
        .fold(mutableMapOf<String, Node>()) { nodes, ids ->
            val parentNode = nodes.compute(ids.first()) { id, node -> node ?: Node(id) }!!
            val childNode = nodes.compute(ids.last()) { id, node -> node ?: Node(id) }!!
            parentNode.children.add(childNode)
            childNode.parent = parentNode
            nodes
        }
        .get("COM")
        ?: throw IllegalStateException("Could not parse root node")
}

fun countDirectOrbits(rootNode: Node) = rootNode.allChildren().size

fun countIndirectOrbits(rootNode: Node): Int {
    return rootNode.allChildren().sumBy { node ->
        node.pathToParent(rootNode.id).size - 1
    }
}
