package sweeper;

class Bomb {
    private Matrix bombMap;
    private int totalBomb;


    public Bomb(int totalBomb) {
        this.totalBomb = totalBomb;
        fixBombsCount();
    }

    void start() {
        bombMap = new Matrix(Box.ZERO);
        for (int i = 0; i < totalBomb; i++) {
            placeBomb();
        }
    }

    Box get(Coord coord) {
        return bombMap.get(coord);
    }

    private void fixBombsCount() {
        int maxBombs = Ranges.getSize().getX() * Ranges.getSize().getY() / 2;
        if (totalBomb > maxBombs) {
            totalBomb = maxBombs;
        }
    }

    private void placeBomb() {
        while (true) {
            Coord coord = Ranges.getRandomCoord();
            if (bombMap.get(coord) == Box.BOMB) {
                continue;
            }
            bombMap.set(coord, Box.BOMB);
            incNumberAroundBomb(coord);
            break;
        }

    }

    private void incNumberAroundBomb(Coord coord) {
        for (Coord around : Ranges.getCoordAround(coord)) {
            if (Box.BOMB != bombMap.get(around)) {
                bombMap.set(around, bombMap.get(around).getNextNumberBox());
            }
        }
    }


     int getTotalBombs() {
        return totalBomb;
    }
}
