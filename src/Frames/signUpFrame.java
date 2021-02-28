/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frames;

import Managers.CustomerManager;
import com.toedter.calendar.JDateChooser;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Vicen
 */
public class signUpFrame extends JFrame implements ActionListener {

    private JFrame signUpFrame = new JFrame();
    private final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    private CustomerManager customerMgr = CustomerManager.getInstance();
    private final Font f = new Font("Arial", Font.PLAIN, 18);
    private JTextField usernameInput;
    private JPasswordField passwordInput;
    private JTextField firstNameInput;
    private JTextField lastNameInput;
    private JDateChooser DOBInput;
    private JTextField addressInput;
    private JButton logInButton;
    private JButton signUpButton;

    public signUpFrame() {
        signUpFrame.setTitle("Bang and Co - Create account");
        signUpFrame.setSize(450, 600);
        signUpFrame.setLocation(dim.width / 2 - signUpFrame.getSize().width / 2, dim.height / 2 - signUpFrame.getSize().height / 2);
        signUpFrame.setDefaultCloseOperation(logInFrame.EXIT_ON_CLOSE);
        signUpFrame.setResizable(false);

        JPanel mainPanel = mainPanel();

        signUpFrame.add(mainPanel);

        signUpFrame.setVisible(true);
    }

    private JPanel mainPanel() {
        JPanel customerDataPanel = new JPanel();
        customerDataPanel.setLayout(new BoxLayout(customerDataPanel, BoxLayout.Y_AXIS));

        JLabel createAccountLabel = new JLabel("Create account");
        createAccountLabel.setFont(new Font("Arial", Font.BOLD, 32));
        createAccountLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        createAccountLabel.setBorder(new EmptyBorder(20, 0, 20, 0));

        signUpButton = new JButton("Create account");
        signUpButton.setPreferredSize(new Dimension(175, 50));
        signUpButton.setMaximumSize(signUpButton.getPreferredSize());
        signUpButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        signUpButton.setFont(f);
        signUpButton.addActionListener(this);

        JLabel orLabel = new JLabel("or");
        orLabel.setFont(f);
        orLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        orLabel.setBorder(new EmptyBorder(15, 0, 15, 0));

        logInButton = new JButton("Log in");
        logInButton.setPreferredSize(new Dimension(175, 50));
        logInButton.setMaximumSize(signUpButton.getPreferredSize());
        logInButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        logInButton.setFont(f);
        logInButton.addActionListener(this);

        customerDataPanel.add(createAccountLabel);
        customerDataPanel.add(customerUsernamePanel());
        customerDataPanel.add(customerPasswordPanel());
        customerDataPanel.add(customerFirstNamePanel());
        customerDataPanel.add(customerLastNamePanel());
        customerDataPanel.add(customerDateOfBirthPanel());
        customerDataPanel.add(customerAddressPanel());
        customerDataPanel.add(signUpButton);
        customerDataPanel.add(orLabel);
        customerDataPanel.add(logInButton);

        return customerDataPanel;
    }

    private JPanel customerUsernamePanel() {
        JPanel customerUsernamePanel = new JPanel();
        customerUsernamePanel.setPreferredSize(new Dimension(500, 50));
        customerUsernamePanel.setMaximumSize(customerUsernamePanel.getPreferredSize());

        JLabel username = new JLabel("Username: ");
        username.setFont(f);

        usernameInput = new JTextField();
        usernameInput.setPreferredSize(new Dimension(170, 25));

        customerUsernamePanel.add(username);
        customerUsernamePanel.add(usernameInput);

        return customerUsernamePanel;
    }

    private JPanel customerPasswordPanel() {
        JPanel customerPasswordPanel = new JPanel();
        customerPasswordPanel.setPreferredSize(new Dimension(500, 50));
        customerPasswordPanel.setMaximumSize(customerPasswordPanel.getPreferredSize());

        JLabel password = new JLabel("Password: ");
        password.setFont(f);

        passwordInput = new JPasswordField();
        passwordInput.setPreferredSize(new Dimension(170, 25));

        customerPasswordPanel.add(password);
        customerPasswordPanel.add(passwordInput);

        return customerPasswordPanel;
    }

    private JPanel customerFirstNamePanel() {
        JPanel customerFirstNamePanel = new JPanel();
        customerFirstNamePanel.setPreferredSize(new Dimension(500, 50));
        customerFirstNamePanel.setMaximumSize(customerFirstNamePanel.getPreferredSize());

        JLabel firstName = new JLabel("First name: ");
        firstName.setFont(f);

        firstNameInput = new JTextField();
        firstNameInput.setPreferredSize(new Dimension(170, 25));

        customerFirstNamePanel.add(firstName);
        customerFirstNamePanel.add(firstNameInput);

        return customerFirstNamePanel;
    }

    private JPanel customerLastNamePanel() {
        JPanel customerLastNamePanel = new JPanel();
        customerLastNamePanel.setPreferredSize(new Dimension(500, 50));
        customerLastNamePanel.setMaximumSize(customerLastNamePanel.getPreferredSize());

        JLabel lastName = new JLabel("Last name: ");
        lastName.setFont(f);

        lastNameInput = new JTextField();
        lastNameInput.setPreferredSize(new Dimension(170, 25));

        customerLastNamePanel.add(lastName);
        customerLastNamePanel.add(lastNameInput);

        return customerLastNamePanel;
    }

    private JPanel customerDateOfBirthPanel() {
        JPanel customerDateOfBirthPanel = new JPanel();
        customerDateOfBirthPanel.setPreferredSize(new Dimension(500, 50));
        customerDateOfBirthPanel.setMaximumSize(customerDateOfBirthPanel.getPreferredSize());

        JLabel DOB = new JLabel("Date of birth: ");
        DOB.setFont(f);

        DOBInput = new JDateChooser();
        DOBInput.setPreferredSize(new Dimension(170, 25));

        customerDateOfBirthPanel.add(DOB);
        customerDateOfBirthPanel.add(DOBInput);

        return customerDateOfBirthPanel;
    }

    private JPanel customerAddressPanel() {
        JPanel customerAddressPanel = new JPanel();
        customerAddressPanel.setPreferredSize(new Dimension(500, 50));
        customerAddressPanel.setMaximumSize(customerAddressPanel.getPreferredSize());

        JLabel address = new JLabel("Address ");
        address.setFont(f);

        addressInput = new JTextField();
        addressInput.setPreferredSize(new Dimension(170, 25));

        customerAddressPanel.add(address);
        customerAddressPanel.add(addressInput);

        return customerAddressPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == signUpButton) {
            if (usernameInput.getText().isBlank() || new String(passwordInput.getPassword()).isBlank() || firstNameInput.getText().isBlank() || lastNameInput.getText().isBlank() || new String(DOBInput.toString()).isBlank() || addressInput.getText().isBlank()) {
                JOptionPane.showMessageDialog(signUpFrame, "Please fill all fields before attempting log in", "ERROR - Empty username or password", JOptionPane.ERROR_MESSAGE);
            } else {
                int newCustomerAge = Period.between(LocalDate.ofInstant(DOBInput.getCalendar().toInstant(), ZoneId.systemDefault()), LocalDate.now()).getYears();
                if (newCustomerAge >= 18) {
                    if (customerMgr.checkUsernameAvailability(usernameInput.getText()) == false) {
                        JOptionPane.showMessageDialog(signUpFrame, "Username already taken, please insert a new username", "ERROR - Username duplication", JOptionPane.ERROR_MESSAGE);
                    } else {
                        customerMgr.signUpCustomer(usernameInput.getText(), new String(passwordInput.getPassword()), firstNameInput.getText(), lastNameInput.getText(), LocalDate.ofInstant(DOBInput.getCalendar().toInstant(), ZoneId.systemDefault()), addressInput.getText(), false);
                        JOptionPane.showMessageDialog(signUpFrame, "Account created!");
                        logInFrame logInFrame = new logInFrame();
                        signUpFrame.setVisible(false);
                        signUpFrame.dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(signUpFrame, "You need to be over 18 to create an account with Bangers and Co", "ERROR - Over 18 restriction", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (e.getSource() == logInButton) {
            logInFrame logInFrame = new logInFrame();
            signUpFrame.setVisible(false);
            signUpFrame.dispose();
        }
    }

}
