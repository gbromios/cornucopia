package com.gb.cornucopia.bees.apiary;

import com.gb.cornucopia.bees.Bees;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockDoublePlant.EnumPlantType;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Random;

public class TileEntityApiary extends TileEntity implements ITickable {
	public static final int TICK_PERIOD = 20 * 30; //tick 2x per minute, because it might be kind of expensive >__>
	public static final Random RANDOM = new Random();

	// 0 = queen slot, 1 = worker slot, 2-8 are the honeycombs*/
	public ItemStackHandler inventory = new ItemStackHandler(9);

	private int ticks = 0;
	private ArrayList<IBlockState> flower_survey = new ArrayList<>();
	private int flower_score = 0;

	//System.out.format("\n");

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T)inventory : super.getCapability(capability, facing);
	}
	@Override
	public void update() {
		if (!this.hasWorld()) return;
		final World world = this.getWorld();
		if (world.isRemote) return;

		if (this.ticks++ < TICK_PERIOD) {
			return;
		}

		// time to actually do the tick!
		this.ticks = 0;
		//System.out.format("\n   ===   TICKING!   ===\n");

		// no bees???? nothing I can do, slot 1 is bees
		if (inventory.getStackInSlot(1).isEmpty()) {
			return;
		}
		flowerSurvey();

		// hungry bees? no soup for u
		if (this.feedBees()) {
			produce();
			cloneFlower();
		}
		this.markDirty();
	}

	private void produce() {
		// 75%~ chance to produce with max bees and max flowers!
		final int fs = Math.min(this.flower_score, 32);
		if (fs == 0) {
			return;
		} // need at least one flower!
		int grow_chance = (this.beeCount() + fs) / (1 + (this.getNeighboringApiaries() * 2));
		//System.out.format(" @ produce bee gifts (%d / 128)\n ~ ( %d bees + %d flowers ) / (%d neighbors * 2)\n", grow_chance, this.beeCount(), fs, this.getNeighboringApiaries());
		if (RANDOM.nextInt(128) < grow_chance) {
			// look for empties first:
			for (int i = 2; i < 9; i++) {
				if (inventory.getStackInSlot(i).isEmpty()) {
					inventory.setStackInSlot(i, new ItemStack(Bees.waxcomb));
					//System.out.format("    make a wax\n");
					return;
				}
			}

			// then look for plain wax:
			for (int i = 2; i < 9; i++) {
				if (inventory.getStackInSlot(i).getItem() == Bees.waxcomb) {
					inventory.setStackInSlot(i, new ItemStack(Bees.honeycomb));
					//System.out.format("    make a honey comb\n");
					return;
				}
			}

			// once the hive is full of honey, 
			// if there's a queen in the queen slot and slot 6 is not already jelly
			// AND you've got a royal flower AAAND you're really lucky...
			if (
					inventory.getStackInSlot(6).getItem() != Bees.royal_jelly
							&& this.beeCount() == 64
							&& this.nearRoyalBloom()
							&& RANDOM.nextInt(64) == 0) {
				// can happen without a queen, but it's super rare!
				if (this.hasQueen() || RANDOM.nextInt(32) == 0) {
					// royal jelly comes at a heavy price though. it uses up most of the honey, and the bee population will suffer
					for (int i : new int[]{2, 3, 4, 5, 7, 8}) { // outer comb slots
						inventory.getStackInSlot(i).setCount(0);

					}
					inventory.setStackInSlot(6, new ItemStack(Bees.royal_jelly));
					inventory.getStackInSlot(1).setCount(8);
				}
			}
		}
	}

	private int getNeighboringApiaries() {
		// remember to leave 4 blocks of space between your apiaries!
		int n = 0;
		for (int x = -4; x < 5; x++) {
			for (int z = -4; z < 5; z++) {
				for (int y = -1; y < 2; y++) {
					if (this.getWorld().getBlockState(this.pos.add(x, y, z)).getBlock() == Bees.apiary) {
						n++;
					}
				}
			}
		}
		return n - 1;
	}

	private boolean feedBees() {
		final int food = this.flower_score - (this.beeCount() / 4);
		//System.out.format("%d food\n", food);

		final ItemStack workers = inventory.getStackInSlot(1);

		if (food < -4) {
			workers.shrink(1);
			if (workers.getCount() < 8) {
				workers.setCount(8);
			}
			return false;
		} else if (food > 4 && this.hasQueen() && RANDOM.nextInt((12 - Math.min(food, 11)) * (1 + (2 * this.getNeighboringApiaries()))) == 0) {
			//System.out.format("   bees grew! :)\n");
			workers.setCount(Math.min(workers.getCount() + 1, workers.getMaxStackSize()));
		}
		return true;
	}

	private boolean nearRoyalBloom() {
		for (int x = -2; x < 3; x++) {
			for (int z = -2; z < 3; z++) {
				if (this.getWorld().getBlockState(this.pos.add(x, 0, z)).getBlock() == Bees.royal_bloom) {
					return true;
				}
			}
		}
		return false;
	}

	private void cloneFlower() {
		// pick a random block near the apiary.
		final BlockPos fpos = this.pos.add(RANDOM.nextInt(19) - 9, RANDOM.nextInt(3) - 1, RANDOM.nextInt(19) - 9);

		double d = fpos.distanceSq(pos.getX(), pos.getY(), pos.getZ()) + 10.0;

		double rs = RANDOM.nextDouble() * d;
		//System.out.format("%s => %s : (%s / %s)\n",pos, fpos, rs, d);
		if (rs < 2.0) {

			if ((this.world.isAirBlock(fpos) || this.world.getBlockState(fpos).getBlock() == Blocks.TALLGRASS) && this.world.getBlockState(fpos.down()).getBlock() == Blocks.GRASS && RANDOM.nextInt(80) < this.beeCount()) {
				// stop growing if there are 48 flowers in the vicinity (don't grow at all if there's no flowwers :()
				if (this.flower_score > 48 || this.flower_survey.size() < 1) {
					return;
				}
				IBlockState b = this.flower_survey.get(RANDOM.nextInt(this.flower_survey.size()));
				//public void placeAt(World worldIn, BlockPos lowerPos, BlockDoublePlant.EnumPlantType variant, int flags)

				this.world.setBlockState(fpos, b, 0);
				if (b.getBlock() instanceof BlockDoublePlant) {
					Blocks.DOUBLE_PLANT.placeAt(world, fpos, (EnumPlantType) b.getValue(BlockDoublePlant.VARIANT), 2);
				}
				this.world.notifyBlockUpdate(fpos, this.world.getBlockState(fpos), this.world.getBlockState(fpos), 3);
			}

			// royal flower!
			final BlockPos rpos = this.pos.add(RANDOM.nextInt(5) - 2, 0, RANDOM.nextInt(5) - 2);
			if (this.beeCount() == 64 && this.flower_score > 16 && !this.nearRoyalBloom() && RANDOM.nextInt(this.hasQueen() ? 512 : 1024) == 0) {
				if ((this.world.isAirBlock(rpos) || this.world.getBlockState(rpos).getBlock() == Blocks.TALLGRASS || this.world.getBlockState(rpos).getBlock() instanceof BlockFlower) && this.world.getBlockState(rpos.down()).getBlock() == Blocks.GRASS) {
					this.world.setBlockState(rpos, Bees.royal_bloom.getDefaultState(), 0);
					this.world.notifyBlockUpdate(fpos, this.world.getBlockState(fpos), this.world.getBlockState(fpos), 3);
				}
			}
		}
	}

	private void flowerSurvey() {
		this.flower_survey.clear();
		this.flower_score = this.flowerSample(this.pos, 7, true);
	}

	private int flowerSample(final BlockPos pos, final int r, final boolean record_states) {
		// sample a radius * radius area around the apiary N times, return the number of flowers found
		int density = 0;
		for (int z = -r; z <= r; z++) {
			for (int x = -r; x <= r; x++) {
				for (int y = -1; y <= 1; y++) {
					// pollenate-able?
					BlockPos fpos = pos.add(x, y, z);
					IBlockState bs = this.getWorld().getBlockState(fpos);
					if (bs.getBlock() instanceof BlockFlower || bs.getBlock() instanceof BlockDoublePlant) {
						density++;
						// this list could potentially slow the server down, searching a 15x15 area...
						if (record_states) {
							this.flower_survey.add(bs);
						}
						// stop counting after a while?
					}
				}

			}
		}
		return density;
	}
	private boolean hasQueen() {
		// theoretically slot permissions means we don't need to check the item, slot 0 is queen
		return !inventory.getStackInSlot(0).isEmpty();
	}

	private int beeCount() {
		return inventory.getStackInSlot(1).getCount();
	}

	@Override
	public void onDataPacket(final NetworkManager net, final SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}

	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("inventory", inventory.serializeNBT());
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		super.readFromNBT(compound);
	}

}
