package openccsensors.common.sensors.targets;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.LiquidStack;
import openccsensors.common.api.ISensorTarget;
import openccsensors.common.helper.InventoryHelper;

public class TankTarget implements ISensorTarget {

	private ILiquidTank[] tanks = null;
	private int relativeX;
	private int relativeY;
	private int relativeZ;
	private String rawName;
	private String displayName;
	
	public TankTarget(ILiquidTank[] tanks, String rawName, String displayName, int relativeX, int relativeY,
			int relativeZ) {
		this.tanks = tanks;
		this.relativeX = relativeX;
		this.relativeY = relativeY;
		this.relativeZ = relativeZ;
		this.rawName = rawName;
		this.displayName = displayName;
	}

	@Override
	public HashMap getExtendedDetails(World world) {
		HashMap retMap = getBasicDetails(world);
		LiquidStack stack;
		ItemStack istack;
		Item item;
		Map tankProperties;
		Map tankMap = new HashMap();
		int i = 1;
		try {
			if (tanks != null) {
				for (ILiquidTank tank : tanks) {
					tankProperties = new HashMap();
					tankProperties.put("Capacity", tank.getCapacity());
		
					stack = tank.getLiquid();
		
					if (stack != null) {
						istack = stack.asItemStack();
						if (istack != null) {
							if (istack.getItem() != null) {
								tankProperties.put("Name", InventoryHelper.getNameForItemStack(istack));
								tankProperties.put("RawName", InventoryHelper.getRawNameForStack(istack));
								tankProperties.put("Amount", stack.amount);
							}
						}
					}
					if (!tankProperties.containsKey("Amount")) {
						tankProperties.put("Amount", 0);
					}
		
					tankMap.put(i, tankProperties);
					i++;
				}
			}
		}catch(Exception e) {}
		retMap.put("Tanks", tankMap);
		return retMap;
	}

	@Override
	public String[] getTrackablePropertyNames() {
		return null;
	}

	@Override
	public HashMap getBasicDetails(World world) {
		HashMap retMap = new HashMap();
		HashMap<String, Integer> pos = new HashMap<String, Integer>();
		pos.put("X", relativeX);
		pos.put("Y", relativeY);
		pos.put("Z", relativeZ);

		retMap.put("RawName", this.rawName);
		retMap.put("Name", this.displayName);
		retMap.put("Position", pos);
		return retMap;
	}


}
