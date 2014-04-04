package game.section_16;

import java.lang.Math;
import java.util.ArrayList;

public class MazeGen {
 
 public static void generateObstacles(MazeCell[][] maze, int mazeSize){
	 for(int i = 0; i < mazeSize; i++){
		for(int j = 0; j < mazeSize; j++){
			int var = (int)(Math.random()*10);
			if(!maze[i][j].isWall() && var < 2){
				maze[i][j].obstacle = true;
			}
		}
	 }		 
 }
	
	
 public static MazeCell[][] generateMaze(){
  int mazeSize = 10;
  MazeCell maze[][] = new MazeCell[mazeSize][mazeSize]; 
  
  //Initialize cells
  for(int i = 0; i < mazeSize; i++){
   for(int j = 0; j < mazeSize; j++){
     maze[i][j] = new MazeCell(i, j, null);
   }
  }
  
  //Pick random start point
  MazeCell start = new MazeCell((int)(Math.random()*mazeSize),(int)(Math.random()*mazeSize), null);
  maze[start.x][start.y] = start;
  
  //Initialize and add walls to frontier
  ArrayList<MazeCell> frontierWalls = new ArrayList<MazeCell>();
    
  //3x3 Grid centered at (0,0)
  for(int a = -1; a <= 1; a++){
   for(int b = -1; b <= 1; b++){
    //Skip diagonal neighbors and the origin
    if(a==0 && b==0 || a!=0 && b!=0){
     continue;
    }
    //Ignore out of bounds exceptions
    try{
     //Skip neighbors that are paths
     if(!maze[start.x+a][start.y+b].isWall()){
      continue;
     }
    }catch(Exception e){
     continue;
    }
    //Add eligible cells to frontier 
    frontierWalls.add(new MazeCell(start.x+a, start.y+b, start));
   }
  }
  
  MazeCell save = null;
  
  //Start Prim's algorithm iterations
  while(!frontierWalls.isEmpty()){
   MazeCell current = frontierWalls.remove((int)(Math.random()*frontierWalls.size()));
   MazeCell opposite = current.oppositeCell();
   
   try{
    //When both current cell and opposite are walls
    if(maze[current.x][current.y].isWall()){
     if(maze[opposite.x][opposite.y].isWall()){
      //Make path between two cells
      maze[current.x][current.y].wall = false;
      maze[opposite.x][opposite.y].wall = false;
      //Marks the end node if iteration completes
      save = opposite;  
      
      //Add neighbor walls to frontier again
      for(int a = -1; a <= 1; a++){
       for(int b = -1; b <= 1; b++){
        //Skip diagonal neighbors and the origin
        if(a==0 && b==0 || a!=0 && b!=0){
         continue;
        }
        //Ignore out of bounds exceptions
        try{
         //Skip neighbors that are paths
         if(!maze[opposite.x+a][opposite.y+b].wall){
          continue;
         }
        }catch(Exception e){
         continue;
        }
        //Add eligible cells to frontier 
        frontierWalls.add(new MazeCell(opposite.x+a, opposite.y+b, opposite));
       }
      }      
     }
    }
   }catch(Exception e){
    //Ignore out of bounds and null pointers
   }
   
   if(frontierWalls.isEmpty()){
    maze[save.x][save.y].end = true;
   }   
  } 
  
  generateObstacles(maze, mazeSize);
  
  return maze;
 }
 
 
 }
