package io.github.emergentorganization.cellrpg.scenes;

import io.github.emergentorganization.emergent2dcore.PixelonTransmission;
import io.github.emergentorganization.cellrpg.scenes.game.Arcade;
import io.github.emergentorganization.cellrpg.scenes.game.LifeGeneLab;
import io.github.emergentorganization.cellrpg.scenes.game.Story;
import io.github.emergentorganization.cellrpg.scenes.menu.MainMenu;
import io.github.emergentorganization.cellrpg.tools.mapeditor.MapEditor;


public enum Scene {

    MAIN_MENU {
        @Override
        public BaseScene getScene(PixelonTransmission pt) {
            return new MainMenu(pt);
        }
    },
    ARCADE {
        @Override
        public BaseScene getScene(PixelonTransmission pt) {
            return new Arcade(pt);
        }
    },
    LAB {
        @Override
        public BaseScene getScene(PixelonTransmission pt) {
            return new LifeGeneLab(pt);
        }
    },
    EDITOR {
        @Override
        public BaseScene getScene(PixelonTransmission pt) {
            return new MapEditor(pt);
        }
    },
    STORY {
        @Override
        public BaseScene getScene(PixelonTransmission pt) {
            return new Story(pt);
        }
    };

    public abstract BaseScene getScene(PixelonTransmission pt);
}
