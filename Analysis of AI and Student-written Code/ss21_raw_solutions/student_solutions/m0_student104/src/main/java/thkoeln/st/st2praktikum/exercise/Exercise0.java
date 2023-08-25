package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements GoAble
{
    private Dot dot;
    private Field[][] field;
    private Wall[] walls;

    public Exercise0()
    {
        walls=new Wall[8];
        walls[0]=new Wall(3,0,3,2);
        walls[1]=new Wall(0,2,2,2);
        walls[2]=new Wall(1,3,9,3);
        walls[3]=new Wall(9,3,9,7);
        walls[4]=new Wall(0,0,11,0);
        walls[5]=new Wall(0,0,0,8);
        walls[6]=new Wall(11,0,11,8);
        walls[7]=new Wall(0,8,11,8);

        field=new Field[11][8];
        for (int a=0; a<11; a++)
        {
            for (int b=0; b<8; b++)
            {
                field[a][b]=new Field(a,b);
            }
        }

        for (Wall w:walls)
        {
            if(w.getStartY()==w.getEndY())
            {
                System.out.println("Horizontal wall");
                for (int i = w.getStartX(); i < w.getEndX(); i++)
                {
                    if(w.getStartY()!=8)
                    {
                        field[i][w.getStartY()].setWalls(true, 0);
                        System.out.println("Field "+i+"/"+w.getStartY()+" marked with upper wall");
                    }
                    if(w.getStartY()-1!=-1)
                    {
                        field[i][w.getStartY()-1].setWalls(true, 2);
                        System.out.println("Field "+i+"/"+(w.getStartY()-1)+" marked with lower wall");
                    }
                }
            }
            else
            {
                System.out.println("Vertical wall");
                for (int i = w.getStartY(); i < w.getEndY(); i++)
                {
                    if(w.getStartX()!=11)
                    {
                        field[w.getStartX()][i].setWalls(true, 3);
                        System.out.println("Field "+w.getStartX()+"/"+i+" marked with left wall");
                    }
                    if(w.getStartX()>0)
                    {
                        field[w.getStartX()-1][i].setWalls(true, 1);
                        System.out.println("Field "+(w.getStartX()-1)+"/"+i+" marked with right wall");
                    }
                }
            }
        }
        dot=new Dot(field[1][1]);
    }

    public void step(String dir)
    {
        System.out.println("Dot at "+dot.getHere().getX()+"/"+dot.getHere().getY());
        if (!dot.getHere().getWall(dir))
        {
            System.out.println("No obstacle at "+dir);
            switch (dir)
            {
                case "no":
                    System.out.println("Moving no now.");
                    dot.setHere(field[dot.getHere().getX()][dot.getHere().getY()-1]);
                    break;
                case "ea":
                    System.out.println("Moving ea now.");
                    dot.setHere(field[dot.getHere().getX()+1][dot.getHere().getY()]);
                    break;
                case "so":
                    System.out.println("Moving so now.");
                    dot.setHere(field[dot.getHere().getX()][dot.getHere().getY()+1]);
                    break;
                case "we":
                    System.out.println("Moving we now.");
                    dot.setHere(field[dot.getHere().getX()-1][dot.getHere().getY()]);
                    break;
            }
            System.out.println("Dot at "+dot.getHere().getX()+"/"+dot.getHere().getY());
        }
        else
        {
            System.out.println("Obstacle detected at "+dir+". Not moving.");
        }
    }


    @Override
    public String go(String goCommandString) {
        String s = goCommandString.substring(1, goCommandString.length() - 1);
        String[] input = s.split(",");

        String dir = input[0];
        int steps = Integer.parseInt(input[1]);

        for (int i = 0; i < steps; i++)
        {
            step(dir);
        }

        System.out.println("Dot finally at "+dot.getHere().getX()+"/"+dot.getHere().getY());
        int x=dot.getHere().getX();
        int flipY=7-dot.getHere().getY();
        System.out.println("Position converted to "+x+"/"+flipY);

        return "(" + x + "," + flipY + ")" ;
    }
}
