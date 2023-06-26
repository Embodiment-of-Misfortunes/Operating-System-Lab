import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class Process3 {
    String name;
    int arrivalTime;
    int burstTime;
    int age = 3;
    
    public Process3(String name, int arrivalTime, int burstTime) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }
}


//sort based on arrival time
class ArrivalComparator1 implements Comparator<Process3> {
    public int compare(Process3 p1, Process3 p2) {
        if (p1.arrivalTime < p2.arrivalTime)
            return -1;
        else if (p1.arrivalTime > p2.arrivalTime)
            return 1;
        else
            return 0;
    }
}

//sort based on Burst time
class BurstComparator1 implements Comparator<Process3> {
  @Override
  public int compare(Process3 p1, Process3 p2) {
      if (p1.burstTime < p2.burstTime)
          return -1;
      else if (p1.burstTime > p2.burstTime)
          return 1;
      else
          return 0;
  }
}


//main class
public class SJFSa1 {
	 static float AverageWaitingTime = 0f;
	 static float AverageTurnAroundTime = 0f;
	    
	static ArrayList<Process3> processes = new ArrayList<>();
	static ArrayList<Process3> temp = new ArrayList<>();
	
	static int currentTime = 0;
	
	
	public static void calculate(String name , int at,int bt) {
		int st = currentTime;
		int ct = st + bt;
		currentTime = currentTime + bt;
		
		int tat = ct - at;
		int wt = tat - bt;
		AverageWaitingTime += wt;
		AverageTurnAroundTime += tat;
		
		System.out.println(name+"\t\t" + at +"\t\t"+ bt +"\t\t"+ st +"\t\t\t"+ ct +"\t\t\t"+ tat +"\t\t\t"+ wt );
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
	    Boolean flag = false;

	   
	    
	    System.out.print("Enter the number of processes: ");
	    int n = sc.nextInt();

	    System.out.print("Enter process Arrival & Burst time :\n");
	    for (int i = 0; i < n; i++) {
	    	System.out.println("For Process " + (i+1) + ":");        
	    	int arrivalTime = sc.nextInt();
	    	int burstTime = sc.nextInt();

	    	processes.add(new Process3("P"+(i+1), arrivalTime, burstTime));
	    }

		//sort based on arrival time
	    Collections.sort(processes, new ArrivalComparator1());
	    
	     
	    System.out.println("PID\t\t"+"Arrival\t\t"+"Burst\t\t"+"Starting\t\t"+"Compleition\t\t"+"TurnAround\t\t"+"Waiting");
	    //calculate 1st process
	    calculate(processes.get(0).name , processes.get(0).arrivalTime , processes.get(0).burstTime);
	    
	    processes.remove(0);
	    
	    
	    while(!processes.isEmpty() || !temp.isEmpty()) {
	    //add process which has arrived into temporary list
	    if(!processes.isEmpty()) {	
	    	for(int i= 0;i<processes.size();i++) {
	    		if(processes.get(i).arrivalTime <= currentTime) {
	    		temp.add(processes.get(i));
	    		processes.remove(processes.get(i));

	    		}
	    	}
	    }
	    
	    if(!processes.isEmpty()) {	
	    	for(int i= 0;i<processes.size();i++) {
	    		if(processes.get(i).arrivalTime <= currentTime) {
	    		temp.add(processes.get(i));
	    		processes.remove(processes.get(i));
 
	    		}
	    	}
	    }
	    

	    //sort based on burst time
	    Collections.sort(temp, new BurstComparator1());
	    

	    for(int i =0;i<temp.size();i++) {
	    	if(temp.get(i).age == 0) {
	    		flag = true;
	    		calculate(temp.get(i).name, temp.get(i).arrivalTime, temp.get(i).burstTime);
	    		temp.remove(temp.get(i));
	    		
	    	}else {
	    		flag = false;
	    	}
	    }
	    
	    
	    if(flag == false) {
	    calculate(temp.get(0).name,temp.get(0).arrivalTime,temp.get(0).burstTime);
	    for(Process3 p : temp) {
	    	p.age -= 1 ;
	    }
	    temp.remove(temp.get(0));
	    flag = false;
	    }
	    
	    }
	    
	    System.out.println("Average Waiting Time : " + AverageWaitingTime/n);
	    System.out.println("Average Turn Around Time : " + AverageTurnAroundTime/n);
	}

}


