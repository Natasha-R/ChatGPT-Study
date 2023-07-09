package thkoeln.st.st2praktikum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ProjectApplication {

	/**
	 * Entry method
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}


    // 12 x 8 matrix [borders]
	// edges -> barrieren
	//  X spalten, Y zeilen | [X,Y] | X ost-west ,Y NORD-SÜD
	// initial dot @ [5,3]

	//EGDES @ [6,2] -> [6,8] , [3,3] -> [9,3], [1,7] -> [6,7] ,[4,1]->[4,3]

	// EDGE1 = [6,2;6,3;6,4;6,5;6,6]

	//if(punkt) ) edge1/2/3/4
	//-1
	//else +1 (repeat)



}
