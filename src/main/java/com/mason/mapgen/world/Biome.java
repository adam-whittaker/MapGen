package com.mason.mapgen.world;

public enum Biome{

    UNSET(false),
    OCEAN(false),
    LAKE(false),
    UNSET_LAND(true),
    SCORCHED(true),
    BARE(true),
    TUNDRA(true),
    SNOW(true),
    TEMPERATE_DESERT(true),
    SHRUBLAND(true),
    TAIGA(true),
    GRASSLAND(true),
    TEMPERATE_DECIDUOUS_FOREST(true),
    TEMPERATE_RAINFOREST(true),
    SUBTROPICAL_DESERT(true),
    TROPICAL_SEASONAL_FOREST(true),
    TROPICAL_RAIN_FOREST(true),
    RIVER(false);


    private final boolean land;


    Biome(boolean land){
        this.land = land;
    }


    public boolean isLand(){
        return land;
    }

}
