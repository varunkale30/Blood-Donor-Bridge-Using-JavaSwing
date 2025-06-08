import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class DonationEligibility extends JFrame {
    private JTextField ageField;
    private JTextField weightField;
    private JTextField heightField;
    private JComboBox<String> bloodGroupCombo;
    private JComboBox<String> genderCombo;
    private JButton checkButton;
    private JButton faqButton;
    private JButton resetButton;
    private JButton feedbackButton; // Feedback button

    public DonationEligibility() {
        setTitle("Check Donation Eligibility");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Check Your Donation Eligibility");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        JLabel ageLabel = new JLabel("Age:");
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        add(ageLabel, gbc);

        ageField = new JTextField(15);
        gbc.gridx = 1;
        add(ageField, gbc);
        ageField.setToolTipText("Enter your age (18-65)");

        JLabel weightLabel = new JLabel("Weight (kg):");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(weightLabel, gbc);

        weightField = new JTextField(15);
        gbc.gridx = 1;
        add(weightField, gbc);
        weightField.setToolTipText("Enter your weight in kg (at least 50 kg)");

        JLabel heightLabel = new JLabel("Height (cm):");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(heightLabel, gbc);

        heightField = new JTextField(15);
        gbc.gridx = 1;
        add(heightField, gbc);
        heightField.setToolTipText("Enter your height in cm (at least 150 cm)");

        JLabel genderLabel = new JLabel("Gender:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(genderLabel, gbc);

        genderCombo = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        gbc.gridx = 1;
        add(genderCombo, gbc);

        JLabel bloodGroupLabel = new JLabel("Blood Group:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(bloodGroupLabel, gbc);

        bloodGroupCombo = new JComboBox<>(new String[]{"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"});
        gbc.gridx = 1;
        add(bloodGroupCombo, gbc);

        checkButton = new JButton("Check Eligibility");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        checkButton.setBackground(new Color(0, 128, 0));
        checkButton.setForeground(Color.WHITE);
        add(checkButton, gbc);

        checkButton.addActionListener(e -> checkEligibility());

        faqButton = new JButton("FAQ");
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        faqButton.setBackground(new Color(0, 0, 255));
        faqButton.setForeground(Color.WHITE);
        add(faqButton, gbc);

        faqButton.addActionListener(e -> new FAQ().setVisible(true)); // Ensure FAQ class exists

        resetButton = new JButton("Reset");
        gbc.gridy = 8; // Position below FAQ button
        add(resetButton, gbc);
        resetButton.addActionListener(e -> resetFields());

        feedbackButton = new JButton("Feedback");
        gbc.gridy = 9; // Position below Reset button
        add(feedbackButton, gbc);
        feedbackButton.addActionListener(e -> openFeedbackDialog());

        pack();
        setVisible(true);
    }

    private void checkEligibility() {
        try {
            if (ageField.getText().trim().isEmpty() || 
                weightField.getText().trim().isEmpty() || 
                heightField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
                return;
            }

            int age = Integer.parseInt(ageField.getText());
            int weight = Integer.parseInt(weightField.getText());
            int height = Integer.parseInt(heightField.getText());
            String gender = (String) genderCombo.getSelectedItem();

            if (age < 18 || age > 65) {
                JOptionPane.showMessageDialog(this, "You must be between 18 and 65 years old to donate blood.");
                return;
            }

            if (weight < 50) {
                JOptionPane.showMessageDialog(this, "You must weigh at least 50 kg to donate blood.");
                return;
            }

            if (height < 150) {
                JOptionPane.showMessageDialog(this, "You must be at least 150 cm tall to donate blood.");
                return;
            }

            double bloodVolume = calculateBloodVolume(height, weight, gender);
            double donationVolume = bloodVolume * 0.10; // 10% of total blood volume can be donated
            
            // Cap the donation volume between 450 and 500 mL
            donationVolume = Math.max(450, Math.min(donationVolume, 500));

            JOptionPane.showMessageDialog(this, "You are eligible to donate approximately " + Math.round(donationVolume) + " mL of blood.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for age, weight, and height.");
        }
    }

    private double calculateBloodVolume(int height, int weight, String gender) {
        if (gender.equalsIgnoreCase("Male")) {
            return 0.3669 * Math.pow(height, 3) + 0.03219 * weight + 604;
        } else if (gender.equalsIgnoreCase("Female")) {
            return 0.3561 * Math.pow(height, 3) + 0.03308 * weight + 183;
        } else {
            // For "Other" gender, average the male and female formulas
            return (0.3669 * Math.pow(height, 3) + 0.03219 * weight + 604
                    + 0.3561 * Math.pow(height, 3) + 0.03308 * weight + 183) / 2;
        }
    }

    private void resetFields() {
        ageField.setText("");
        weightField.setText("");
        heightField.setText("");
        genderCombo.setSelectedIndex(0);
        bloodGroupCombo.setSelectedIndex(0);
    }

    private void openFeedbackDialog() {
        JDialog feedbackDialog = new JDialog(this, "Feedback", true);
        feedbackDialog.setLayout(new BorderLayout());
        feedbackDialog.setSize(300, 200);
        feedbackDialog.setLocationRelativeTo(this);

        JTextArea feedbackArea = new JTextArea();
        feedbackArea.setLineWrap(true);
        feedbackArea.setWrapStyleWord(true);
        feedbackArea.setToolTipText("Enter your feedback here");

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String feedback = feedbackArea.getText().trim();
            if (!feedback.isEmpty()) {
                saveFeedbackToFile(feedback); // Save feedback to file
                JOptionPane.showMessageDialog(this, "Thank you for your feedback!");
                feedbackDialog.dispose(); // Close the dialog
            } else {
                JOptionPane.showMessageDialog(this, "Please enter some feedback.");
            }
        });

        feedbackDialog.add(new JScrollPane(feedbackArea), BorderLayout.CENTER);
        feedbackDialog.add(submitButton, BorderLayout.SOUTH);
        feedbackDialog.setVisible(true);
    }

    private void saveFeedbackToFile(String feedback) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("feedback.txt", true))) {
            writer.write(feedback);
            writer.newLine(); // Move to the next line for each feedback
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving feedback. Please try again.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DonationEligibility::new);
    }
}
