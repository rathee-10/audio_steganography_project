import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class AudioSteganography {
    public static void embedMessageInAudio(File audioFile, String hiddenMessage) {
        try {
            FileInputStream fileInputStream = new FileInputStream(audioFile);
            double fileSize = audioFile.length();
            byte[] audioData = new byte[(int) fileSize];
            fileInputStream.read(audioData);

            String binaryMessage = LSBSteganography.stringToBinary(hiddenMessage);

            LSBSteganography.embedMessage(audioData, binaryMessage);

            File outputAudioFile = new File(audioFile.getParent(), "audio_with_message.wav");
            FileOutputStream fileOutputStream = new FileOutputStream(outputAudioFile);
            fileOutputStream.write(audioData);

            fileInputStream.close();
            fileOutputStream.close();

            System.out.println("Message embedded successfully in " + outputAudioFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
