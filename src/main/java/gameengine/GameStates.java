package gameengine;

public enum GameStates {
	SETUP{
        @Override
        public String toString() {
            return "SETUP Phase";
        }
    },
	ONGOING{
        @Override
        public String toString() {
            return "ONGOING game";
        }
    },
	STANDBY{
        @Override
        public String toString() {
            return "STANDBY Phase";
        }
    },
	GAMEWON{
        @Override
        public String toString() {
            return "GAME WON";
        }
    },
	GAMELOST{
        @Override
        public String toString() {
            return "GAME OVER";
        }
    },
	TERMINATE{
        @Override
        public String toString() {
            return "GAME TERMINATE";
        }
    }
    
}

