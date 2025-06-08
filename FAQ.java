import javax.swing.*;
import java.awt.*;

public class FAQ extends JFrame {
    public FAQ() {
        setTitle("Frequently Asked Questions");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JTextArea faqTextArea = new JTextArea();
        faqTextArea.setText(
                "1. Who can donate blood?\n" +
                "Anyone aged between 18 and 65 years and weighing at least 50 kg can donate blood.\n\n" +
                "2. How much blood can I donate?\n" +
                "You can donate approximately 450 mL of blood.\n\n" +
                "3. Is it safe to donate blood?\n" +
                "Yes, donating blood is safe and does not harm your health.\n\n" +
                "4. How often can I donate blood?\n" +
                "You can donate blood every 3 months.\n"
        );
        faqTextArea.setEditable(false);
        faqTextArea.setLineWrap(true);
        faqTextArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(faqTextArea);
        add(scrollPane, BorderLayout.CENTER);
    }
}