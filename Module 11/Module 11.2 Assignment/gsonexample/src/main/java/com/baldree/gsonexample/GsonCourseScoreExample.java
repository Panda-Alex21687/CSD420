package com.baldree.gsonexample;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class GsonCourseScoreExample {

    public static void main(String[] args) {
        String jsonData = """
                [
                  {
                    "studentName": "Alex",
                    "course": "CSD420",
                    "module": 11,
                    "score": 96.5,
                    "passed": true
                  },
                  {
                    "studentName": "Jordan",
                    "course": "CSD420",
                    "module": 11,
                    "score": 88.0,
                    "passed": true
                  }
                ]
                """;

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        Type scoreListType = new TypeToken<List<CourseScore>>() {
        }.getType();

        List<CourseScore> scores = gson.fromJson(jsonData, scoreListType);

        System.out.println("Original JSON converted into Java objects:");

        for (CourseScore score : scores) {
            System.out.println(
                    score.studentName
                            + " earned "
                            + score.score
                            + " in "
                            + score.course
                            + " Module "
                            + score.module
                            + ". Passed: "
                            + score.passed);
        }

        scores.add(new CourseScore("Taylor", "CSD420", 11, 91.0, true));

        String updatedJson = gson.toJson(scores);

        System.out.println();
        System.out.println("Updated Java list converted back into JSON:");
        System.out.println(updatedJson);
    }

    static class CourseScore {
        String studentName;
        String course;
        int module;
        double score;
        boolean passed;

        CourseScore(String studentName, String course, int module, double score, boolean passed) {
            this.studentName = studentName;
            this.course = course;
            this.module = module;
            this.score = score;
            this.passed = passed;
        }
    }
}