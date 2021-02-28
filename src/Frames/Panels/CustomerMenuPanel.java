package Frames.Panels;

import Frames.logInFrame;
import Managers.CustomerManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

public class CustomerMenuPanel implements ActionListener {

    private JPanel customerMenuPanel;
    private final Font text = new Font("Arial", Font.PLAIN, 14);
    private final Font headers = new Font("Arial", Font.BOLD, 18);
    private CustomerManager customerMgr = CustomerManager.getInstance();
    private JButton newBookingButton;
    private JButton modifyBookingsButton;
    private JButton logOffButton;

    public CustomerMenuPanel() {
        customerMenuPanel = new JPanel();
        customerMenuPanel.setLayout(new BorderLayout());
        customerMenuPanel.setBorder(new EmptyBorder(20, 15, 0, 15));

        customerMenuPanel.add(userNavigationPanel(), BorderLayout.NORTH);
        customerMenuPanel.add(userOptionsPanel(), BorderLayout.CENTER);
    }

    private JPanel userNavigationPanel() {
        JPanel userNavigationPanel = new JPanel();
        userNavigationPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        userNavigationPanel.setBorder(new MatteBorder(0, 0, 1, 0, Color.black));

        JLabel welcomeMessage = new JLabel("Welcome back, " + customerMgr.getCustomerUser().getCustomerFirstName());
        welcomeMessage.setFont(headers);
        welcomeMessage.setBorder(new EmptyBorder(0, 0, 0, 1050));

        logOffButton = new JButton();
        logOffButton.setText("Log off");
        logOffButton.setFont(text);
        logOffButton.addActionListener(this);

        userNavigationPanel.add(welcomeMessage);
        userNavigationPanel.add(logOffButton);

        return userNavigationPanel;
    }

    private JPanel userOptionsPanel() {
        JPanel userOptionsPanel = new JPanel();
        userOptionsPanel.setLayout(new GridLayout(2, 2));

        JPanel newBookingButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        newBookingButtonPanel.setBorder(new EmptyBorder(0, 0, 0, 25));
        newBookingButton = new JButton("Make new booking");
        newBookingButton.setFont(headers);
        newBookingButton.setPreferredSize(new Dimension(250, 50));
        newBookingButton.setMaximumSize(newBookingButton.getPreferredSize());
        newBookingButton.addActionListener(this);
        newBookingButtonPanel.add(newBookingButton);

        JPanel modifyBookingsButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        modifyBookingsButtonPanel.setBorder(new EmptyBorder(0, 25, 0, 0));
        modifyBookingsButton = new JButton("See your bookings");
        modifyBookingsButton.setFont(headers);
        modifyBookingsButton.setPreferredSize(new Dimension(250, 50));
        modifyBookingsButton.setMaximumSize(modifyBookingsButton.getPreferredSize());
        modifyBookingsButton.addActionListener(this);
        modifyBookingsButtonPanel.add(modifyBookingsButton);

        userOptionsPanel.add(new JPanel());
        userOptionsPanel.add(new JPanel());
        userOptionsPanel.add(newBookingButtonPanel);
        userOptionsPanel.add(modifyBookingsButtonPanel);

        return userOptionsPanel;
    }

    public JPanel getCustomerMenuPanel() {
        return customerMenuPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newBookingButton) {
            customerMenuPanel.setVisible(false);
            customerMenuPanel.getParent().add(new CustomerBookCarsPanel().getCustomerBookCarsPanel());
            customerMenuPanel.getParent().remove(customerMenuPanel);
        } else if (e.getSource() == modifyBookingsButton) {
            customerMenuPanel.setVisible(false);
            customerMenuPanel.getParent().add(new CustomerSeeBookings().getCustomerSeeBookings());
            customerMenuPanel.getParent().remove(customerMenuPanel);
        } else if (e.getSource() == logOffButton) {
            logInFrame logInFrame = new logInFrame();
            SwingUtilities.getWindowAncestor(customerMenuPanel).dispose();
        }
    }

}
