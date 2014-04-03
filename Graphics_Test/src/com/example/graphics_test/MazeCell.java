package com.example.graphics_test;

import android.graphics.Point;

public class MazeCell {
	//Coordinates
	int x, y;
	
	//Cell qualities
	boolean start;
	boolean end;
	boolean wall;
	boolean inMaze;
	boolean pathWay;
	boolean obstacle;
	boolean isPerson;
	
	//Parent cell for computing opposite
	MazeCell parent;
	
	//Contructor
	public MazeCell(int x, int y, MazeCell parent){
		this.wall = false;
		this.end = false;
		this.start = false;
		this.inMaze = false;
		this.pathWay = false;
		this.obstacle = false;
		this.isPerson = false;
		this.x=x;
		this.y=y;
		this.parent = parent;		
	}
	
	//Compute cell located on opposite side of parent
	public MazeCell oppositeCell(){
		int xDif = this.x - parent.x;
		int yDif = this.y - parent.y;
		
		if(xDif != 0){
			return new MazeCell(x+xDif, y, this);
		}
		if(yDif != 0){
			return new MazeCell(x, y+yDif, this);
		}		
		return null;		
	}
	
	public boolean isWall(MazeCell cell){
		return cell.wall;
	}
	
	public boolean isPath(MazeCell cell){
		return cell.pathWay;
	}
	
	public boolean isInMaze(MazeCell cell){
		return cell.inMaze;
	}
	
	public boolean hasObstacle(MazeCell cell){
		return cell.obstacle;
	}
	
	public boolean isStart(MazeCell cell){
		return cell.start;
	}
	
	public boolean isEnd(MazeCell cell){
		return cell.end;
	}
	
	public Point coordinates(MazeCell cell){
		Point point = new Point(this.x, this.y);
		return point;
	}
}
