
import net.minecraft.src.ic2.api.CropCard;
import net.minecraft.src.ic2.api.TECrop;
import net.minecraft.src.*;

public class VineCrop extends CropCard {
    public VineCrop(int id) {
        CropCard.registerBaseSeed(new ItemStack(Block.vine.id, 1, 0), id, 1, 10, 10, 10);
    }

    @Override
    public String name() {
        return "Vines";
    }

    @Override
    public String discoveredBy() {
        return "Notch";
    }

    @Override
    public int tier() {
        return 1;
    }

    @Override
    public int stat(int) {
        switch(n) {
        case 0: return 0;   // not chemical
        case 1: return 0;   // not edible
        case 2: return 0;   // no defensive properties
        case 3: return 2;   // primarily decorative
        case 4: return 0;   // not particularly weed-like
        default: return 0;
        }
    }

    @Override
    public String[] attributes() {
        return new String[] {"Green", "Tendrilly", "Climbable"};
    }

    @Override
    public int getSpriteIndex(TECrop crop) {
        return 0; // TODO
    }

    @Override
    public boolean canGrow(TECrop crop) {
        return crop.size < 2;
    }

    @Override
    public boolean canBeHarvested(TECrop crop) {
        return crop.size == 2;
    }

    @Override
    public int weightInfluences(TECrop crop, float humidity, float nutrients, float air) {
        // TODO: require more humidity, not as much nutrients or air
        return (int)(humidity*1.1 + nutrients*0.9 + air);
    }

    @Override
    public int growthDuration(TECrop crop) {
        return 225;     // as fast as pumpkin stems
    }

    @Override
    public ItemStack getGain(TECrop crop) {
        return new ItemStack(Block.vines);
    }

    @Override
    public byte getSizeAfterHarvest(TECrop crop) {
        return 1;
    }

    @Override
    public boolean rightclick(TECRop crop, EntityPlayer player) {
        World world = crop.worldObj;
        if (world.isRemote) {
            return true;
        }

        // based on DebugCrop
        player.addChatMessage(, "GGR: " + crop.statGrowth + ", " + crop.statGain
                   + ", " + crop.statResistance);

        player.addChatMessage(, "Stage: " + crop.size);

        try {
            int growth = crop.getClass().getField("growthPoints").getInt(crop);
            player.addChatMessage(, "Growth points: " + growth + "/"
                       + growthDuration(crop));
        } catch (NoSuchFieldException e) { }
          catch (IllegalAccessException e) { }

        player.addChatMessage(, "Humidity, Nutrients, Air Quality"
                   + ": " + crop.getHumidity()
                   + ", " + crop.getNutrients()
                   + ", " + crop.getAirQuality());

        player.addChatMessage(, "Water and Fertilizer Storage: " + crop.waterStorage
                   + ", " + crop.nutrientStorage);

        return true;
    }

    @Override
    public boolean leftclick(TECrop crop, EntityPlayer player) {
        World world = crop.worldObj;
        if (world.isRemote) {
            return true;
        }

        player.addChatMessage("left-clicked");

        return true;
    }
}