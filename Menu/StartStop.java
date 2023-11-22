import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class StartStop extends JPanel {
    
    public StartStop(TetrisBase tetrisBase) {
        setBackground(Color.ORANGE);
        setPreferredSize(new Dimension(250, 100));
        setLayout(new BorderLayout());
        JButton button = new JButton("Start Stop");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tetrisBase.getTimer().isRunning()) {
                    tetrisBase.getTimer().stop();
                } else {
                    tetrisBase.getTimer().start();
                    tetrisBase.requestFocusInWindow();
                }
            }
        });
        add(button, BorderLayout.CENTER);
    }
    
}
