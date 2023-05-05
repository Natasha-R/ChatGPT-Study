package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements GoAble {

    int x=8,y=3;




    @Override
    public String goTo(String goCommandString) {

        char direction=goCommandString.charAt(1);
        int value=Integer.parseInt(goCommandString.substring(4,5));
        //System.out.println("debut "+direction+" val "+value);
        System.out.println("direction : "+direction+" value : "+value);
        if(direction=='e') {
            //first red line
            if( x<=3 && 1<=y && y<=6){
                if( (x+value)>3)
                    x=3;
                else
                    x += value;
            }
            //second red line
            if( 4<=x && x<=5 && 2<=y && y<=4){
                if( (x+value)>5)
                    x=5;
                else
                    x += value;
                System.out.println("hello word");
            }
            //the another case
            if(6<=x){
                x+=value;
            }
            if( x<=4 && (y==0 || 7<=y) ){
                x+=value;
            }
            if( 4<=x && x<=5 && (y==1 || y>4)){
                    x += value;
            }
        }
        if(direction=='w') {
            //first red line west
            if( 4<=x && x<=6 && 1<=y && y<=7){
                if( (x-value)<4)
                    x=4;
                else
                    x -= value;
            }
            //second red line west
            if( 6<=x  && 2<=y && y<=4){
                if( (x-value)<6)
                    x = 6;
                else
                    x -= value;
            }
            //The another case
            if(4<=x && (y==1 || (5<=y && y<=6)  )){
                if( (x-value)<4) {
                    x = 4;
                }
                else
                    x-=value;
            }
            if(y == 0 || y > 6){
                x-=value;
            }
        }
        if(direction=='n') {
            //bottom red line nord
            if( 6<=x && x<=8 && (y==1 || y==0) ){
                if( (y+value)>1  )
                    y=1;
                else
                    y += value;
            }
            //top red line nord
            if(6<=x && x<=8 && (1<y && y<5) ){
                if( (y+value)>4 )
                    y=4;
                else
                    y+=value;
            }
            if(x<4 || x==4 || x==5 || 9<=x)
                y+=value;
            if(6<=x && x<=8 && 5<=y)
                y+=value;
        }
        if(direction=='s') {
            //bottom red line
            if(6<=x && x<=8 && 2<=y && y<=4){
                if( (y-value)<2 )
                    y=2;
                else
                    y-=value;
            }
            //top red line
            if(6<=x && x<=8 && 5<=y){
                if( (x-value)<5 ){
                    y=5;
                }
                else
                    y -= value;
            }
            //another case
            if(x<4 || x==4 || x==5 || 9<=x)
                y-=value;
            if(6<=x && x<=8 && y<=1)
                y-=value;
        }
        System.out.println("x: "+x+" y= "+y);
        return "("+x+","+y+")";
    }
    private boolean check(int firstX,int firstY){

        return true;
    }
}
