package com.gb.cornucopia;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class CMDReplant  implements ICommand{
	private static final Random RANDOM = new Random();

	@Override
	public int compareTo(final Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "repl";
	}

	@Override
	public String getCommandUsage(final ICommandSender sender) {
		// TODO Auto-generated method stub
		return " == repl, area replanting command ==\n == `/repl PLANT[#M] [TARGET [GROUND]] [RADIUS [DENSITY]`\n\n" +
		" desired plant - block name, meta defaults to 0, can set it like\n   '/repl tallgrass#1'\n" +
		" target block - block name, defaults to air (i.e. empty)\n" +
		" ground block - block name, defaults to grass. can only specify a ground block after a target block\n" +
		" radius - int r, search will occur within 2r x 2r square cenetered on user; defaults to r=16; takes a few minutes but i have done r=1000. would like to add a more circular search\ny is always searched from +2 to -20" +
		" density - ind d, permil of valid blocks that will be replaced, d:1000 odds. default d=1000, all matched bocks. \n" + 
		" EX: `/repl red_flower#5 air leaves 32 50` - plants orange tulips on half of the tree tops in a 64x22x64 block area";
	}

	@Override
	public List<?> getAliases() {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

	@Override
	public void execute(final ICommandSender sender, final String[] args) throws CommandException {
		World world = sender.getEntityWorld();
	//System.out.println("yes..");
		try {

			// parse out args!
			final int len = args.length;

			// should always be the plant we want to replant
			int desired_meta = 0;
			String desired_arg = args[0]; 
			if (desired_arg.contains("#")){
				// is meta value included?
				desired_meta = Integer.parseInt(desired_arg.split("#")[1]);
				desired_arg = desired_arg.split("#")[0];

			}
			final Block desired = Block.getBlockFromName(desired_arg);

			int next_arg = 1;
			Block target;
			Block ground;
			int radius;
			int density;

			// next arg may be a target block	
			if (len > next_arg && (!args[next_arg].matches("^[0-9]+f?$"))) {
				target = Block.getBlockFromName(args[next_arg]);
				next_arg++;
			} else {
				target = Blocks.air;
			}

			// when specifying a specific target, you can go one further and specify a ground block
			if (len > next_arg && (!args[next_arg].matches("^[.0-9]+f?$"))) {	
				ground = Block.getBlockFromName(args[next_arg]);
				next_arg++;
			} else {
				ground = Blocks.grass;
			}

			// now check for numbers
			if (len > next_arg) {
				// first, the radius
				radius = Integer.parseInt(args[next_arg]);
				next_arg++;
			} else {
				radius = 16;
			}

			if (len > next_arg){
				// lastly, density
				density = Integer.parseInt(args[next_arg]);
				next_arg++;
			} else {
				density = 1000;
			}

		//System.out.format(" replant '%s' => '%s#%s' - r%d, d%d\n\n", target, desired, desired_meta, radius, density);

			final BlockPos pos = sender.getPosition();
			final int miny = pos.getY() - 20; // make param
			final int maxy = pos.getY() + 2;
			final int minx = pos.getX() - radius;
			final int maxx = pos.getX() + radius;
			final int minz = pos.getZ() - radius;
			final int maxz = pos.getZ() + radius;



			// find the correct y for every position
			for (int x = minx; x <= maxx; x++){
				for (int z = minz; z <= maxz; z++){
					int y = maxy;
					while (y-- > miny) {
						BlockPos p = new BlockPos(x, y, z);
						//System.out.println(p);
						if (world.getBlockState(p).getBlock() == target && world.getBlockState(p.down()).getBlock() == ground) {
							//search down until we hit grass w/ the target block above
							if (RANDOM.nextInt(1000) - density < 1) {
								world.setBlockState(p, desired.getStateFromMeta(desired_meta));
							}
							break;
						}
					}
				}				
			}
		} catch(Exception e) {
			throw new CommandException("Usage: " +this.getCommandUsage(sender));
		}

	}

	@Override
	public boolean canCommandSenderUse(final ICommandSender sender) {
		return true;
	}

	@Override
	public List<?> addTabCompletionOptions(final ICommandSender sender, final String[] args, final BlockPos pos) {
		return null;
	}

	@Override
	public boolean isUsernameIndex(final String[] args, final int index) {
		return false;
	}

}
