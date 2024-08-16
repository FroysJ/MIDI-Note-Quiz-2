package com.example.demo.controller;

import com.example.demo.DemoApplication;
import javafx.stage.Stage;
import model.Quiz;
import model.Note;

import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.*;
import web.MidiApp;
import web.MidiApp.Storage;

import java.util.AbstractMap;

@RestController
@RequestMapping("/quiz")
public class Controller {

    private Quiz quiz = new Quiz();

    @GetMapping("/start")
    public String startQuiz() {
        quiz.nextQuestion();
        return "Quiz started";
    }

    @GetMapping("/play")
    public void playNote() {
        quiz.play();
    }

    @PostMapping("/submit")
    public boolean submitAnswer(@RequestParam String noteName, @RequestParam int octave) {
        boolean correct = quiz.checkAnswer(noteName, octave);
        if (correct) {
            quiz.addScore();
        }
        quiz.nextQuestion();
        return correct;
    }

    @GetMapping("/score")
    public int getScore() {
        return quiz.getScore();
    }

    @GetMapping("/answered")
    public int getAnswered() {
        return quiz.getAnswered();
    }

    @GetMapping("/finished")
    public boolean isFinished() {
        return quiz.isFinished();
    }

    @GetMapping("/current")
    public String getCurrentNote() {
        AbstractMap.SimpleEntry<String, Integer> currNoteID = quiz.getCurrNote().getNoteID();
        String ret = currNoteID.getKey();
        ret += " ";
        ret += currNoteID.getValue().toString();
        return ret;
    }

    @GetMapping("/end")
    public String endQuiz() {
        quiz = new Quiz();
        DemoApplication.stopServer();
        return "ok";
    }
}