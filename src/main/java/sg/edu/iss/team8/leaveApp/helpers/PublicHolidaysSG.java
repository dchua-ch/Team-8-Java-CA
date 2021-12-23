package sg.edu.iss.team8.leaveApp.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PublicHolidaysSG {
	
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	public PublicHolidaysSG() {
		
	}
	
	public List<Date> getPublicHolidays() throws ParseException {
		List<Date> publicHolidaysSG = new ArrayList<>();
		
		//2020
		Date ph2020day1 = formatter.parse("2020-01-01");
		Date ph2020day2 = formatter.parse("2020-01-25");
		Date ph2020day3 = formatter.parse("2020-01-26");
		Date ph2020day4 = formatter.parse("2020-04-10");
		Date ph2020day5 = formatter.parse("2020-05-01");
		Date ph2020day6 = formatter.parse("2020-05-07");
		Date ph2020day7 = formatter.parse("2020-05-24");
		Date ph2020day8 = formatter.parse("2020-07-10");
		Date ph2020day9 = formatter.parse("2020-07-31");
		Date ph2020day10 = formatter.parse("2020-08-09");
		Date ph2020day11 = formatter.parse("2020-11-14");
		Date ph2020day12 = formatter.parse("2020-12-25");
		
		//2021
		Date ph2021day1 = formatter.parse("2021-01-01");
		Date ph2021day2 = formatter.parse("2021-02-12");
		Date ph2021day3 = formatter.parse("2021-02-13");
		Date ph2021day4 = formatter.parse("2021-04-02");
		Date ph2021day5 = formatter.parse("2021-05-01");
		Date ph2021day6 = formatter.parse("2021-05-13");
		Date ph2021day7 = formatter.parse("2021-05-26");
		Date ph2021day8 = formatter.parse("2021-07-20");
		Date ph2021day9 = formatter.parse("2021-08-09");
		Date ph2021day10 = formatter.parse("2021-11-04");
		Date ph2021day11 = formatter.parse("2021-12-25");
		
		//2022
		Date ph2022day1 = formatter.parse("2022-01-01");
		Date ph2022day2 = formatter.parse("2022-02-01");
		Date ph2022day3 = formatter.parse("2022-02-02");
		Date ph2022day4 = formatter.parse("2022-04-15");
		Date ph2022day5 = formatter.parse("2022-05-01");
		Date ph2022day6 = formatter.parse("2022-05-03");
		Date ph2022day7 = formatter.parse("2022-05-15");
		Date ph2022day8 = formatter.parse("2022-07-10");
		Date ph2022day9 = formatter.parse("2022-08-09");
		Date ph2022day10 = formatter.parse("2022-10-24");
		Date ph2022day11 = formatter.parse("2022-12-25");
		
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
