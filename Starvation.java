package priority;

import java.util.*;

class Process2 {
    int id;
    int arrivalTime;
    int burstTime;
    int priority;
    int tempPriority;
    int startTime;
    int completionTime;
    int waitingTime;
    int turnaroundTime;
    boolean completed ;
  

    public Process2(int id, int arrivalTime, int burstTime, int priority) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.tempPriority = priority;
        this.startTime = 0;
        this.completionTime = 0;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
        this.completed = false;
    }
}

public class PriorityAging {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        List<Process2> processes = new ArrayList<>();

        System.out.println("Enter Arrival , Burst , Priority ");
        for (int i = 0; i < n; i++) {
            System.out.println("for process " + (i + 1) + ":");
            int arrivalTime = scanner.nextInt();
            int burstTime = scanner.nextInt();
            int priority = scanner.nextInt();

            Process2 process = new Process2(i + 1, arrivalTime, burstTime, priority);
            processes.add(process);
        }

        // Sort processes based on arrival time
        processes.sort(Comparator.comparingInt((Process2 p) -> p.arrivalTime));

        int currentTime = 0;
        int completedProcesses = 0;

        while (completedProcesses < n) {
            Process2 selectedProcess = null;
            int highestPriority = Integer.MAX_VALUE;

            for (Process2 process : processes) {
            	
               if (process.arrivalTime <= currentTime && process.priority < highestPriority && !process.completed) {
                    highestPriority = process.priority;
                    selectedProcess = process;
                    
                }
            }

            if (selectedProcess == null) {
                currentTime++;
                continue;
            }

           selectedProcess.startTime = currentTime;
            selectedProcess.completionTime = selectedProcess.startTime + selectedProcess.burstTime;
            currentTime += selectedProcess.burstTime;
            selectedProcess.turnaroundTime = selectedProcess.completionTime - selectedProcess.arrivalTime;

            selectedProcess.waitingTime = selectedProcess.turnaroundTime - selectedProcess.burstTime;
            selectedProcess.completed = true;
            completedProcesses++;

            // Aging: Increase the priority of waiting processes
            for (Process2 process : processes) {
                if (process.arrivalTime <= currentTime && process != selectedProcess) {
                    process.priority--;
                }
            }
        }

        float AverageWaitingTime = 0f , AverageTurnAroundTime = 0f;
        System.out.println("\nProcess\t\tArrival\t\tBurst\t\tPriority\tCompletion\t\tWaiting\t\tTurnaround");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------");
        for (Process2 process : processes) {
        	AverageTurnAroundTime += process.turnaroundTime;
        	AverageWaitingTime += process.waitingTime;
            System.out.println(process.id + "\t\t" + process.arrivalTime + "\t\t" + process.burstTime + "\t\t" +process.tempPriority+"\t\t"+
                    + process.completionTime + "\t\t" + process.waitingTime +
                    "\t\t" + process.turnaroundTime);
        }
        
        System.out.println("Average Waiting Time : " + AverageWaitingTime/n);
        System.out.println("Average TurnAround Time : " + AverageTurnAroundTime/n);

        scanner.close();
    }
}

