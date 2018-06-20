package sweeper;

class Flag {
    private Matrix flagMap;
    private int countOfClosedBoxes;

    void start() {
        flagMap = new Matrix(Box.CLOSED);
        countOfClosedBoxes = Ranges.getSize().getX() * Ranges.getSize().getY();

    }

    Box get(Coord coord) {
        return flagMap.get(coord);
    }

    void setOpenedToBox(Coord coord) {
        flagMap.set(coord, Box.OPENED);
        countOfClosedBoxes--;
    }

    void toogleFlagedToBox(Coord coord) {
        switch (flagMap.get(coord)) {
            case FLAGED:
                setCloseBox(coord);
                break;
            case CLOSED:
                setFlagetToBox(coord);
                break;
        }
    }

    private void setCloseBox(Coord coord) {
        flagMap.set(coord, Box.CLOSED);
    }

    private void setFlagetToBox(Coord coord) {
        flagMap.set(coord, Box.FLAGED);
    }

    public int getCountOfClosedBoxes() {
        return countOfClosedBoxes;
    }

    void setBombedToBox(Coord coord) {
        flagMap.set(coord, Box.BOMBED);
    }

    void setOpenedToClosedBombBox(Coord coord) {
        if (flagMap.get(coord) == Box.CLOSED) {
            flagMap.set(coord, Box.OPENED);
        }
    }

    void setNoBombToFlagedSafeBox(Coord coord) {
        if (flagMap.get(coord) == Box.FLAGED) {
            flagMap.set(coord, Box.NOBOMB);
        }
    }

  

     int getCountOfFlagedBoxesAround(Coord coord) {
        int count = 0;
        for (Coord around : Ranges.getCoordAround(coord)) {
            if (flagMap.get(around) == Box.FLAGED) {
                count++;
            }
        }
        return count;
    }
}
