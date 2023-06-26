#include <stdio.h>
int main() {
    int n;
    printf("Enter the no. of process : ");
   scanf("%d",&n);
   //process id's
   //execu time of all processes
   int proc[n],execu_time[n];
   int wait_time[n], tat[n], total_wt = 0, total_tat = 0,temp[n];
   for(int i=0;i<n;i++){
       printf("Enter the Process ID for P[%d]:",i+1);
       scanf("%d",&proc[i]);
       printf("Enter the execution time for P[%d]:",i+1);
       scanf("%d",&execu_time[i]);

   }
   //sorting
   for (int i = 0; i < n-1 ; i++ )
   {
       for (int j=i+1;j<n;j++){
           if (execu_time[i]>execu_time[j]){
               int temp=execu_time[i];
               execu_time[i]=execu_time[j];
               execu_time[j]=temp;
               int temp2=proc[i];
               proc[i]=proc[j];
               proc[j]=temp2;
           }
       }
   }
   // calculating waiting time and turn around time

   printf("order  processID  Burst   Waiting  Turn around");
   for (int i = 0; i < n ; i++ ){
       if(i==0){
           wait_time[0] = 0;
       }
       else{
           wait_time[i] = execu_time[i-1] + wait_time[i-1] ;
       }
   tat[i] = execu_time[i] + wait_time[i];
   total_wt = total_wt + wait_time[i];
      total_tat = total_tat + tat[i];
      printf("\n %d \t%d \t%d \t%d \t%d", i+1,proc[i], execu_time[i], wait_time[i], tat[i]);
   }
   printf("\nAverage waiting time = %f", (float)total_wt / (float)n);
   printf("\nAverage turn around time = %f", (float)total_tat / (float)n);
   return 0;
}

