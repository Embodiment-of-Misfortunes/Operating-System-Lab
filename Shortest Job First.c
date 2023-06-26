include <stdio.h>

void sjf_scheduling(int processes[][3], int n) {
    // Sort the processes based on arrival time
    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - i - 1; j++) {
            if (processes[j][1] > processes[j + 1][1]) {
                // Swap processes
                int temp[3];
                for (int k = 0; k < 3; k++) {
                    temp[k] = processes[j][k];
                    processes[j][k] = processes[j + 1][k];
                    processes[j + 1][k] = temp[k];
                }
            }
        }
    }

    // Variables to track waiting time, turnaround time, completion time, start time, and execution order
    int waiting_time[n];
    int turnaround_time[n];
    int completion_time[n];
    int start_time[n];
    int execution_order[n];

    // Initialize remaining burst time for each process
    int remaining_burst_time[n];
    for (int i = 0; i < n; i++) {
        remaining_burst_time[i] = processes[i][2];
    }

    // Calculate completion time, waiting time, and turnaround time for each process
    int current_time = 0;
    int completed_processes = 0;
    int prev_process_index = -1;
    int order = 1; // Order of execution

    while (completed_processes < n) {
        int shortest_burst_time = __INT_MAX__;
        int shortest_process_index = -1;

        // Find the process with the shortest remaining burst time among the arrived processes
        for (int i = 0; i < n; i++) {
            if (processes[i][1] <= current_time && remaining_burst_time[i] < shortest_burst_time && remaining_burst_time[i] > 0) {
                shortest_burst_time = remaining_burst_time[i];
                shortest_process_index = i;
            }
        }

        // If no process is found, increment the current time
        if (shortest_process_index == -1) {
            current_time += 1;
            continue;
        }

        // If the previously executed process was different, set the start time to the completion time of the previous process
        if (prev_process_index != shortest_process_index) {
            start_time[shortest_process_index] = current_time;
        }

        // Execute the process for one unit of time
        remaining_burst_time[shortest_process_index] -= 1;
        current_time += 1;

        // If the process is completed, calculate waiting time, turnaround time, and completion time
        if (remaining_burst_time[shortest_process_index] == 0) {
            completion_time[shortest_process_index] = current_time;
            waiting_time[shortest_process_index] = completion_time[shortest_process_index] - processes[shortest_process_index][1] - processes[shortest_process_index][2];
            turnaround_time[shortest_process_index] = waiting_time[shortest_process_index] + processes[shortest_process_index][2];
            completed_processes += 1;
            prev_process_index = shortest_process_index;

            // Update the execution order
            execution_order[shortest_process_index] = order;
            order += 1;
        }
    }

    // Calculate average waiting time and average turnaround time
    float avg_waiting_time = 0.0;
    float avg_turnaround_time = 0.0;
    for (int i = 0; i < n; i++) {
        avg_waiting_time += waiting_time[i];
        avg_turnaround_time += turnaround_time[i];
    }
    avg_waiting_time /= n;
    avg_turnaround_time /= n;

    // Calculate throughput
    float throughput = n / completion_time[n - 1];

    // Print the details
    printf("Process\tArrival Time\tBurst Time\tCompletion Time\tTurnaround Time\tWaiting Time\tOrder Of Execution\n");
    for (int i = 0; i < n; i++) {
        printf("P%d\t\t%d\t\t%d\t\t%d\t\t%d\t\t%d\t\t%d\n", processes[i][0], processes[i][1], processes[i][2], completion_time[i], turnaround_time[i], waiting_time[i], execution_order[i]);
    }

    // Print average waiting time, average turnaround time, and throughput
    printf("\nAverage Waiting Time: %.2f\n", avg_waiting_time);
    printf("Average Turnaround Time: %.2f\n", avg_turnaround_time);
    printf("Throughput: %.2f processes per time unit\n", throughput);
}

int main() {
    // Get the number of processes from the user
    int n;
    printf("Enter the number of processes: ");
    scanf("%d", &n);

    // Initialize the 2D array to store the processes
    int processes[n][3];

    // Get arrival time and burst time for each process
    for (int i = 0; i < n; i++) {
        int process_id = i + 1;
        printf("Enter arrival time for process P%d: ", process_id);
        scanf("%d", &processes[i][1]);
        printf("Enter burst time for process P%d: ", process_id);
        scanf("%d", &processes[i][2]);
    }

    // Call the SJF scheduling function
    sjf_scheduling(processes, n);

    return 0;
}
