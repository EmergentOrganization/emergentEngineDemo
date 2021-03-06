package io.github.emergentorganization.cellrpg.systems.CASystems.GeneticCells.GeneticCellBuilders;

import io.github.emergentorganization.cellrpg.systems.CASystems.CAs.CACell.GeneticCell;
import io.github.emergentorganization.cellrpg.systems.CASystems.GeneticCells.DGRN4j.DGRN;
import it.uniroma1.dis.wsngroup.gexf4j.core.Node;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.crypto.KeySelectorException;


public class AgeDarkener implements GeneticNetworkBuilderInterface {
    private static final Logger logger = LogManager.getLogger(GeneticCell.class);

    public void buildNetwork(DGRN dgrn) {
        try {
            Node lightener = dgrn.graph.createNode("always-darken-gene");
            lightener
                    .setLabel("always-darken-gene")
                    .getAttributeValues()
                    .addValue(GeneticCell.attr_ActivationValue, "0")
                    .addValue(DGRN.attr_AlleleCount, "1");
            dgrn.connect(GeneticCell.inflowNodes.ALWAYS_ON, lightener.getId(), 1);
            dgrn.connect(lightener.getId(), GeneticCell.outflowNodes.COLOR_DARKEN, 1);

        } catch (KeySelectorException err) {
            logger.error("nodes failed to insert in building mock network: " + err.getMessage());
        }

    }
}
