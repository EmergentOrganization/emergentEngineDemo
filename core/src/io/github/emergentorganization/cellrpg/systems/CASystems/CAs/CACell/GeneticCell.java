package io.github.emergentorganization.cellrpg.systems.CASystems.CAs.CACell;

import com.badlogic.gdx.graphics.Color;
import io.github.emergentorganization.cellrpg.systems.CASystems.GeneticCells.DGRN4j.DGRN;
import io.github.emergentorganization.cellrpg.systems.CASystems.GeneticCells.DGRN4j.InflowNodeHandler;
import io.github.emergentorganization.cellrpg.systems.CASystems.GeneticCells.DGRN4j.OutflowNodeHandler;
import io.github.emergentorganization.cellrpg.systems.CASystems.GeneticCells.GeneticCellBuilders.GeneticNetworkBuilderInterface;
import it.uniroma1.dis.wsngroup.gexf4j.core.data.Attribute;
import it.uniroma1.dis.wsngroup.gexf4j.core.data.AttributeClass;
import it.uniroma1.dis.wsngroup.gexf4j.core.data.AttributeList;
import it.uniroma1.dis.wsngroup.gexf4j.core.data.AttributeType;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.data.AttributeListImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.crypto.KeySelectorException;
import java.util.ArrayList;

/**
 * CA grid cell which has a digital gene regulatory network (DGRN) to represent it's genome,
 * and traits which are extracted from this genome.
 */
public class GeneticCell extends BaseCell implements OutflowNodeHandler, InflowNodeHandler {
    static final Color DEFAULT_COLOR = new Color(.4f, .4f, .4f, .9f);
    private static final Logger logger = LogManager.getLogger(GeneticCell.class);
    private static AttributeList attrList = new AttributeListImpl(AttributeClass.NODE);
    public static Attribute attr_ActivationValue = attrList.createAttribute(
            nodeAttribute.ACTIVATION_VALUE,
            AttributeType.INTEGER,
            "activation value"
    ).setDefaultValue("0");  // NOTE: default value doesn't seem to have intended effect?
    public DGRN dgrn;
    public int neighborCount = 0;
    private GeneticNetworkBuilderInterface builder;
    private Color color = new Color(DEFAULT_COLOR);
    public GeneticCell(int _state, ArrayList<GeneticCell> parents, int mutateLevel) {
        // builds cell network inherting from parent(s), mutated based on given level
        super(_state);
        initDGRN();
        // TODO: inherit + mutation
    }
    public GeneticCell(int _state, ArrayList<GeneticCell> parents) {
        // builds cell network inherting from parent(s) (no mutations)
        super(_state);
        initDGRN();

        // inherit network from parents
        if (parents.size() > 1) {
            logger.debug("cell inheriting from 2 parents");
            // choose 2 parents at random
            int p1 = dgrn.randomGenerator.nextInt(parents.size());
            int p2 = dgrn.randomGenerator.nextInt(parents.size());
            while (p2 == p1) p2 = dgrn.randomGenerator.nextInt(parents.size());
            // for 1st parent
            dgrn.inheritGenes(parents.get(p1).dgrn, 2);
            dgrn.inheritGenes(parents.get(p2).dgrn, 2);
        } else {
            // not enough parents, cells are being manually inserted
            throw new IllegalStateException("not enough parents to inherit");
        }
    }

    public GeneticCell(int _state, GeneticNetworkBuilderInterface _builder) {
        // builds preset network using given builder.
        super(_state);
        initDGRN();
        builder = _builder;
        logger.trace("building seed cell network");
        builder.buildNetwork(dgrn);
    }
    // active state of a node defines if gene is expressed (and how much (in some cases))

    public GeneticCell incubate() {
        // used to grow the cells a bit before releasing into environment
        dgrn.tick();
        dgrn.tick();
        dgrn.tick();
        return this;
    }

    public void initDGRN() {
        dgrn = new DGRN(
                "Planiverse Bridge ",  // TODO: add back version : v" + CellRpg.fetch().getVersion(),
                "Digital Gene Regulatory Network",
                attrList,
                attr_ActivationValue,
                this,
                this
        );
    }

    public Color getColor() {
        return color;
    }

    public String[] getListOfInflowNodes() {
        return inflowNodes.values();
    }

    public int getInflowNodeValue(String key) throws KeySelectorException {
        int TRUE = 9999;
        int FALSE = 0;
        if (key == inflowNodes.ALWAYS_ON) {
            return TRUE;
        } else if (key == inflowNodes.NEIGHBOR_COUNT) {
            return neighborCount;
        } else if (key == inflowNodes.CROWDED) {
            if (neighborCount > 2) {
                return TRUE;
            } else {
                return FALSE;
            }
        } else if (key == inflowNodes.LONELY) {
            if (neighborCount < 3) {
                return TRUE;
            } else {
                return FALSE;
            }
        } else {
            throw new KeySelectorException("inflow node '" + key + "' not recognized");
        }
    }

    public String[] getListOfOutflowNodes() {
        return outflowNodes.values();
    }

    public void handleOutputNode(String key, int value) {
        // handles special actions caused by outflow nodes
        //logger.info("taking action " + key + "(" + value + ")");
        final float COLOR_DELTA = .1f * value;
        final float tooDark = .2f;
        final float tooLight = .9f;
        if (key == outflowNodes.COLOR_LIGHTEN) {
            if (color.r < tooLight && color.g < tooLight && color.b < tooLight)
                color.add(COLOR_DELTA, COLOR_DELTA, COLOR_DELTA, 0);
        } else if (key == outflowNodes.COLOR_DARKEN) {
            if (color.r > tooDark && color.g > tooDark && color.b > tooDark)
                color.sub(COLOR_DELTA, COLOR_DELTA, COLOR_DELTA, 0);
        } else if (key == outflowNodes.COLOR_ADD_R) {
            if (color.r < tooLight)
                color.add(COLOR_DELTA, 0, 0, 0);
        } else if (key == outflowNodes.COLOR_ADD_G) {
            if (color.g < tooLight)
                color.add(0, COLOR_DELTA, 0, 0);
        } else if (key == outflowNodes.COLOR_ADD_B) {
            if (color.b < tooLight)
                color.add(0, 0, COLOR_DELTA, 0);
        } else if (key == outflowNodes.COLOR_SUB_R) {
            if (color.r < tooDark)
                color.sub(COLOR_DELTA, 0, 0, 0);
        } else if (key == outflowNodes.COLOR_SUB_G) {
            if (color.g < tooDark)
                color.sub(0, COLOR_DELTA, 0, 0);
        } else if (key == outflowNodes.COLOR_SUB_B) {
            if (color.b < tooDark)
                color.sub(0, 0, COLOR_DELTA, 0);
        } else { // not an output key
            return;  // do nothing
            //throw new KeySelectorException("inflow node '" + key + "' not recognized");
        }
    }

    public static class inflowNodes {
        public static final String ALWAYS_ON = "alwaysOn";
        public static final String NEIGHBOR_COUNT = "# of neighbors";
        public static final String CROWDED = "is_crowded";
        public static final String LONELY = "is_lonely";

        public static String[] values() {
            return new String[]{ALWAYS_ON, NEIGHBOR_COUNT, CROWDED, LONELY};
        }
    }

    public static class outflowNodes {
        public static final String COLOR_LIGHTEN = "COLOR_LIGHTEN";
        public static final String COLOR_DARKEN = "COLOR_DARKEN";
        public static final String COLOR_ADD_R = "COLOR_ADD_R";
        public static final String COLOR_ADD_G = "COLOR_ADD_G";
        public static final String COLOR_ADD_B = "COLOR_ADD_B";
        public static final String COLOR_SUB_R = "COLOR_SUB_R";
        public static final String COLOR_SUB_G = "COLOR_SUB_G";
        public static final String COLOR_SUB_B = "COLOR_SUB_B";

        public static String[] values() {
            return new String[]{
                    COLOR_LIGHTEN, COLOR_DARKEN,
                    COLOR_ADD_R, COLOR_ADD_G, COLOR_ADD_B,
                    COLOR_SUB_R, COLOR_SUB_G, COLOR_SUB_B
            };
        }
    }

    public static class nodeAttribute {
        public static final String ACTIVATION_VALUE = "activation level";
    }

}
