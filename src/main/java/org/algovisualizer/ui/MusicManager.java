package org.algovisualizer.ui;

import javazoom.jl.player.Player;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MusicManager {
    private static final MusicManager INSTANCE = new MusicManager();

    private Player player;
    private Thread playerThread;
    private final List<String> playlist = new ArrayList<>();
    private int currentTrackIndex = 0;
    private PlaybackMode mode = PlaybackMode.NO_REPEAT;
    private boolean isPlaying = false;

    public enum PlaybackMode {
        NO_REPEAT,
        REPEAT_ONE,
        REPEAT_ALL,
        SHUFFLE
    }

    private MusicManager() {
        playlist.add("/music/lofi.mp3");
        playlist.add("/music/classical.mp3");
        playlist.add("/music/instrumental.mp3");
        playlist.add("/music/jazz.mp3");
    }

    public static MusicManager getInstance() {
        return INSTANCE;
    }

    public void play(int trackIndex) {
        stop();
        currentTrackIndex = trackIndex;
        isPlaying = true;
        startPlaybackLoop();
    }

    private void startPlaybackLoop() {
        if (playerThread != null) {
            playerThread.interrupt();
        }
        
        playerThread = new Thread(() -> {
            while (isPlaying && !Thread.currentThread().isInterrupted()) {
                try (InputStream inputStream = getClass().getResourceAsStream(playlist.get(currentTrackIndex))) {
                    if (inputStream == null) {
                        System.err.println("Could not find music resource: " + playlist.get(currentTrackIndex));
                        break; 
                    }
                    player = new Player(inputStream);
                    player.play();
                } catch (Exception e) {
                    if (Thread.currentThread().isInterrupted()) {
                        break;
                    }
                }
                
                if (!Thread.currentThread().isInterrupted()) {
                    handleNextTrack();
                }
            }
        });
        playerThread.setDaemon(true);
        playerThread.start();
    }

    private void handleNextTrack() {
        if (!isPlaying) return;

        switch (mode) {
            case REPEAT_ONE:
                // Loop will restart the same track
                break;
            case REPEAT_ALL:
                currentTrackIndex = (currentTrackIndex + 1) % playlist.size();
                break;
            case SHUFFLE:
                currentTrackIndex = (int) (Math.random() * playlist.size());
                break;
            case NO_REPEAT:
            default:
                stop();
                break;
        }
    }

    public void stop() {
        isPlaying = false;
        if (playerThread != null) {
            playerThread.interrupt();
            playerThread = null;
        }
        if (player != null) {
            player.close();
            player = null;
        }
    }

    public void setMode(PlaybackMode mode) {
        this.mode = mode;
    }
}