package io.github.emergentorganization.cellrpg.systems.CASystems.GeneticCells.GeneticCellBuilders;

import io.github.emergentorganization.cellrpg.systems.CASystems.CAs.CACell.GeneticCell;
import io.github.emergentorganization.cellrpg.systems.CASystems.CAs.CACell.GeneticCellTest;
import io.github.emergentorganization.cellrpg.systems.CASystems.GeneticCells.DGRN4j.DGRN;
import it.uniroma1.dis.wsngroup.gexf4j.core.Node;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.crypto.KeySelectorException;


public class TestCell2 implements GeneticNetworkBuilderInterface {
    private static final Logger logger = LogManager.getLogger(GeneticCell.class);

    public void buildNetwork(DGRN dgrn) {
        try {
            Node testNode = dgrn.graph.createNode(GeneticCellTest.TEST_INNER_NODE_ID_1);
            testNode
                    .setLabel(GeneticCellTest.TEST_INNER_NODE_ID_1)
                    .getAttributeValues()
                    .addValue(DGRN.attr_ActivationValue, "0")
                    .addValue(DGRN.attr_AlleleCount, "1");
            dgrn.connect(GeneticCell.inflowNodes.ALWAYS_ON, GeneticCellTest.TEST_INNER_NODE_ID_1, 1);
            dgrn.connect(GeneticCellTest.TEST_INNER_NODE_ID_1, GeneticCell.outflowNodes.COLOR_LIGHTEN, 2);

        } catch (KeySelectorException err) {
            logger.error("nodes failed to insert in building mock network: " + err.getMessage());
        }

    }
}
