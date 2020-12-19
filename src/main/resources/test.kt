import org.intellij.lang.annotations.Language

@Language("JSON")
val x = """{
  "type": "minecraft:dimension_type",
  "value": [
    {
      "name": "minecraft:overworld",
      "id": 0,
      "element": {
        "piglin_safe": 0,
        "natural": 1,
        "ambient_light": 0.0,
        "infiniburn": "minecraft:infiniburn_overworld",
        "respawn_anchor_works": 0,
        "has_skylight": 1,
        "bed_works": 1,
        "has_raids": 1,
        "name": "minecraft:overworld",
        "logical_height": 256,
        "coordinate_scale": 1,
        "ultrawarm": 0,
        "has_ceiling": 0
      }
    }
  ]
}
""".trimIndent()

@Language("JSON")
val y = """{
  "type": "minecraft:worldgen/biome",
  "value": [
    {
      "name": "minecraft:plains",
      "id": 0,
      "element": {
        "precipitation": "rain",
        "effects": {
          "sky_color": 7907327,
          "water_fog_color": 329011,
          "fog_color": 12638463,
          "water_color": 4159204
        },
        "temperature": 0.8,
        "scale": 0.05,
        "downfall": 0.4,
        "category": "none"
      }
    }
  ]
}
""".trimIndent()
