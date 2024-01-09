import javax.sound.sampled.*;
import javax.swing.*;
import java.io.*;
import java.awt.Color;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SoundBoard extends JFrame {
    public static final int MARGIN = 20;
    public static final int BUTTON_WIDTH = 300;
    public static final int BUTTON_HEIGHT = 80;

    private static class SoundButton {
        private Clip clip;
        private File sourceFile;
        private boolean playing;

        public JButton button;

        public SoundButton(File sourceFile) {
            this.sourceFile = sourceFile;

            var name = sourceFile.getName().split("\\.")[0];
            button = new JButton(name);
            button.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
            button.setAlignmentX(CENTER_ALIGNMENT);

            button.addActionListener(e -> {
                if (playing)
                    stop();
                else
                    start();
            });
        }

        public void start() {
            if (clip == null)
                load();
            playing = true;
            button.setBackground(Color.GREEN);
            clip.start();
        }

        public void stop() {
            playing = false;
            button.setBackground(Color.RED);
            clip.stop();
            if (clip.getMicrosecondPosition() == clip.getMicrosecondLength()) {
                clip.setMicrosecondPosition(0);
            }
        }

        public void load() {
            try (var audioInputStream = AudioSystem.getAudioInputStream(sourceFile)) {
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static final List<String> AUDIO_FILE_EXTENSIONS = List.of("wav");

    public static boolean isAudioFile(File file) {
        var segments = file.getName().split("\\.");
        if (segments.length > 0) {
            var extension = segments[segments.length - 1];
            return AUDIO_FILE_EXTENSIONS.contains(extension);
        } else
            return false;
    }

    private File audioSourceDir;
    private ArrayList<SoundButton> soundButtons = new ArrayList<>();

    private JPanel buttonPanel;
    private JScrollPane scrollPane;

    File wavFile;
    URL defaultSound;
    public static Clip clip;
    public static AudioInputStream audioInputStream;

    private void prepare_visuals() {
        setSize(400, 600);
        setLocation(1000, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        var pane = getContentPane();
        pane.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        buttonPanel = new JPanel();
        buttonPanel.setSize(BUTTON_WIDTH + 2 * MARGIN, 5 * BUTTON_HEIGHT + 2 * MARGIN);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
        buttonPanel.setVisible(true);

        scrollPane = new JScrollPane(buttonPanel);
        pane.add(scrollPane);
        scrollPane.setVisible(true);
    }

    public SoundBoard(File audioSourceDir) {
        this.audioSourceDir = audioSourceDir;

        var files = this.audioSourceDir.listFiles();
        var audioFiles = Arrays.stream(files)
                .filter(f -> f.isFile() && f.canRead())
                .filter(SoundBoard::isAudioFile)
                .toList();

        this.prepare_visuals();
        for (File file : audioFiles) {
            var button = new SoundButton(file);
            soundButtons.add(button);
            this.buttonPanel.add(button.button);
            button.button.setVisible(true);
        }
    }

    public static final String SOUND_ASSETS_FOLDER = "sounds";

    public static void main(String args[]) {
        var audioSourceDir = new File(SOUND_ASSETS_FOLDER).getAbsoluteFile();
        SoundBoard soundboard = new SoundBoard(audioSourceDir);
        soundboard.setVisible(true);
    }
}
