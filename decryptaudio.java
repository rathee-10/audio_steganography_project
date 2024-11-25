import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class decryptaudio  {

    public static String extractMessageFromAudio(File audioFile) {
        StringBuilder binaryMessage = new StringBuilder();

        try {
            FileInputStream fileInputStream = new FileInputStream(audioFile);
            byte[] audioData = new byte[(int) audioFile.length()];
            fileInputStream.read(audioData);
            fileInputStream.close();

            for (byte b : audioData) {
                int lsb = b & 1; 
                binaryMessage.append(lsb);
            }
            return binaryToString(binaryMessage.toString());

        } catch (IOException e) {
            System.out.println("Error reading the audio file.");
            e.printStackTrace();
            return null;
        }
    }

    private static String binaryToString(String binaryMessage) {
        StringBuilder message = new StringBuilder();

        for (int i = 0; i < binaryMessage.length() - 7; i += 8) {
            String byteString = binaryMessage.substring(i, i + 8);  
            int charCode = Integer.parseInt(byteString, 2);  
            if (charCode == 0) {  
                break;
            }
            message.append((char) charCode); 
        }
        return message.toString(); 
    }
    public static void main(String[] args) {
        File audioFileWithMessage = new File("C:\\Users\\HITESH RATHEE\\OneDrive\\Desktop\\minor_pjct\\audio_with_message.wav");

        String extractedMessage = extractMessageFromAudio(audioFileWithMessage);
        if (extractedMessage != null) {
            System.out.println("Extracted Message: " + extractedMessage);
        } else {
            System.out.println("No message found.");
        }
    }
}


