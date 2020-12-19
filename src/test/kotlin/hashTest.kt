import com.soywiz.korio.util.toByteArray
import kotlinx.coroutines.runBlocking
import me.bristermitten.minekraft.data.NBT
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.ByteBuffer

class Tests {

    @Test
    fun hashTest() {

        val overworldNBT = NBT(
            "", mapOf(
                "name" to "minecraft:overworld",
                "id" to 0,
                "element" to NBT(
                    "", mapOf(
                        "piglin_safe" to 0.toByte(),
                        "natural" to 1.toByte(),
                        "ambient_light" to 0.0f,
                        "infiniburn" to "minecraft:infiniburn_overworld",
                        "respawn_anchor_works" to 0.toByte(),
                        "has_skylight" to 1.toByte(),
                        "bed_works" to 1.toByte(),
                        "has_raids" to 1.toByte(),
                        "name" to "minecraft:overworld",
                        "logical_height" to 256,
                        "coordinate_scale" to 1,
                        "ultrawarm" to 0.toByte(),
                        "has_ceiling" to 0.toByte()
                    )
                )
            )
        )

        val dimensions = NBT(
            "", mapOf(
                "type" to "minecraft:dimension_type",
                "value" to listOf(overworldNBT)
            )
        )

        val biomes = NBT(
            "", mapOf(
                "type" to "minecraft:worldgen/biome",
                "value" to listOf(
                    NBT(
                        "", mapOf(
                            "name" to "minecraft:plains",
                            "id" to 0,
                            "element" to NBT(
                                "",
                                mapOf(
                                    "precipitation" to "rain",
                                    "depth" to 0.125f,
                                    "temperature" to 0.8f,
                                    "scale" to 0.05f,
                                    "downfall" to 0.4,
                                    "category" to "none",
                                    "effects" to NBT(
                                        "", mapOf(
                                            "sky_color" to 7907327,
                                            "water_fog_color" to 329011,
                                            "fog_color" to 12638463,
                                            "water_color" to 4159204,
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            )
        )
        val nbt = NBT(
            "",
            mapOf(
                "minecraft:dimension_type" to dimensions,
                "minecraft:worldgen/biome" to biomes
            ),
        )

        val buf = ByteBuffer.allocateDirect(nbt.getNBTSize())
        runBlocking {
            val serialized = nbt.push(buf)
            buf.rewind()
            val pulled = NBT.pull(buf)
            buf.rewind()
            File("/home/alex/lol.nbt").writeBytes(buf.toByteArray())
            assert(nbt == pulled)
        }
    }
}
