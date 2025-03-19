import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Maze {
    private String[] map;

    public Maze(String filename) {
        try{

            File myFile = new File(filename);
            Scanner myScanner = new Scanner(myFile);

            int numRows = Integer.parseInt(myScanner.nextLine());

            map = new String[numRows];

            MazeConstrRec(myScanner , 0);

            myScanner.close() ;

        }catch (FileNotFoundException e){
            map = new String[0];
        }
    }

    private void MazeConstrRec(Scanner myScanner , int rowIdx){
        if(rowIdx < map.length && myScanner.hasNextLine()){
            map[rowIdx] = myScanner.nextLine();
            MazeConstrRec(myScanner , rowIdx + 1);
        }
    }



    public Maze(Maze other) {

        map = new String[other.map.length];
        CopyMazeRec(other,0);
    }

    private void CopyMazeRec(Maze other , int rowIdx){
        if(rowIdx < map.length){
            map[rowIdx] = other.map[rowIdx];
            CopyMazeRec(other , rowIdx + 1);
        }
    }

    @Override
    public String toString() {
        StringBuilder MyString = new StringBuilder();
        if(map.length == 0){
            return "Empty Map";
        }
        toStringRec(map , 0 , MyString);
        return MyString.toString() ;
    }

    private void toStringRec(String [] mymap , int rowIdx , StringBuilder MyString){
        if(rowIdx < map.length - 1 ){
            MyString.append(map[rowIdx]).append("\n");
            toStringRec(map , rowIdx + 1 , MyString);
            return ;
        }
        MyString.append(map[rowIdx]);

    }

    public boolean validSolution(int startX, int startY, int goalX, int goalY, LinkedList path) {
        if(path == null){
            return false;
        }
        if (startX < 0 || startY < 0 || startY >= map.length || startX >= map[startY].length() || map[startY].charAt(startX) == 'X'){
            return false;
        }

        if (goalX < 0 || goalY < 0 || goalY >= map.length || goalX >= map[goalY].length() || map[goalY].charAt(goalX) == 'X'){
            return false;
        }

        if (path.head == null || (path.head.x != startX || path.head.y != startY)){
            return false;
        }

        CoordinateNode tail = tailNodeRec(path.head);

        if (tail == null || (tail.x != goalX || tail.y != goalY)){
            return false;
        }

        return validSolRec(startX, startY, goalX, goalY, path.head.next , path.head);
    }

    private boolean validSolRec(int startX, int startY, int goalX,int goalY, CoordinateNode current , CoordinateNode previous){
        
        if(current == null){
            return true ;
        }

        if (current == null || current.x < 0 || current.y < 0 || current.y >= map.length || current.x >= map[current.y].length() || map[current.y].charAt(current.x) == 'X'){
            return false;
        }

        if(current.x >= previous.x + 2 ){
            return false;
        }

        if(current.y >= previous.y + 2 ){
            return false;
        }

        if(current.y == previous.y && current.x == previous.x){
            return false;
        }

        if(current.y == previous.y + 1 && current.x == previous.x + 1 ){
            return false;
        }

        if(current.y == previous.y - 1 && current.x == previous.x - 1 ){
            return false;
        }


        if(current.y == previous.y - 1 && current.x == previous.x + 1 ){
            return false;
        }

        if(current.y == previous.y + 1 && current.x == previous.x - 1 ){
            return false;
        }

        return validSolRec(startX, startY, goalX, goalY, current.next , previous.next);


        
    }

    private CoordinateNode tailNodeRec(CoordinateNode current) {
        if (current == null || current.next == null)
            return current;
        return tailNodeRec(current.next);
    }


    public String solve(int x, int y, int goalX, int goalY) {

        if (x < 0 || y < 0 || y >= map.length || x >= map[y].length() || map[y].charAt(x) == 'X'){
            return "No valid solution exists";
        }

        if (goalX < 0 || goalY < 0 || goalY >= map.length || goalX >= map[goalY].length() || map[goalY].charAt(goalX) == 'X'){
            return "No valid solution exists";
        }


        LinkedList sol = solveRec(x, y, goalX, goalY, new LinkedList());
    

        if (sol == null || sol.head == null) {
            return "No valid solution exists";
        } 
        else 
        {
            StringBuilder solvedsb = new StringBuilder();

            solvedsb.append("Solution\n");

            String[] solMaze = new String[map.length];

            System.arraycopy(map, 0, solMaze, 0, map.length);

            PathRec(solMaze, sol.head);

            solMaze[y] = solMaze[y].substring(0, x) + "S" + solMaze[y].substring(x + 1);

            solMaze[goalY] = solMaze[goalY].substring(0, goalX) + "E" + solMaze[goalY].substring(goalX + 1);
    
            appendRowsRec(solMaze, 0, solvedsb);
            
            solvedsb.append(sol.toString());

            return solvedsb.toString();
        }
    }
    
    private LinkedList solveRec(int x, int y, int goalX, int goalY, LinkedList path) {
        if (x == goalX && y == goalY) {
            path.append(x, y);
            return path;
        }
    
        if (x >= 0 && y >= 0 && y < map.length && x < map[y].length() && map[y].charAt(x) != 'X' && !path.contains(x, y)) {
            LinkedList leftPath = new LinkedList();
            leftPath.appendList(path);
            leftPath.append(x, y);
            leftPath = solveRec(x - 1, y, goalX, goalY, leftPath);
            if (leftPath != null) return leftPath;
        }
    
        if (x >= 0 && y >= 0 && y < map.length && x < map[y].length() && map[y].charAt(x) != 'X' && !path.contains(x, y)) {
            LinkedList upPath = new LinkedList();
            upPath.appendList(path);
            upPath.append(x, y);
            upPath = solveRec(x, y - 1, goalX, goalY, upPath);
            if (upPath != null) return upPath;
        }
    

        if (x >= 0 && y >= 0 && y < map.length && x < map[y].length() && map[y].charAt(x) != 'X' && !path.contains(x, y)) {
            LinkedList downPath = new LinkedList();
            downPath.appendList(path);
            downPath.append(x, y);
            downPath = solveRec(x, y + 1, goalX, goalY, downPath);
            if (downPath != null) return downPath;
        }
    

        if (x >= 0 && y >= 0 && y < map.length && x < map[y].length() && map[y].charAt(x) != 'X' && !path.contains(x, y)) {
            LinkedList rightPath = new LinkedList();
            rightPath.appendList(path);
            rightPath.append(x, y);
            rightPath = solveRec(x + 1, y, goalX, goalY, rightPath);
            if (rightPath != null) return rightPath;
        }
    

        return null;
    }

    private void PathRec(String[] solvedMaze, CoordinateNode currentNode) {
        if (currentNode != null) {
            solvedMaze[currentNode.y] = solvedMaze[currentNode.y].substring(0, currentNode.x) + "@" + solvedMaze[currentNode.y].substring(currentNode.x + 1);
            PathRec(solvedMaze, currentNode.next);
        }
    }
    
    private void appendRowsRec(String[] solvedMaze, int rowIdx, StringBuilder result) {
        if (rowIdx < solvedMaze.length - 1) {
            result.append(solvedMaze[rowIdx]).append("\n");
            appendRowsRec(solvedMaze, rowIdx + 1, result);
        } else {
            result.append(solvedMaze[rowIdx]).append("\n");
        }
    }

    public LinkedList validStarts(int x, int y) {
        LinkedList paths = new LinkedList();
        validStartsRec(0, 0, x, y, paths);
        return paths;
    }

    private void validStartsRec(int x, int y, int goalX, int goalY, LinkedList paths) {
    

    
        if (x < 0 || y < 0 || y >= map.length || x >= map[y].length() || map[y].charAt(x) == 'X' || paths.contains(x, y)) {
            return ;
        }
    
        LinkedList sol = new LinkedList();
        solveRec(x,y,goalX,goalY,sol);
        if(sol != null){
            paths.append(x, y);
        }

        validStartsRec(x + 1, y, goalX, goalY, paths);
        validStartsRec(x = 0, y + 1, goalX, goalY, paths);
        

    
    
    }
    

}