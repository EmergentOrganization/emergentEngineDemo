package io.github.emergentorganization.cellrpg.systems.CASystems.GeneticCells.GeneticCellBuilders;

import io.github.emergentorganization.cellrpg.systems.CASystems.CAs.CACell.GeneticCell;
import io.github.emergentorganization.cellrpg.systems.CASystems.GeneticCells.DGRN4j.DGRN;
import it.uniroma1.dis.wsngroup.gexf4j.core.Node;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.crypto.KeySelectorException;


public class MrRed implements GeneticNetworkBuilderInterface {
    private static final Logger logger = LogManager.getLogger(GeneticCell.class);

    public void buildNetwork(DGRN dgrn) {
        try {

            Node reddener = dgrn.graph.createNode("redden-crowded-gene");
            reddener.setLabel("redden-crowded-gene")
                    .getAttributeValues()
                    .addValue(GeneticCell.attr_ActivationValue, "0")
                    .addValue(DGRN.attr_AlleleCount, "2");
            dgrn.connect(GeneticCell.inflowNodes.ALWAYS_ON, reddener.getId(), 1);
            dgrn.connect(reddener.getId(), GeneticCell.outflowNodes.COLOR_ADD_R, 1);

            // this is instantaneous, but inheritance doesn't deal with direct inflow to outflow connections (at this time)
            //dgrn.connect(GeneticCell.inflowNodes.CROWDED, GeneticCell.outflowNodes.COLOR_ADD_R, 1);

        } catch (KeySelectorException err) {
            logger.error("nodes failed to insert in building mock network: " + err.getMessage());
        }

    }
}
