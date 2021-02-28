package Frames;

import Managers.*;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class logInFrame extends JFrame implements ActionListener {

    private AdminManager adminMgr = AdminManager.getInstance();
    private CustomerManager customerMgr = CustomerManager.getInstance();
    private JFrame logInFrame = new JFrame();
    private final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    private final Font f = new Font("Arial", Font.PLAIN, 14);
    private JButton signUpButton;
    private JButton logInButton;
    private JTextField usernameInput;
    private JPasswordField passwordInput;
    private JCheckBox logInAsAdmin;

    public logInFrame() {
        logInFrame.setTitle("Bang and Co - Log In");
        logInFrame.setSize(600, 575);
        logInFrame.setLocation(dim.width / 2 - logInFrame.getSize().width / 2, dim.height / 2 - logInFrame.getSize().height / 2);
        logInFrame.setDefaultCloseOperation(logInFrame.EXIT_ON_CLOSE);
        logInFrame.setResizable(false);

        JPanel mainPanel = setupMainPanel();

        logInFrame.add(mainPanel);

        logInFrame.setVisible(true);
    }

    //Deals with calling the methods that will make up the frame and passes a panel so the frame can use it
    private JPanel setupMainPanel() {
        JPanel mainPanel = new JPanel();

        JPanel logInPanel = setupLogInPanel();
        JPanel signUpPanel = setupSignUpPanel();

        mainPanel.add(logInPanel);
        mainPanel.add(signUpPanel);

        return mainPanel;
    }

    //Sets up the login part of the frame
    private JPanel setupLogInPanel() {
        JPanel logInPanel = new JPanel();

        logInPanel.setBorder(BorderFactory.createTitledBorder(null, "Log in", TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 16)));
        logInPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        logInPanel.setPreferredSize(new Dimension(500, 400));

        JLabel pleaseLogInText = new JLabel("If you are already a member please log in: ");
        pleaseLogInText.setFont(new Font("Arial", Font.BOLD, 16));
        pleaseLogInText.setBorder(new EmptyBorder(10, 90, 15, 90));
        logInPanel.add(pleaseLogInText);

        JLabel username = new JLabel("Username: ");
        username.setFont(f);
        username.setBorder(new EmptyBorder(10, 110, 15, 0));
        logInPanel.add(username);

        usernameInput = new JTextField();
        usernameInput.setFont(f);
        usernameInput.setPreferredSize(new Dimension(170, 25));
        logInPanel.add(usernameInput);

        JLabel password = new JLabel("Password: ");
        password.setFont(f);
        password.setBorder(new EmptyBorder(10, 113, 15, 0));
        logInPanel.add(password);

        passwordInput = new JPasswordField();
        passwordInput.setFont(f);
        passwordInput.setPreferredSize(new Dimension(170, 25));
        logInPanel.add(passwordInput);

        logInAsAdmin = new JCheckBox();
        logInAsAdmin.setText("Log in as admin?");
        logInAsAdmin.setMargin(new Insets(10, 200, 15, 0));
        logInAsAdmin.setSelected(false);
        logInAsAdmin.addActionListener(this);
        logInPanel.add(logInAsAdmin);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(new EmptyBorder(10, 175, 15, 0));
        logInButton = new JButton("Log In");
        logInButton.setPreferredSize(new Dimension(150, 40));
        logInButton.addActionListener(this);
        buttonPanel.add(logInButton);
        logInPanel.add(buttonPanel);

        return logInPanel;
    }

    //Sets up the Sign up part of the frame
    private JPanel setupSignUpPanel() {
        JPanel signUpPanel = new JPanel();

        signUpPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        signUpPanel.setPreferredSize(new Dimension(500, 200));

        JLabel pleaseRegister = new JLabel("If you dont have an account register here: ");
        pleaseRegister.setFont(f);
        pleaseRegister.setBorder(new EmptyBorder(0, 20, 0, 0));
        signUpPanel.add(pleaseRegister);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(new EmptyBorder(10, 190, 0, 0));
        signUpButton = new JButton("Create account");
        signUpButton.setPreferredSize(new Dimension(150, 40));
        buttonPanel.add(signUpButton);
        signUpButton.addActionListener(this);
        signUpPanel.add(buttonPanel);
        return signUpPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logInButton) {
            if (logInAsAdmin.isSelected()) {
                boolean isAdminAuthenticated = adminMgr.authenticateAdmin(usernameInput.getText(), new String(passwordInput.getPassword()));

                if (usernameInput.getText().isBlank() || new String(passwordInput.getPassword()).isBlank()) {
                    JOptionPane.showMessageDialog(logInFrame, "Please fill all fields before attempting log in", "ERROR - Empty username or password", JOptionPane.ERROR_MESSAGE);
                } else if (isAdminAuthenticated == true) {
                    mainFrame mainFrame = new mainFrame(false);
                    logInFrame.setVisible(false);
                    logInFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(logInFrame, "Username or password incorrect", "ERROR - Failed log in", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                boolean isUserAuthenticated = customerMgr.authenticateCustomer(usernameInput.getText(), new String(passwordInput.getPassword()));

                if (usernameInput.getText().isBlank() || new String(passwordInput.getPassword()).isBlank()) {
                    JOptionPane.showMessageDialog(logInFrame, "Please fill all fields before attempting log in", "ERROR - Empty username or password", JOptionPane.ERROR_MESSAGE);
                } else if (isUserAuthenticated == true) {
                    if (customerMgr.getCustomerUser().isBlacklisted() == true) {
                        JOptionPane.showMessageDialog(logInFrame, "Your account has been blacklisted by our admins, please contact the office if you think this is a mistake", "ERROR - Blacklisted user", JOptionPane.WARNING_MESSAGE);
                    } else {
                        mainFrame mainFrame = new mainFrame(true);
                        logInFrame.setVisible(false);
                        logInFrame.dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(logInFrame, "Username or password incorrect", "ERROR - Failed log in", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (e.getSource() == signUpButton) {
            signUpFrame signUpFrame = new signUpFrame();
            logInFrame.setVisible(false);
            logInFrame.dispose();
        }
    }
}
