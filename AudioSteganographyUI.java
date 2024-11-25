import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class AudioSteganographyUI extends JFrame {
    private JLabel fileNameLabel;
    private JTextArea secretMessageTextArea;
    private JProgressBar progressBar;
    private JLabel charCountLabel;
    private File selectedFile;

    public AudioSteganographyUI() {
        
        setTitle("Audio Steganography");
        setSize(600, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(173, 216, 230));
        add(mainPanel, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("Audio Steganography", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setBounds(150, 20, 300, 30);
        mainPanel.add(titleLabel);

        JButton fileInputButton = new JButton("Choose Audio File");
        fileInputButton.setBounds(225, 70, 150, 30);
        fileInputButton.addActionListener(e -> chooseFile());
        mainPanel.add(fileInputButton);

        JPanel dropArea = new JPanel();
        dropArea.setBounds(150, 120, 300, 80);
        dropArea.setBorder(new LineBorder(Color.WHITE, 2, true));
        dropArea.setBackground(new Color(173, 216, 230));
        dropArea.setLayout(new BorderLayout());
        JLabel dropAreaLabel = new JLabel("or Drag & Drop Audio File Here", SwingConstants.CENTER);
        dropAreaLabel.setForeground(Color.WHITE);
        dropArea.add(dropAreaLabel, BorderLayout.CENTER);

        dropArea.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> droppedFiles = (List<File>) evt.getTransferable()
                            .getTransferData(DataFlavor.javaFileListFlavor);
                    if (droppedFiles.size() > 0) {
                        selectedFile = droppedFiles.get(0);
                        fileNameLabel.setText("File: " + selectedFile.getName());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        mainPanel.add(dropArea);

        fileNameLabel = new JLabel("", SwingConstants.CENTER);
        fileNameLabel.setForeground(Color.BLACK);
        fileNameLabel.setBounds(150, 210, 300, 20);
        mainPanel.add(fileNameLabel);

        JLabel secretMessageLabel = new JLabel("Enter Secret Message:");
        secretMessageLabel.setBounds(150, 240, 200, 20);
        mainPanel.add(secretMessageLabel);

        secretMessageTextArea = new JTextArea();
        secretMessageTextArea.setBounds(150, 270, 300, 100);
        mainPanel.add(secretMessageTextArea);

        JButton embedButton = new JButton("Embed Message");
        embedButton.setBounds(150, 400, 130, 30);
        embedButton.addActionListener(e -> startEmbedding());
        mainPanel.add(embedButton);

        JButton decryptButton = new JButton("Decrypt Message");
        decryptButton.setBounds(300, 400, 150, 30);
        decryptButton.addActionListener(e -> startDecryption());
        mainPanel.add(decryptButton);
    }

    private void chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            fileNameLabel.setText("File: " + selectedFile.getName());
        }
    }

    private void startEmbedding() {
        if (selectedFile == null || secretMessageTextArea.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please choose a file and enter a secret message.");
            return;
        }

        String secretMessage = secretMessageTextArea.getText();
        AudioSteganography.embedMessageInAudio(selectedFile, secretMessage);
        JOptionPane.showMessageDialog(this, "Message embedded successfully!");
    }

    private void startDecryption() {
        if (selectedFile == null) {
            JOptionPane.showMessageDialog(this, "Please choose an audio file to decrypt.");
            return;
        }

        String extractedMessage = decryptMessage(selectedFile);
        if (extractedMessage != null) {
            
            JOptionPane.showMessageDialog(this, "Extracted Message: " + extractedMessage);

            System.out.println("Extracted Message: " + extractedMessage);
        } else {
            JOptionPane.showMessageDialog(this, "No message found or error occurred.");
            System.out.println("No message found or error occurred.");
        }
    }

    private String decryptMessage(File audioFile) {
        return decryptaudio.extractMessageFromAudio(audioFile);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AudioSteganographyUI ui = new AudioSteganographyUI();
            ui.setVisible(true);
        });
    }
}


