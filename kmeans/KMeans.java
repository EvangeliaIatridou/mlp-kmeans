//Evangelia Iatridou AM 4676, Spyridoula Tsafoni AM 5373, Aikaterini Panagiota Katsanou AM 5249

import java.util.Random;
import java.util.Scanner;
import java.lang.Math; //for sqrt and pow
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Arrays;

class KMeans{ //K6
	
	private int M; //number of clusters 
	private int DATASET_SIZE = 1000;
	
	private double[][] centers = new double[M][2]; //has centers wj as a vector of weights for each center
	private double[][] data = new double[DATASET_SIZE][2];
	private double[][][] Oj = new double[M][DATASET_SIZE][2]; //array of data for each cluster
	
	private double error;
	
	private Random rnd = new Random(System.currentTimeMillis());
	
	public KMeans(){
		this.M = M;
		for(int i=0;i<M;i++){
			for(int j=0;j<DATASET_SIZE;j++){
				Oj[i][j][0]=0;
				Oj[i][j][1]=0;
			}
		}
	}

	public double[][] loadData()
	{
		BufferedReader reader =null;
		try
		{
			reader = new BufferedReader(new FileReader("dataSDO.txt"));
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File was not found");
			System.out.println("or could not be opened.");
			System.exit(0);
		}
		try
		{
			String line = reader.readLine();
			int counter=0;
			while (line != null) 
			{
				line = line.trim();
				String[] split = line.split(",");
				
				double x = Double.parseDouble(split[0].trim());
				double y = Double.parseDouble(split[1].trim());
				data[counter][0]=x;
				data[counter][1]=y;
				line = reader.readLine(); // read next line
				if(counter<DATASET_SIZE) // prints data in cmd
				{
					System.out.println(data[counter][0]+" "+data[counter][1]+" "+counter);
					counter++;
				}
			}
			reader.close();
		}
		catch(IOException e)
		{
			System.out.println("IOException");
			System.out.println("or could not be opened.");
			System.exit(0);
		}
		return data;

	}
	
	public void initializeClusters(){		
		this.Oj = Oj;
		for(int j=0;j<M;j++){
			for(int i=0;i<DATASET_SIZE;i++){
				Oj[j][i][0] = 0;
				Oj[j][i][1] = 0;	
			}
		}		
	}
	
	public void initializeCenters(){ 	
		this.centers = centers;
		this.data = data;
		int randomCenterPos = 0;	
		for(int i=0;i<M;i++){
			randomCenterPos = rnd.nextInt(DATASET_SIZE);
			this.centers[i] = data[randomCenterPos];

		}
	}
	

	public double computeEuclDist(double examplePoint1, double examplePoint2, double centerPoint1, double centerPoint2){
		double dist = 0;
		dist = Math.sqrt(Math.pow(examplePoint1-centerPoint1,2) + Math.pow(examplePoint2-centerPoint2,2));
		return dist;
	}
	
	//sum of distances for each example
	public double computeError(){ 
		double sum = 0;
		for(int i =0;i<M;i++){
			for(int j=0;j<DATASET_SIZE;j++){
				if(Oj[i][j][0]!=0.0 || Oj[i][j][1]!=0.0 ){
					sum += computeEuclDist(this.Oj[i][j][0],this.Oj[i][j][1],this.centers[i][0],this.centers[i][1]);
				}
			}
		}
		return sum;
	}

	public double findMinDist(double dist1, double dist2){		
		double dist = 0;
		if(dist1<dist2){
			dist = dist1;
		}else if(dist1>dist2){
			dist = dist2;
		}
		return dist;
	}


	public void assignClusters(double point1, double point2){
		
		double euclDist = 0;
		int chosenIndex = -1;
		double minDist = 1000;
		
		for(int i =0;i<M;i++){ 
			euclDist = computeEuclDist(point1,point2,centers[i][0],centers[i][1]);
			if(euclDist<minDist){
				minDist = euclDist;
				chosenIndex = i;
			}
		}
		
		if(chosenIndex!=-1){
			for(int j=0;j<DATASET_SIZE;j++){
				if(Oj[chosenIndex][j][0]==0.0 && Oj[chosenIndex][j][1]==0.0){
					Oj[chosenIndex][j][0]=point1;
					Oj[chosenIndex][j][1]=point2;
					break;
				}
			}
		}
	}
	
	public double[][] computeNewCenters(){ //wj(t+1) = mean of points in assigned team (cluster)
		double[][] newCenters = new double[M][2]; //might need initialization smh
		
		for(int i=0;i<M;i++){
			double sumX1=0;
			double sumX2=0;
			double clusterDataCount=0;
			for(int j=0;j<DATASET_SIZE;j++){
				if(Oj[i][j][0]!=0.0 || Oj[i][j][1]!=0.0){
					sumX1 += Oj[i][j][0];
				    sumX2 += Oj[i][j][1];
					clusterDataCount++;
				}				
			}
		
			if(clusterDataCount>0){				
				newCenters[i][0] = sumX1/clusterDataCount;
				newCenters[i][1] = sumX2/clusterDataCount;		
			}else{
				newCenters[i][0]= centers[i][0];
				newCenters[i][1] = centers[i][1];	
			}
			
		}	
		centers = newCenters;
		
		return centers;
	}
	
	public void setNumOfClusters(int M){
		this.M = M;
	}
	
	public void setOj(double[][][] Oj){
		this.Oj = Oj;
	}
	
	public void setCenters(double[][] centers){
		this.centers = centers;
	}
	
	public int getDatasetSize(){
		return DATASET_SIZE;
	}
	public void setOjSize(int M, int DATASET_SIZE,double[][][] Oj){
		this.Oj = Oj;
		Oj = new double[M][DATASET_SIZE][2];
	}
	public double[][][] getOj(){
		return this.Oj;
	}
	
	public double[][] getCenters(){
		return this.centers;
	}
	
	
	public static void main(String[] args){
		
		KMeans k = new KMeans();
		
		Scanner in = new Scanner(System.in);
		System.out.println("define number of clusters M: "); //4,8,10,12
		int M = in.nextInt();
		k.setNumOfClusters(M);
		
		int DATASET_SIZE = k.getDatasetSize();
		k.setNumOfClusters(M);

		double minError=10000;
		double tempError=0;
		double[][] inData;
		double[][] minCenters = new double[M][2];
		double[][][] Oj = new double[M][DATASET_SIZE][2];
		double[][] centers = new double[M][2];
		
		String outData = "";		
		inData = k.loadData();
		
		for(int runs=0;runs<20;runs++){
			
			k.setCenters(centers);
			k.initializeCenters();
			
			double[][] oldCenters = new double[M][2];
			double[][] newCenters = new double[M][2];
			boolean terminalConditions = false;
			while(terminalConditions==false){
			
				terminalConditions = true;
				k.setOj(Oj);
				k.initializeClusters();
				
				for(int i=0;i<DATASET_SIZE;i++){
					k.assignClusters(inData[i][0],inData[i][1]);
				}
				
				oldCenters = k.getCenters();
				newCenters = k.computeNewCenters();
				
				for(int i=0;i<M;i++){
					
					if(oldCenters[i][0]!=newCenters[i][0] || oldCenters[i][1]!=newCenters[i][1])
					{
						terminalConditions = false;
						break;
					}					
				}
				
				tempError = k.computeError(); 
				if(tempError<minError){
					minError = tempError;
					minCenters = newCenters;
				}
			
			}
		}
		
		System.out.println("\n\n\nMinimum error: "+minError+" with centers:");
		
		for(int i=0;i<M;i++){ //for loop for the txt file writer
			System.out.print(minCenters[i][0]+" "+minCenters[i][1]+" "+"\n");
			outData+=k.getCenters()[i][0]+","+k.getCenters()[i][1]+","+1000+"\n";
			for(int j=0;j<DATASET_SIZE;j++){		
				if(k.getOj()[i][j][0]!=0.0 || k.getOj()[i][j][1]!=0.0 ){
					outData+=k.getOj()[i][j][0]+","+k.getOj()[i][j][1]+","+i+"\n";
				}
			}
		}
		
		String filePath = "KMeansOut.txt";
		
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))){
			writer.write(outData);
		}catch(IOException e){
			System.err.println("error occured: unable to write KMeans data.");
			System.exit(0);
		}
		
	}
	
}