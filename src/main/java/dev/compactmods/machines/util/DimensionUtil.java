package dev.compactmods.machines.util;

import com.mojang.serialization.JsonOps;
import dev.compactmods.machines.CompactMachines;
import dev.compactmods.machines.api.dimension.CompactDimension;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.io.IOException;
import java.nio.file.Files;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DimensionUtil {

    @SuppressWarnings("deprecation") // because we call the forge internal method server#markWorldsDirty
    public static void createAndRegisterWorldAndDimension(final MinecraftServer server) {
        final var map = server.forgeGetWorldMap();

        // get everything we need to create the dimension and the dimension
        final ServerLevel overworld = server.getLevel(Level.OVERWORLD);

        // dimension keys have a 1:1 relationship with dimension keys, they have the same IDs as well
        final ResourceKey<LevelStem> dimensionKey = ResourceKey.create(Registries.LEVEL_STEM, CompactDimension.LEVEL_KEY.location());

        final var serverResources = server.getResourceManager();

        // only back up dimension.dat in production
        if (FMLEnvironment.production && !doLevelFileBackup(server)) return;

        var reg = server.registryAccess();
        var cmDimType = reg.registryOrThrow(Registries.DIMENSION_TYPE)
                .get(CompactDimension.DIM_TYPE_KEY);

        var ops = RegistryOps.create(JsonOps.INSTANCE, reg);
        
//        var resourceAccess = RegistryResourceAccess.forResourceManager(serverResources);
//        var dims = resourceAccess.listResources(Registries.DIMENSION);

        // TODO - Revisit
//        resourceAccess.getResource(Registration.COMPACT_DIMENSION).ifPresent(lev -> {
//            var parsed = lev.parseElement(JsonOps.INSTANCE, LevelStem.CODEC);
//
//            var stem = parsed.result().orElseThrow().value();
//
//            // the int in create() here is radius of chunks to watch, 11 is what the server uses when it initializes worlds
//            final ChunkProgressListener chunkProgressListener = server.progressListenerFactory.create(11);
//            final Executor executor = server.executor;
//            final LevelStorageSource.LevelStorageAccess anvilConverter = server.storageSource;
//            final WorldData worldData = server.getWorldData();
//            final WorldGenSettings worldGenSettings = worldData.worldGenSettings();
//            final DerivedLevelData derivedLevelData = new DerivedLevelData(worldData, worldData.overworldData());
//
//            // now we have everything we need to create the dimension and the dimension
//            // this is the same order server init creates levels:
//            // the dimensions are already registered when levels are created, we'll do that first
//            // then instantiate dimension, add border listener, add to map, fire world load event
//
//            // register the actual dimension
//            if (worldGenSettings.dimensions() instanceof MappedRegistry<LevelStem> stems) {
//                stems.unfreeze();
//                Registry.register(stems, dimensionKey, stem);
//                stems.freeze();
//            } else {
//                CompactMachines.LOGGER.fatal("Failed to re-register compact machines dimension; registry was not the expected class type.");
//                return;
//            }
//
//            // create the world instance
//            final ServerLevel newWorld = new ServerLevel(
//                    server,
//                    executor,
//                    anvilConverter,
//                    derivedLevelData,
//                    Registration.COMPACT_DIMENSION,
//                    Holder.direct(cmDimType),
//                    chunkProgressListener,
//                    stem.generator(),
//                    worldGenSettings.isDebug(),
//                    net.minecraft.world.level.biome.BiomeManager.obfuscateSeed(worldGenSettings.seed()),
//                    ImmutableList.of(), // "special spawn list"
//                    false // "tick time", true for overworld, always false for nether, end, and json dimensions
//            );
//
//            /*
//             add world border listener, for parity with json dimensions
//             the vanilla behaviour is that world borders exist in every dimension simultaneously with the same size and position
//             these border listeners are automatically added to the overworld as worlds are loaded, so we should do that here too
//             TODO if world-specific world borders are ever added, change it here too
//            */
//            overworld.getWorldBorder().addListener(new BorderChangeListener.DelegateBorderChangeListener(newWorld.getWorldBorder()));
//
//            // register dimension
//            map.put(Registration.COMPACT_DIMENSION, newWorld);
//
//            // update forge's world cache so the new dimension can be ticked
//            server.markWorldsDirty();
//
//            // fire world load event
//            MinecraftForge.EVENT_BUS.post(new LevelEvent.Load(newWorld));
//        });
    }

    public static boolean doLevelFileBackup(MinecraftServer server) {
        var levelRoot = server.getWorldPath(LevelResource.ROOT);
        var levelFile = server.getWorldPath(LevelResource.LEVEL_DATA_FILE);

        var formatter = DateTimeFormatter.ofPattern("'cm4-dimension-'yyyyMMdd-HHmmss'.dat'");
        var timestamp = formatter.format(ZonedDateTime.now());
        try {
            Files.copy(levelFile, levelRoot.resolve(timestamp));
        } catch (IOException e) {
            CompactMachines.LOGGER.error("Failed to backup dimension.dat file before modification; canceling register dim attempt.");
            return false;
        }

        return true;
    }

}
