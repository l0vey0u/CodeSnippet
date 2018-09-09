/** 
 * Copyright (c) 2018, Deok Ho Jeong
 * 
 * This file is licenced under a Creative Commons license: 
 * http://creativecommons.org/licenses/by/4.0/ 
 */

/*
 *  Dispute
 *   ArrayList vs LinkedList
 *    if data index has some meaning , Array List will be good
 *    else LinkedList will be good 
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;


public class StudentScoreManager {
	private Scanner scan;
	private static final int END_SIG_SCORE = 0;
	private static final String END_SIG_NAME = "end";
	private ArrayList<Student> stdList;

	public StudentScoreManager() {
		scan = new Scanner(System.in);
		stdList = new ArrayList<Student>();
	}

	public void inputScoreData() {
		String name;
		int schlYear;
		int score;
		LinkedList<Integer> scoreList;

		while (!((name = scan.next()).equals(END_SIG_NAME))) {
			schlYear = scan.nextInt();
			scoreList = new LinkedList<Integer>();
			while ((score = scan.nextInt()) != END_SIG_SCORE) {
				scoreList.add(score);
			}
			stdList.add(new Student(name, schlYear, scoreList));
		}
	}

	public void printScoreData() {
		
		LinkedList<Integer> scoreList;
		double customTotal = 0.0;
		double customAvg;

		int cnt = 0;
		double topAvg = 0.0;
		String topStdName = null;

		// Output
		for (Student std : stdList) {
			System.out.print(std.getName() + " ");
			System.out.print("(" + std.getSchlYear() + "학년) ");

			// TO DO : NULL Return시 처리
			scoreList = std.getScoreList();
			for (int scr : scoreList) {
				System.out.print(scr + " ");
				customTotal += scr;
			}

			customTotal -= std.getMax() + std.getMin();
			customAvg = customTotal / (scoreList.size() - 2);
			if (topStdName == null) {
				topStdName = std.getName();
				topAvg = customAvg;
			} else {
				if (customAvg > topAvg) {
					topStdName = std.getName();
					topAvg = customAvg;
				}
			}
			
			System.out.printf("[%.2f] ( %d, %d 제외 %d개 )\n", customAvg, std.getMin(), std.getMax(),
					scoreList.size() - 2);

			customTotal = 0.0;
		}

		// TO DO : 공동 1등의 경우 처리
		System.out.println("1등: " + topStdName);

		// 우수선수 출력
		System.out.print("우수선수 : ");
		for (Student std : stdList) {
			if (std.isGoodStudent()) {
				cnt++;
				System.out.print(std.getName() + " ");
			}
		}
		System.out.println(" - " + cnt + "명");
	}

	public static void main(String[] args) {
		StudentScoreManager SSM = new StudentScoreManager();
		SSM.inputScoreData();
		SSM.printScoreData();
		SSM.scan.close();
	}

}

class Student {

	private String name;
	private int schlYear;
	private LinkedList<Integer> scoreList;

	private int max, min;

	public Student(String name, int schlYear, LinkedList<Integer> scoreList) {
		this.name = name;
		this.schlYear = schlYear;
		this.scoreList = scoreList;
		max = Collections.max(scoreList);
		min = Collections.min(scoreList);
	}

	public boolean isGoodStudent() {
		if (max >= 45)
			if (min >= 10)
				return true;
		return false;
	}

	public int getMax() {
		return max;
	}

	public int getMin() {
		return min;
	}

	public String getName() {
		return name;
	}

	public int getSchlYear() {
		return schlYear;
	}

	public LinkedList<Integer> getScoreList() {
		return scoreList;
	}

	public int getScore(int offset) throws ArrayIndexOutOfBoundsException {
		return scoreList.get(offset);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSchlYear(int schlYear) {
		this.schlYear = schlYear;
	}

	public void setScoreList(LinkedList<Integer> scoreList) {
		this.scoreList = scoreList;
	}

	public void setScore(int offset, int score) throws ArrayIndexOutOfBoundsException {
		this.scoreList.set(offset, score);
	}

}