package sg.edu.iss.team8.leaveApp.helpers;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PublicHolidaysSG {
	
	DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	public PublicHolidaysSG() {
		
	}
	
	public List<LocalDate> getPublicHolidays() {
		List<LocalDate> publicHolidaysSG = new ArrayList<LocalDate>();
		
		//2020
		LocalDate ph2020day1 = LocalDate.parse("2020-01-01", format);
		LocalDate ph2020day2 = LocalDate.parse("2020-01-25", format);
		LocalDate ph2020day3 = LocalDate.parse("2020-01-26", format);
		LocalDate ph2020day4 = LocalDate.parse("2020-04-10", format);
		LocalDate ph2020day5 = LocalDate.parse("2020-05-01", format);
		LocalDate ph2020day6 = LocalDate.parse("2020-05-07", format);
		LocalDate ph2020day7 = LocalDate.parse("2020-05-24", format);
		LocalDate ph2020day8 = LocalDate.parse("2020-07-10", format);
		LocalDate ph2020day9 = LocalDate.parse("2020-07-31", format);
		LocalDate ph2020day10 = LocalDate.parse("2020-08-09", format);
		LocalDate ph2020day11 = LocalDate.parse("2020-11-14", format);
		LocalDate ph2020day12 = LocalDate.parse("2020-12-25", format);
		
		//2021
		LocalDate ph2021day1 = LocalDate.parse("2021-01-01", format);
		LocalDate ph2021day2 = LocalDate.parse("2021-02-12", format);
		LocalDate ph2021day3 = LocalDate.parse("2021-02-13", format);
		LocalDate ph2021day4 = LocalDate.parse("2021-04-02", format);
		LocalDate ph2021day5 = LocalDate.parse("2021-05-01", format);
		LocalDate ph2021day6 = LocalDate.parse("2021-05-13", format);
		LocalDate ph2021day7 = LocalDate.parse("2021-05-26", format);
		LocalDate ph2021day8 = LocalDate.parse("2021-07-20", format);
		LocalDate ph2021day9 = LocalDate.parse("2021-08-09", format);
		LocalDate ph2021day10 = LocalDate.parse("2021-11-04", format);
		LocalDate ph2021day11 = LocalDate.parse("2021-12-25", format);
		
		//2022
		LocalDate ph2022day1 = LocalDate.parse("2022-01-01", format);
		LocalDate ph2022day2 = LocalDate.parse("2022-02-01", format);
		LocalDate ph2022day3 = LocalDate.parse("2022-02-02", format);
		LocalDate ph2022day4 = LocalDate.parse("2022-04-15", format);
		LocalDate ph2022day5 = LocalDate.parse("2022-05-01", format);
		LocalDate ph2022day6 = LocalDate.parse("2022-05-03", format);
		LocalDate ph2022day7 = LocalDate.parse("2022-05-15", format);
		LocalDate ph2022day8 = LocalDate.parse("2022-07-10", format);
		LocalDate ph2022day9 = LocalDate.parse("2022-08-09", format);
		LocalDate ph2022day10 = LocalDate.parse("2022-10-24", format);
		LocalDate ph2022day11 = LocalDate.parse("2022-12-25", format);
		
		publicHolidaysSG.add(ph2020day1);
		publicHolidaysSG.add(ph2020day2);
		publicHolidaysSG.add(ph2020day3);
		publicHolidaysSG.add(ph2020day4);
		publicHolidaysSG.add(ph2020day5);
		publicHolidaysSG.add(ph2020day6);
		publicHolidaysSG.add(ph2020day7);
		publicHolidaysSG.add(ph2020day8);
		publicHolidaysSG.add(ph2020day9);
		publicHolidaysSG.add(ph2020day10);
		publicHolidaysSG.add(ph2020day11);
		publicHolidaysSG.add(ph2020day12);
		publicHolidaysSG.add(ph2021day1);
		publicHolidaysSG.add(ph2021day2);
		publicHolidaysSG.add(ph2021day3);
		publicHolidaysSG.add(ph2021day4);
		publicHolidaysSG.add(ph2021day5);
		publicHolidaysSG.add(ph2021day6);
		publicHolidaysSG.add(ph2021day7);
		publicHolidaysSG.add(ph2021day8);
		publicHolidaysSG.add(ph2021day9);
		publicHolidaysSG.add(ph2021day10);
		publicHolidaysSG.add(ph2021day11);
		publicHolidaysSG.add(ph2022day1);
		publicHolidaysSG.add(ph2022day2);
		publicHolidaysSG.add(ph2022day3);
		publicHolidaysSG.add(ph2022day4);
		publicHolidaysSG.add(ph2022day5);
		publicHolidaysSG.add(ph2022day6);
		publicHolidaysSG.add(ph2022day7);
		publicHolidaysSG.add(ph2022day8);
		publicHolidaysSG.add(ph2022day9);
		publicHolidaysSG.add(ph2022day10);
		publicHolidaysSG.add(ph2022day11);
		
		return publicHolidaysSG;
	}
}
