package org.algovisualizer.ui;

import javazoom.jl.player.Player;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MusicPlayer {
    private Player player;
    private Thread playerThread;
    private final List<String> playlist = new ArrayList<>();
    private int currentTrackIndex = 0;
    private PlaybackMode mode = PlaybackMode.NO_REPEAT;

    public enum PlaybackMode {
        NO_REPEAT,
        REPEAT_ONE,
        REPEAT_ALL,
        SHUFFLE
    }

    public MusicPlayer() {
        playlist.add("/music/lofi.mp3");
        playlist.add("/music/classical.mp3");
        playlist.add("/music/instrumental.mp3");
        playlist.add("/music/jazz.mp3");
    }

    public void play(int trackIndex) {
        stop();
        currentTrackIndex = trackIndex;
        startPlayback();
    }

    private void startPlayback() {
        if (currentTrackIndex < 0 || currentTrackIndex >= playlist.size()) {
            return; // Invalid index
        }
        
        String resourcePath = playlist.get(currentTrackIndex);
        
        playerThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try (InputStream inputStream = getClass().getResourceAsStream(resourcePath)) {
                    if (inputStream == null) {
                        System.err.println("Could not find music resource: " + resourcePath);
                        break;
                    }
                    player = new Player(inputStream);
                    player.play();
                } catch (Exception e) {
                    if (e instanceof InterruptedException) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                    e.printStackTrace();
                }

                if (Thread.currentThread().isInterrupted()) break;
                
                handleNextTrack();
            }
        });
        playerThread.setDaemon(true);
        playerThread.start();
    }

    private void handleNextTrack() {
        switch (mode) {
            case REPEAT_ONE:
                // The loop in startPlayback will handle re-playing the same track.
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
        if (mode == PlaybackMode.SHUFFLE && playerThread == null) {
            Collections.shuffle(playlist); // Shuffle playlist when shuffle is first enabled
        }
    }
}