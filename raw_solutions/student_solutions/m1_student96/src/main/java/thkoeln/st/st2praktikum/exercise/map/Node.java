package thkoeln.st.st2praktikum.exercise.map;

import lombok.Data;

import java.util.UUID;



public @Data
class Node {
    private UUID deckID;
    private Coordinates coordinates;
    private Boolean isBlocked = false;
    private Node north = null;
    private Node south = null;
    private Node east = null;
    private Node west = null;
    private Node connection = null;

    public Node (UUID deckID, int x , int y ) {
        this.setDeckID(deckID);
        this.coordinates = new Coordinates(x,y);
    }

    public void makeNorthernConnection(Node n) {
        this.setNorth(n);
        n.setSouth(this);
    }

    public void makeSouthernConnection(Node n) {
        this.setSouth(n);
        n.setNorth(this);
    }

    public void makeEasternConnection(Node n) {
        this.setEast(n);
        n.setWest(this);
    }

    public void makeWesternConnection(Node n) {
        this.setWest(n);
        n.setEast(this);
    }

    public void removeNorthernConnection(Node end) {
        try {
            if (this == end){
                this.getNorth().setSouth(null);
                this.setNorth(null);
            } else {
                this.getNorth().setSouth(null);
                this.setNorth(null);
                this.getEast().removeNorthernConnection(end);
            }
        } catch (NullPointerException e) {
            System.out.println("NullPointerException: " + e);
        }
    }

    public void removeSouthernConnection(Node end) {
        try {
            if (this == end){
                this.getSouth().setNorth(null);
                this.setSouth(null);
            } else {
                this.getSouth().setNorth(null);
                this.setSouth(null);
                this.getEast().removeSouthernConnection(end);
            }
        } catch (NullPointerException e) {
            System.out.println("NullPointerException: " + e);
        }
    }

    public void removeEasternConnection(Node end) {
        try {
            if (this == end){
                this.getEast().setWest(null);
                this.setEast(null);
            } else {
                this.getEast().setWest(null);
                this.setEast(null);
                this.getNorth().removeEasternConnection(end);
            }

        } catch (NullPointerException e) {
            System.out.println("NullPointerException: " + e);
        }
    }

    public Node getConnection() {
        return connection;
    }

    public void removeWesternConnection() {
        this.getWest().setEast(null);
        this.setEast(null);
    }



    //@TODO tidy up setNorth etc.
    public void setNorth ( Node n){
        this.north = n;
    }

    public void setEast ( Node n){
        this.east = n;
    }

    public void setSouth ( Node n){
        this.south = n;
    }

    public void setWest ( Node n){
        this.west = n;
    }

    public String toString() {
        return getCoordinates().toString();
    }

}
