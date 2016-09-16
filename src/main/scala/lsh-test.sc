import info.debatty.java.lsh.{SuperBit, LSHSuperBit}

import scala.util.Random

def printArray(array: Array[Double]) = {
  array.foreach(x => print(f"$x%1.2f "))
}


val rand = new Random()


val n = 200
val buckets = 600
val stages = 20

val vectors = (1 to 10000).map(_ => (1 to n).map(_ => rand.nextGaussian()).toArray)



val lsh = new LSHSuperBit(stages, buckets, n)

val hashes = vectors.map(lsh.hash)

val vToSearch = (1 to n).map(_ => rand.nextGaussian()).toArray
val hashToSearch = lsh.hash(vToSearch)


val vectorsAndHash = vectors zip hashes

val filteredVectors = vectorsAndHash.filter(pair => {
  val (_, hash) = pair
  hash.zip(hashToSearch).map(pair => pair._1 == pair._2).reduce((x, y) => x || y)
})

val hashClosest = filteredVectors.maxBy(pair =>{
  val (array, _) = pair
  SuperBit.cosineSimilarity(array, vToSearch)
})

val globalClosest = vectorsAndHash.maxBy(pair =>{
  val (array, _) = pair
  SuperBit.cosineSimilarity(array, vToSearch)
})

filteredVectors.size
vectorsAndHash.size

printArray(vToSearch)

println("Hash closest:")
printArray(hashClosest._1)
println(SuperBit.cosineSimilarity(hashClosest._1, vToSearch).abs)
println("Global closest:")
printArray(globalClosest._1)
println(SuperBit.cosineSimilarity(globalClosest._1, vToSearch).abs)

