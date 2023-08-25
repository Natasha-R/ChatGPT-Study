package thkoeln.st.st2praktikum.exercise;


public class Exercise0 implements Moveable {

    //x sit von 0 bis 7
    private int achseX = 9;
    //y ist von 0 bis 11
    private int achseY = 13;

    private int achseXminus1 = achseX-1;
    private int achseYminus1 = achseY-1;

    private int feld[][] = new int[achseY][achseX];


    private int position[] = {5,3};


    @Override
    public String moveTo(String moveCommandString) {

        felden();

        //Extrahieren der Richtung
        // String richtung = (moveCommandString.substring(1, 3));
        String richtung = (moveCommandString.substring((moveCommandString.indexOf('[') + 1), (moveCommandString.indexOf(','))));
        // Extrahieren der Bewegungseinheiten
        // bewegungseinheiten = Integer.parseInt(moveCommandString.substring(4, 5));
       int  bewegungseinheiten = Integer.parseInt(moveCommandString.substring((moveCommandString.indexOf(',') + 1), (moveCommandString.indexOf(']'))));


        //Kontrollkram, später weg
        /*
        System.out.println("Position  Zeile: " + position[0]);
        System.out.println("Position  Spalte: " + position[1]);
        System.out.println("Position Richtung: " + richtung);
        System.out.println("Position Bewegungseinheiten: " + bewegungseinheiten);
         */

        switch (richtung){


            case "no": {

                /*
                bewegen nach norden

                -------
                1.1 prüfen, ob ich bereits oben bin
                1.2 bewegung ist inhalt von position[1] um eine zeile erhöht
                vergleich das mit dem grid, ob da mauer ist
                wenn ja nicht bewegen
                wenn nein bewegen
                ------

                 */

                do{

                //check ob in oberster Zeile also position[1] == 7
                //statt break auch einfach ien return machen, geht auch
                if (position[1] == 7) break;

               //     System.out.println("Wert position0: "+position[0]+" Wert postion1 +1:  "+(position[1]+1));

                    int temp_feldwert = feld[position[0]][(position[1]+1)];
                 //   System.out.println("Wert temp_feld: "+temp_feldwert);


                    int posplus2=position[1]+2;
                    if(posplus2>=7) posplus2=7;
                    int temp_feldwert2 = feld[position[0]][posplus2];

                //    System.out.println("Wert temp_feld2: "+temp_feldwert2);

                    int posplus3=position[1]+1;
                    if(posplus3>=7) posplus3=7;
                    int temp_feldwert3 = feld[position[0]+1][posplus3];

                  //  System.out.println("Wert temp_feld3: "+temp_feldwert3);

                if(temp_feldwert==0 ||((temp_feldwert==1 && temp_feldwert2==1)&&(temp_feldwert3==0))){
                    position[1]=position[1]+1;
                }else break;


                bewegungseinheiten--;
            } while(bewegungseinheiten>0);

            }break;


            //--------------------------------------------------------------------------------
            //--------------------------------------------------------------------------------
            //--------------------------------------------------------------------------------
            //--------------------------------------------------------------------------------


            case "ea":{

                do{

                    //statt break auch einfach ien return machen, geht auch
                    if (position[0] == 11) break;

                //    System.out.println("Wert position0 +1:   "+(position[0]+1)+" Wert postion1: "+(position[1]));

                    int temp_feldwert = feld[position[0]+1][(position[1])];
              //      System.out.println("Wert temp_feld: "+temp_feldwert);

                    int posplus2=position[0]+2;
                    if(posplus2>=11) posplus2=10;
                    int temp_feldwert2 = feld[posplus2][(position[1])];

              //      System.out.println("Wert temp_feld2: "+temp_feldwert2);

                    if(temp_feldwert==0 || (temp_feldwert==1 && temp_feldwert2==1) ){
                        position[0]=position[0]+1;
                    }else break;


                    bewegungseinheiten--;
                } while(bewegungseinheiten>0);



            }break;

            //--------------------------------------------------------------------------------
            //--------------------------------------------------------------------------------
            //--------------------------------------------------------------------------------
            //--------------------------------------------------------------------------------


            case "we":{

                do{

                    //statt break auch einfach ien return machen, geht auch
                    if (position[0] == 0) break;

                //    System.out.println("Wert position0 -1:   "+(position[0]-1)+" Wert postion1: "+(position[1]));

                    int temp_feldwert = feld[position[0]-1][(position[1])];

               //     System.out.println("Wert temp_feld: "+temp_feldwert);


                    int posplus2=position[0]-2;
                    if(posplus2<=0) posplus2=0;
                    int temp_feldwert2 = feld[posplus2][(position[1])];

                   // System.out.println("Wert temp_feld2: "+temp_feldwert2);

                    int posplus3=position[1]-1;
                    if(posplus3<=0) posplus3=0;
                    int temp_feldwert3 = feld[position[0]-1][posplus3];

                   // System.out.println("Wert temp_feld3: "+temp_feldwert3);


                    boolean temp1 =temp_feldwert==0;
                    boolean temp2 =(temp_feldwert==1 && temp_feldwert2==1);
                    boolean temp3 =temp_feldwert2==0;
                    boolean temp4=(temp_feldwert==1&&temp_feldwert3==1);

                    if((temp1 || temp2) || temp4 || temp3){
                        if(temp3 && temp4) {
                            position[0]=position[0]-1;
                            break;
                        }

                        position[0]=position[0]-1;
                    }else break;

                    bewegungseinheiten--;
                } while(bewegungseinheiten>0);

            }break;


            //--------------------------------------------------------------------------------
            //--------------------------------------------------------------------------------
            //--------------------------------------------------------------------------------
            //--------------------------------------------------------------------------------



            case "so":{

                do{

                    if (position[1] == 0) break;

           //         System.out.println("Wert position0: "+position[0]+" Wert postion1 -1:  "+(position[1]-1));

                    int temp_feldwert = feld[position[0]][(position[1]-1)];

                  //  System.out.println("Wert temp_feld: "+temp_feldwert);

                    if(temp_feldwert==0){
                        position[1]=position[1]-1;
                    }else break;

                    bewegungseinheiten--;
                } while(bewegungseinheiten>0);

            }break;


            default:{
                System.out.println("Keine richtige Richtung zum richten, \ndamit ist das Richten der Richtung nicht möglich");
            }break;

        }
        return ("("+position[0]+","+position[1]+")");
    }



    public void felden(){

        for (int x = 0; x < achseY; x++) {
            for (int y = 0; y < achseX; y++) {
                feld[x][y]=0;

                //Wand Zeilen nur Norden Süden abfragen als Horizontal
                //  wand1 = "(1,6)-(5,6)";
                // wand2 = "(3,3)-(8,3)";

                //Wand Spalten Nur Osten Westen abfragen also Vertikal
                // wand3 = "(6,2)-(6,7)";
                //  wand4 = "(4,1)-(4,2)";


                //Wand1
                if((y==6)&&((x>=1)&&(x<=5))) feld[x][y]=1;
                //Wande2
                if((y==3)&&((x>=3)&&(x<=8))) feld[x][y]=1;


                //Wand3
                if((x==6)&&((y>=2)&&(y<=7))) feld[x][y]=1;
                //Wand4
                if((x==4)&&((y>=1)&&(y<=2))) feld[x][y]=1;
            }
        }
    }
}
