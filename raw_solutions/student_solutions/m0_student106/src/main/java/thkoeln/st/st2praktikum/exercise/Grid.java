package thkoeln.st.st2praktikum.exercise;

public class Grid {
    //coordinates
    private int x=1;
    private int y=6;

    //boardes of the field
    final int maxX=11;
    final int maxY=8;

    //contains a list with walls. A wall is a list of coordinats
    // Von welcher Himmerlrichtung laufe ich drüber?
    // Nachher standort berrechnen und dann vgl. ob eine Mauer dazwischen ist.
    final int[] wallHorizontalSo1 = {0,1,5}; //von x bis x bei y
    final int[] wallHorizontalNo1 = {0,1,6}; //von x bis x bei y
    final int[] wallHorizontalSo2 = {1,8,4}; //von x bis x bei y
    final int[] wallHorizontalNo2 = {1,8,5}; //von x bis x bei y

    final int[] wallVerticalEa1 = {1,4,9}; //von y bis y bei x
    final int[] wallVerticalWe1 = {1,4,8}; //von y bis y bei x
    final int[] wallVerticalEa2 = {6,7,3}; //von y bis y bei x
    final int[] wallVerticalWe2 = {6,7,2}; //von y bis y bei x

    public void setX(int x) {
        if (x<0) this.x=0;
        else if (x>=maxX) this.x=10;
        else this.x = x;
    }

    public void setY(int y) {
        if (y<0) this.y=0;
        else if (y>=maxY) this.y=7;
        else this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void checkForWallsInTheWay(Direction direc, int lastX, int lastY){
            switch (direc){
                case no:
                    //check if X could hit a wall
                    if (this.x>=wallHorizontalNo1[0] && this.x<=wallHorizontalNo1[1]){
                        //if Yes check if wall is between old and new Y
                        if (lastY<wallHorizontalNo1[2] && this.y>=wallHorizontalNo1[2]){
                            //if Yes correct position
                            setY(wallHorizontalNo1[2]-1);
                        }
                    }
                    //check if X could hit a wall
                    if (this.x>=wallHorizontalNo2[0] && this.x<=wallHorizontalNo2[1]){
                        //if Yes check if wall is between old and new Y
                        if (lastY<wallHorizontalNo2[2] && this.y>=wallHorizontalNo2[2]){
                            //if Yes correct position
                            setY(wallHorizontalNo2[2]-1);
                        }
                    }
                case ea:
                    //check if Y could hit a wall
                    if (this.y>=wallVerticalEa1[0] && this.y<=wallVerticalEa1[1]){
                        //if Yes check if wall is between old and new X
                        if (lastX<wallVerticalEa1[2] && this.x>=wallVerticalEa1[2]){
                            //if Yes correct position
                            setX(wallVerticalEa1[2]+1);
                        }
                    }
                    //check if Y could hit a wall
                    if (this.y>=wallVerticalEa2[0] && this.y<=wallVerticalEa2[1]){
                        //if Yes check if wall is between old and new X
                        if (lastX<wallVerticalEa2[2] && this.x>=wallVerticalEa2[2]){
                            //if Yes correct position
                            setX(wallVerticalEa2[2]-1);
                        }
                    }
                case so:
                    //check if X could hit a wall
                    if (this.x>=wallHorizontalSo1[0] && this.x<=wallHorizontalSo1[1]){
                        //if Yes check if wall is between old and new Y
                        if (this.y<=wallHorizontalSo1[2] && lastY>wallHorizontalSo1[2]){
                            //if Yes correct position
                            setY(wallHorizontalSo1[2]+1);
                        }
                    }
                    //check if X could hit a wall
                    if (this.x>=wallHorizontalSo2[0] && this.x<=wallHorizontalSo2[1]){
                        //if Yes check if wall is between old and new Y
                        if (this.y<=wallHorizontalSo2[2] && lastY>wallHorizontalSo2[2]){
                            //if Yes correct position
                            setY(wallHorizontalSo2[2]+1);
                        }
                    }
                case we:
                    //check if Y could hit a wall
                    if (this.y>=wallVerticalWe1[0] && this.y<=wallVerticalWe1[1]){
                        //if Yes check if wall is between old and new X
                        if (this.x<=wallVerticalWe1[2] && lastX>wallVerticalWe1[2]){
                            //if Yes correct position
                            setX(wallVerticalWe1[2]+1);
                        }
                    }
                    //check if Y could hit a wall
                    if (this.y>=wallVerticalWe2[0] && this.y<=wallVerticalWe2[1]){
                        //if Yes check if wall is between old and new X
                        if (this.x<=wallVerticalWe2[2] && lastX>wallVerticalWe2[2]){
                            //if Yes correct position
                            setX(wallVerticalWe2[2]+1);
                        }
                    }

            }
    }

    public String getPosition() {
        return ("(" + x + "," + y + ")");
    }
}
