package io.github.emergentorganization.cellrpg.systems.CASystems.CAs;

import io.github.emergentorganization.cellrpg.components.CAGridComponents;


public class NoBufferCA implements iCA {
    public void generate(CAGridComponents gridComps) {
        for (int i = 0; i < gridComps.states.length; i++) {
            for (int j = 0; j < gridComps.states[0].length; j++) {
                gridComps.states[i][j].setState(util.ca_rule(i, j, gridComps));
            }
        }
    }
}
