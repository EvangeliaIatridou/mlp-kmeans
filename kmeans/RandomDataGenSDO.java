//Evangelia Iatridou AM 4676, Spyridoula Tsafoni AM 5373, Aikaterini Panagiota Katsanou AM 5249

import java.util.Random;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

class RandomDataGenSDO{

	public static void main(String[] args){
		Random rnd = new Random();
		
		double[] x1 = new double[9];
		double[] x2 = new double[9];
		double[] baseNums = {-2 ,1.6,-1.2,1.6, 0 , 1.6,-1.8,0.8,-0.6,0.8,-2, 0,-1.2, 0,0,0};
		double[] multNums = {0.4,0.4,-0.4,0.4,-0.4,0.4,0.4,0.4, 0.4, 0.4, 0.4,0.4,0.4,0.4,-0.4,0.4};
		
		int k=0; //array counter
		String data = "";
		String filePath = "dataSDO.txt";
		
		for(int j=0;j<8;j++){ // counter j represents each cluster
			for(int i=0;i<100;i++){  // for the first 7 clusters
				//nothing here yet
				
				x1[j] = baseNums[k] + rnd.nextDouble()*multNums[k];

				x2[j] = baseNums[k+1] +rnd.nextDouble()*multNums[k+1];
				
				System.out.println(x1[j]+", "+x2[j]+" TEAM"+(j+1));
				data += x1[j]+","+x2[j]+"\n";
			}
			System.out.println();
			k+=2;
			
		}
		
		for(int i=0;i<200;i++){ // for the last cluster
			
			x1[8] = rnd.nextDouble()*(-2);
			
			x2[8] = rnd.nextDouble()*2;
			
			
			System.out.println(x1[8]+","+x2[8]+" TEAM9");
			
			data += x1[8]+","+x2[8]+ "\n";
			
			
		}
		
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))){
			writer.write(data);
		}catch(IOException e){
			System.err.println("error occured: unable to write SDO data.");
			System.exit(0);
		}
		
	}

}