TSP Solver - Simulated Annealing and Tabu Search

Instructions:
1. Compile the program:
   javac *.java

2. Create a JAR file:
   jar cfe TSP_Solver.jar Main *.class

3. Run the program:
   java -jar TSP_Solver.jar

4. Input:
   - The program will automatically process the following TSP files:
     - 8.txt
     - 12.txt
     - 15.txt
     - 20.txt
     - 25.txt
   - Each algorithm (Simulated Annealing and Tabu Search) will run 10 times for each file.
   - The program will output the best solution, cost, seed value, and runtime for each run.

5. Output:
   - The program will print the results in the following format:
     Problem Instance | Algorithm | Seed Value | Cost | Best Solution | Runtime (ns)

Run command:
java -jar TSP_Solver.jar