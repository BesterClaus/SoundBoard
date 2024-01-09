package Literatur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.*;
import java.lang.ProcessBuilder.Redirect;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Color;

public class WavPlayer extends JFrame {
    JButton btn = new JButton("Play Sound");
    JButton btn2 = new JButton("Trigger");
    JButton btn4 = new JButton("Stop");
    JButton btn3 = new JButton("Wyld");

    JPanel jp = new JPanel();
    JPanel jp2 = new JPanel();
    JPanel jp3 = new JPanel();
    JPanel jp4 = new JPanel();
    File wavFile;
    URL defaultSound;
    public static Clip clip;
    public static AudioInputStream audioInputStream;

    public WavPlayer() {
        setSize(300, 100);
        setLocation(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        createLayout();
        getContentPane().add(jp);
        getContentPane().add(jp2);
        getContentPane().add(jp4);
        getContentPane().add(jp3);
        pack();

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btn.setBackground(Color.GREEN);
                play("file:D:/GC6CMM4.wav");
            }
        });

        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btn2.setBackground(Color.GREEN);
                play("file:C:/Users/rober/Downloads/annuschka.wav");
            }
        });

        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btn3.setBackground(Color.GREEN);
                play("file:D:/Never Gonna Give You Up Original.mp3");
            }
        });

        btn4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stop();
                btn4.setBackground(Color.RED);

            }
        });

    }

    private void createLayout() {
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        jp.setVisible(true);
        jp.setSize(600, 200);
        jp.add(btn);
        jp.add(btn2);
        jp.add(btn3);
        jp3.add(btn4);
    }

    public void play(String url) {
        try {
            defaultSound = new URL(url);
            audioInputStream = AudioSystem.getAudioInputStream(defaultSound);
            try {
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
                btn4.setBackground(Color.WHITE);

            } catch (LineUnavailableException e) {
            }

        } catch (UnsupportedAudioFileException | IOException e) {
        }
    }

    public void stop() {
        btn.setBackground(Color.WHITE);
        btn2.setBackground(Color.WHITE);
        btn3.setBackground(Color.WHITE);
        clip.stop();
    }

    public static void main(String args[]) {
        WavPlayer t = new WavPlayer();
        t.setVisible(true);

    }

}
