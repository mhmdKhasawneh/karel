/*
 * File: BlankKarel.java
 * ---------------------
 * This class is a blank one that you can change at will.
 */

import stanford.karel.SuperKarel;

public class BlankKarel extends SuperKarel {
	private int columns = Integer.MAX_VALUE;
	private int rows = 1;
	private int moves = 0;

	public void run() {
		setBeepersInBag(1000);

		drawHorizontal();
		drawInner();
		drawVertical();

		resetDimensions();
		resetMoves();
	}
	private void countRows() {
		rows = 1;
		turnLeft();
		while(frontIsClear()) {
			moveAndPrint();
			++rows;
		}
	}
	private void checkDivisibility() {
		if(!(columns >= 7 && rows >= 7))
			throw new UnsupportedOperationException("Can not divide this world!");
	}
	private void resetDimensions(){
		columns = Integer.MAX_VALUE;
		rows = 1;
	}
	private void resetMoves(){
		moves = 0;
	}
	private void fillRow(boolean doubleBeepers) {
		columns = 1;
		putBeeper();
		if(!doubleBeepers) {
			turnLeft();
			while (frontIsClear()) {
				moveAndPrint();
				putBeeper();
				++columns;
			}
		}
		else{
			int i = 0;
			boolean upBefore = false;
			boolean leftBefore = false;
			while(frontIsClear()){
				if(i % 2 != 0){
					++columns;
				}
				moveAndPrint();
				putBeeper();
				if(i % 2 == 0){
					if(!leftBefore) {
						turnLeft();
						leftBefore = true;
					}
					else{
						turnRight();
						leftBefore = false;
					}
				}
				else{
					if(!upBefore){
						turnLeft();
						upBefore = true;
					}
					else{
						turnRight();
						upBefore = false;
					}
				}
				++i;
			}
		}

	}
	private void drawHorizontal(){
		countRows();
		checkDivisibility();
		moveToRowCenter();
		fillRow(rows % 2 == 0);
		checkDivisibility();
	}
	private void moveAndPrint(){
		move();
		++moves;
		System.out.println("Move number " + moves);
	}
	private void moveToRowCenter(){
		turnAround();
		int halfWay = rows % 2 != 0 ? rows / 2 : rows / 2 - 1;
		while(frontIsClear() && halfWay != 0){
			moveAndPrint();
			--halfWay;
		}
	}
	private int getFromCenter(){
		int min = Math.min(columns, rows);
		return min % 2 != 0 ? (min-2)/2 : (min-3)/2;
	}
	private void moveToInnerStartPosition(){
		int fromCenter = getFromCenter();
		int columnCenter = columns / 2;
		turnAround();
		int steps = columns % 2 != 0 ? columnCenter - fromCenter : columnCenter - fromCenter - 1;
		while(frontIsClear() && steps != 0){
			moveAndPrint();
			--steps;
		}
	}
	private void drawInner(){
		moveToInnerStartPosition();
		fillInner();
	}
	private void fillInner(){
		int fromCenter = getFromCenter();

		turnRight();
		int steps = fromCenter;
		moveAndPrint();
		if(!beepersPresent()){
			putBeeper();
			--steps;
		}
		putNBeepers(steps);

		turnLeft();
		steps = columns % 2 != 0 ? 2*fromCenter : 2*fromCenter + 1;
		putNBeepers(steps);

		turnLeft();
		steps = rows % 2 != 0 ? 2*fromCenter : 2*fromCenter + 1;
		putNBeepers(steps);

		turnLeft();
		steps = columns % 2 != 0 ? 2*fromCenter : 2*fromCenter + 1;
		putNBeepers(steps);

		turnLeft();
		steps = fromCenter - 1;
		putNBeepers(steps);
	}
	private void putNBeepers(int N){
		while(frontIsClear() && N != 0){
			moveAndPrint();
			if(!beepersPresent()){
				putBeeper();
			}
			--N;
		}
	}
	private void moveToColumnCenter(){
		int fromCenter = getFromCenter();

		turnAround();
		while(frontIsClear()){
			moveAndPrint();
		}
		turnRight();
		int steps = fromCenter;
		while(frontIsClear() && steps != 0) {
			moveAndPrint();
			--steps;
		}
	}
	private void drawVertical() {
		moveToColumnCenter();
		fillColumn(columns % 2 == 0);
	}
	private void fillColumn(boolean doubleBeepers){
		putBeeper();
		turnRight();
		while(frontIsClear()){
			moveAndPrint();
			if(!beepersPresent()){
				putBeeper();
			}
		}
		if(doubleBeepers){
			turnLeft();
			moveAndPrint();
			putBeeper();
			turnLeft();
			while(frontIsClear()){
				moveAndPrint();
				if(!beepersPresent()){
					putBeeper();
				}
			}
		}
	}
}