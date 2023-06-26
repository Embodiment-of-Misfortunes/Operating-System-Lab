#include <stdio.h>
#include <stdlib.h>

// Structure to represent a process
struct Process {
    int processId;
    int arrivalTime;
    int burstTime;
    int startTime; // Start time
    int completionTime; // Completion time
};

// Function to calculate the completion time for each process
void calculateCompletionTime(struct Process processes[], int n) {
    int currentTime = 0;

    for (int i = 0; i < n; i++) {
        if (currentTime < processes[i].arrivalTime) {
            currentTime = processes[i].arrivalTime;
        }

        processes[i].startTime = currentTime;
        processes[i].completionTime = currentTime + processes[i].burstTime;
        currentTime = processes[i].completionTime;
    }
}

// Function to calculate the turnaround time for each process
void calculateTurnaroundTime(struct Process processes[], int n, int turnaroundTime[]) {
    for (int i = 0; i < n; i++) {
        // Turnaround time = Completion time - Arrival time
        turnaroundTime[i] = processes[i].completionTime - processes[i].arrivalTime;
    }
}

// Function to calculate the waiting time for each process
void calculateWaitingTime(struct Process processes[], int n, int waitingTime[]) {
    int turnaroundTime[n];

    calculateTurnaroundTime(processes, n, turnaroundTime);

    for (int i = 0; i < n; i++) {
        // Waiting time = Turnaround time - Burst time
        waitingTime[i] = turnaroundTime[i] - processes[i].burstTime;
    }
}

// Function to calculate the average waiting time and average turnaround time
void calculateAverageTimes(struct Process processes[], int n) {
    int waitingTime[n], turnaroundTime[n];
    int totalWaitingTime = 0, totalTurnaroundTime = 0;

    calculateCompletionTime(processes, n);
    calculateWaitingTime(processes, n, waitingTime);
    calculateTurnaroundTime(processes, n, turnaroundTime);

    printf("Process ID\tArrival Time\tBurst Time\tStart Time\tCompletion Time\tTurnaround Time\tWaiting Time\n");

    for (int i = 0; i < n; i++) {
        totalWaitingTime += waitingTime[i];
        totalTurnaroundTime += turnaroundTime[i];

        printf("%d\t\t%d\t\t%d\t\t%d\t\t%d\t\t%d\t\t%d\n", processes[i].processId, processes[i].arrivalTime,
               processes[i].burstTime, processes[i].startTime, processes[i].completionTime,
               turnaroundTime[i], waitingTime[i]);
    }

    double averageWaitingTime = (double)totalWaitingTime / n;
    double averageTurnaroundTime = (double)totalTurnaroundTime / n;

    printf("\nAverage Waiting Time: %.2lf\n", averageWaitingTime);
    printf("Average Turnaround Time: %.2lf\n", averageTurnaroundTime);
}

int main() {
    int n;

    printf("Enter the number of processes: ");
    scanf("%d", &n);

    // Dynamic memory allocation for 'n' processes
    struct Process* processes = (struct Process*)malloc(n * sizeof(struct Process));

    printf("Enter the burst time and arrival time for each process:\n");
    for (int i = 0; i < n; i++) {
        printf("Process %d:\n", i + 1);
        printf("Burst Time: ");
        scanf("%d", &processes[i].burstTime);
        printf("Arrival Time: ");
        scanf("%d", &processes[i].arrivalTime);
        processes[i].processId = i + 1;
    }

    calculateAverageTimes(processes, n);

    // Free the allocated memory
    free(processes);

    return 0;
}
