package io.github.emergentorganization.cellrpg.scenes.game.regions;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.TagManager;
import io.github.emergentorganization.cellrpg.components.CAGridComponents;
import io.github.emergentorganization.cellrpg.core.Tags;
import io.github.emergentorganization.cellrpg.scenes.Scene;
import io.github.emergentorganization.cellrpg.systems.CASystems.CAEdgeSpawnType;
import io.github.emergentorganization.cellrpg.systems.CASystems.layers.CALayer;


public class TheEdge implements iRegion {

    public TheEdge(Scene parentScene) {
        super();
    }

    public CALayer[] getCALayers() {
        return new CALayer[]{
                CALayer.ENERGY,
                CALayer.VYROIDS
        };
    }

    public void loadRegion(World world) {

    }

    public void enterRegion(World world) {
        System.out.println("entering the edge region");
        setCAEdgeSpawns(world);
    }

    private void setCAEdgeSpawns(World world) {
        TagManager tagMan = world.getSystem(TagManager.class);

        Entity CAEnt = tagMan.getEntity(Tags.CA_VYROIDS_STD);
        CAGridComponents CAComp = CAEnt.getComponent(CAGridComponents.class);
        CAComp.edgeSpawner = CAEdgeSpawnType.RANDOM_SPARSE;

        tagMan.getEntity(Tags.CA_VYROIDS_GENETIC).getComponent(CAGridComponents.class).edgeSpawner
                = CAEdgeSpawnType.RANDOM_50_50;
    }

    public iRegion getNextRegion(World world) {
        return null;  // TODO: return adjoining region when nearing boundary
    }
}
