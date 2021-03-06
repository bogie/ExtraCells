package extracells.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import extracells.Extracells;
import extracells.tile.TileEntityCertusTank;

public class BlockCertusTank extends BlockContainer
{

	public BlockCertusTank(int id)
	{
		super(id, Material.glass);
		setCreativeTab(Extracells.ModTab);
		this.setUnlocalizedName("block.certustank");
		this.setHardness(2.0F);
		this.setResistance(10.0F);
		setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 1.0F, 0.9375F);
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
	{
		return getDropWithNBT(world, x, y, z);
	}

	@Override
	public boolean onBlockActivated(World worldObj, int x, int y, int z, EntityPlayer entityplayer, int blockID, float offsetX, float offsetY, float offsetZ)
	{
		ItemStack current = entityplayer.inventory.getCurrentItem();

		if (entityplayer.isSneaking() && current == null)
		{
			dropBlockAsItem_do(worldObj, x, y, z, getDropWithNBT(worldObj, x, y, z));
			worldObj.destroyBlock(x, y, z, false);
			return true;
		}
		if (current != null)
		{
			FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(current);
			TileEntityCertusTank tank = (TileEntityCertusTank) worldObj.getBlockTileEntity(x, y, z);

			if (liquid != null)
			{
				int amountFilled = tank.fill(ForgeDirection.UNKNOWN, liquid, true);

				if (amountFilled != 0 && !entityplayer.capabilities.isCreativeMode)
				{
					if (current.stackSize > 1)
					{
						entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem].stackSize -= 1;
						entityplayer.inventory.addItemStackToInventory(current.getItem().getContainerItemStack(current));
					} else
					{
						entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = current.getItem().getContainerItemStack(current);
					}
				}

				return true;

				// Handle empty containers
			} else
			{

				FluidStack available = tank.getTankInfo(ForgeDirection.UNKNOWN)[0].fluid;
				if (available != null)
				{
					ItemStack filled = FluidContainerRegistry.fillFluidContainer(available, current);

					liquid = FluidContainerRegistry.getFluidForFilledItem(filled);

					if (liquid != null)
					{
						if (!entityplayer.capabilities.isCreativeMode)
						{
							if (current.stackSize > 1)
							{
								if (!entityplayer.inventory.addItemStackToInventory(filled))
								{
									tank.fill(ForgeDirection.UNKNOWN, new FluidStack(liquid, liquid.amount), true);
									return false;
								} else
								{
									entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem].stackSize -= 1;
								}
							} else
							{
								entityplayer.inventory.mainInventory[entityplayer.inventory.currentItem] = filled;
							}
						}
						tank.drain(ForgeDirection.UNKNOWN, liquid.amount, true);
						return true;
					}
				}
			}
		}

		return false;
	}

	public ItemStack getDropWithNBT(World world, int x, int y, int z)
	{
		NBTTagCompound tileEntity = new NBTTagCompound();
		TileEntity worldTE = world.getBlockTileEntity(x, y, z);
		if (worldTE != null && worldTE instanceof TileEntityCertusTank)
		{
			ItemStack dropStack = new ItemStack(Extracells.CertusTank, 1);

			((TileEntityCertusTank) worldTE).writeToNBTWithoutCoords(tileEntity);

			dropStack.setTagCompound(new NBTTagCompound());
			dropStack.stackTagCompound.setCompoundTag("tileEntity", tileEntity);
			return dropStack;

		}
		return null;
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntityCertusTank();
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public int getRenderType()
	{
		return -1;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
}
