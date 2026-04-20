# BlockStateLib

## Introduction

This is a library for handling block state IDs according to the Minecraft protocol. This lib is mean to
get [Block State](https://minecraft.wiki/w/Java_Edition_protocol/Entity_metadata#Metadata_type:Block_State).

## Usage

To use this lib,you can download the JAR as a dependency.[Releases](https://github.com/lonlf/BlockStateLib/releases).  
Or use the repository as a dependency [here](https://jitpack.io/#lonlf/BlockStateLib).

### Example

```JAVA
public class TestExample {
    public static void main(String[] args) {
        // 1. Initialize BlockStateLib. Need version of your server.
        BlockStateLib.init(Version.V1_21_6);//e.g., 1.21.6

        // Or load JSON from file.
        // BlockStateLib.initFromFile("block_states.json");

        // 2. Build metadata.(Using default block state of "acacia_door")
        //You can find states here. => https://minecraft.wiki/w/Block_states
        BlockStateMeta meta = BlockStateMeta.builder()
                .addProperty("facing", "north")
                .addProperty("open", "false")
                .addProperty("half", "lower")
                .addProperty("hinge", "left")
                .addProperty("powered", "false")
                .build();

        //Empty metadata. This makes default block state id returned.
        BlockStateMeta emptyMeta = BlockStateMeta.builder().build();

        // 3. Get block state id.
        int stateId = BlockStateLib.getStateId("acacia_door", meta);
        System.out.println("ID of 'acacia_door':\nState ID: " + stateId);

        // 4. Use map rather than builder().
        Map<String, String> properties = Map.of(
                "facing", "north",
                "open", "false"
        );
        //Case insensitive.
        int stateId2 = BlockStateLib.getStateId("ACACIA_DOOR", properties);
        System.out.println("Another state ID: " + stateId2);

        int stateId3 = BlockStateLib.getStateId("ACACIA_DOOR", emptyMeta);
        System.out.println("Default state ID: " + stateId3);

        // 5. Get more information.
        var result = BlockStateLib.getInstance().getStateResult("acacia_door", meta);
        System.out.println("Result: " + result);
    }
}
```

Example output: ↓

```txt
ID of 'acacia_door':
State ID: 12984
Another state ID: 12975
Default state ID: 12984
Result: BlockStateResult{stateId=12984, isDefault=true, normalizedBlockName='minecraft:acacia_door', appliedMeta=BlockStateMeta{{hinge=left, facing=north, half=lower, powered=false, open=false}}}
```