package crazypants.enderio.machine.invpanel;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;

import org.apache.commons.lang3.tuple.Pair;

import crazypants.enderio.machine.AbstractMachineBlock;
import crazypants.enderio.machine.AbstractMachineEntity;
import crazypants.enderio.machine.IoMode;
import crazypants.enderio.machine.MachineRenderMapper;
import crazypants.enderio.render.EnumRenderMode6;
import crazypants.enderio.render.IOMode.EnumIOMode;

public class InvPanelRenderMapper extends MachineRenderMapper {

  public static final MachineRenderMapper instance = new InvPanelRenderMapper();

  public InvPanelRenderMapper() {
    super(null);
  }

  @Override
  protected List<IBlockState> render(IBlockState state, IBlockAccess world, BlockPos pos, AbstractMachineEntity tileEntity, AbstractMachineBlock block) {
    List<IBlockState> states = new ArrayList<IBlockState>();

    EnumFacing facing = tileEntity.getFacing();
    boolean active = tileEntity.isActive();

    if (active) {
      states.add(state.withProperty(EnumRenderMode6.RENDER, EnumRenderMode6.FRONT_ON.rotate(facing)));
    } else {
      states.add(state.withProperty(EnumRenderMode6.RENDER, EnumRenderMode6.FRONT.rotate(facing)));
    }

    return states;
  }

  @Override
  protected EnumMap<EnumFacing, EnumIOMode> renderIO(@Nonnull AbstractMachineEntity tileEntity, @Nonnull AbstractMachineBlock block) {
    EnumMap<EnumFacing, EnumIOMode> result = new EnumMap<EnumFacing, EnumIOMode>(EnumFacing.class);
    EnumFacing face = tileEntity.getFacing().getOpposite();
    IoMode ioMode = tileEntity.getIoMode(face);
    if (ioMode != IoMode.NONE) {
      result.put(face, block.mapIOMode(ioMode, face));
      return result;
    }
    return null;
  }

  @Override
  public Pair<List<IBlockState>, List<IBakedModel>> mapItemRender(Block block, ItemStack stack) {
    List<IBlockState> states = new ArrayList<IBlockState>();
    states.add(block.getStateFromMeta(stack.getMetadata()).withProperty(EnumRenderMode6.RENDER, EnumRenderMode6.FRONT_ON_NORTH));
    return Pair.of(states, null);
  }


}
