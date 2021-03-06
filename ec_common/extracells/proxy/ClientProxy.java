package extracells.proxy;

import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import extracells.Extracells;
import extracells.model.render.item.ItemRendererBusFluidExport;
import extracells.model.render.item.ItemRendererBusFluidImport;
import extracells.model.render.item.ItemRendererBusFluidStorage;
import extracells.model.render.item.ItemRendererCertusTank;
import extracells.model.render.item.ItemRendererSolderingStation;
import extracells.model.render.tileentity.TileEntityRendererBusFluidExport;
import extracells.model.render.tileentity.TileEntityRendererBusFluidImport;
import extracells.model.render.tileentity.TileEntityRendererBusFluidStorage;
import extracells.model.render.tileentity.TileEntityRendererCertusTank;
import extracells.model.render.tileentity.TileEntityRendererSolderingStation;
import extracells.tile.TileEntityBusFluidExport;
import extracells.tile.TileEntityBusFluidImport;
import extracells.tile.TileEntityBusFluidStorage;
import extracells.tile.TileEntityCertusTank;
import extracells.tile.TileEntitySolderingStation;

public class ClientProxy extends CommonProxy
{
	public void RegisterRenderers()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySolderingStation.class, new TileEntityRendererSolderingStation());
		MinecraftForgeClient.registerItemRenderer(Extracells.SolderingStation.blockID, new ItemRendererSolderingStation());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBusFluidStorage.class, new TileEntityRendererBusFluidStorage());
		MinecraftForgeClient.registerItemRenderer(Extracells.BusFluidStorage.blockID, new ItemRendererBusFluidStorage());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBusFluidImport.class, new TileEntityRendererBusFluidImport());
		MinecraftForgeClient.registerItemRenderer(Extracells.BusFluidImport.blockID, new ItemRendererBusFluidImport());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBusFluidExport.class, new TileEntityRendererBusFluidExport());
		MinecraftForgeClient.registerItemRenderer(Extracells.BusFluidExport.blockID, new ItemRendererBusFluidExport());

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCertusTank.class, new TileEntityRendererCertusTank());
		MinecraftForgeClient.registerItemRenderer(Extracells.CertusTank.blockID, new ItemRendererCertusTank());
	}
}