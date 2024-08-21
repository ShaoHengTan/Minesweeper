package gameengine;

public enum GameStates {
	STANDBY{ // might not be used
        @Override
        public String toString() {
            return "STANDBY Phase";
        }
    },
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

